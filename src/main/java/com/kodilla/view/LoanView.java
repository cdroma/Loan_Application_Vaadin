package com.kodilla.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;


@Route("")
public class LoanView extends VerticalLayout {

    private final WebClient webClient;
    private final Grid<Loan> grid;

    private final String backendUrl = "http://localhost:8080/api/loan";


    public LoanView() {
        this.webClient = WebClient.create();
        this.grid = new Grid<>(Loan.class);

        // Ustawienie kolumn w Grid
        grid.setColumns("id", "amount", "currency", "dueDate", "paidOff");

        Button addButton = new Button("Add Loan", e -> showAddLoanDialog());

        add(addButton, grid);
        refreshGrid();
    }

    private void refreshGrid() {
        webClient.get()
                .uri(backendUrl)
                .retrieve()
                .bodyToFlux(Loan.class)
                .collectList()
                .subscribe(
                        grid::setItems,
                        error -> {
                            Notification.show("Error loading loans: " + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    private void showAddLoanDialog() {
        Dialog dialog = new Dialog();
        TextField amountField = new TextField("Amount");
        TextField currencyField = new TextField("Currency");
        TextField dueDateField = new TextField("Due Date (YYYY-MM-DD)");

        Button saveButton = new Button("Save", e -> {
            double amount = Double.parseDouble(amountField.getValue());
            String currency = currencyField.getValue();
            LocalDate dueDate = LocalDate.parse(dueDateField.getValue());
            Loan newLoan = new Loan();
            newLoan.setAmount(amount);
            newLoan.setCurrency(currency);
            newLoan.setDueDate(dueDate);
            newLoan.setPaidOff(false);
            
            webClient.post()
                    .uri(backendUrl)
                    .bodyValue(newLoan)
                    .retrieve()
                    .bodyToMono(Loan.class)
                    .subscribe(loan -> {
                        dialog.close();
                        refreshGrid();
                        Notification.show("Loan added!");
                    }, error -> Notification.show("Error adding loan: " + error.getMessage()));
        });

        dialog.add(amountField, currencyField, dueDateField, saveButton);
        dialog.open();
    }
}
