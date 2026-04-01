package main;

import exception.BankException;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
//import javax.swing.plaf.synth.Region;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import service.*;

public class BankUI extends Application {

    static BankService service = new BankService();
    static Account loggedInAccount;

    @Override
    public void start(Stage stage) {

        service.createAccount(new SavingsAccount(106, "Sastha", 6000, 2006));
        service.createAccount(new SavingsAccount(77, "Navi", 6000, 1234));

        showLogin(stage);
    }

    // ================= LOGIN (UPGRADED UI) =================
    public void showLogin(Stage stage) {

        Label title = new Label("💳 MINI BANKING SYSTEM");
        title.setStyle(
                "-fx-font-size:28;" +
                "-fx-font-weight:bold;" +
                "-fx-text-fill:#38bdf8;" +
                "-fx-alignment:center;"
               // "-fx-effect: dropshadow(gaussian,#38bdf8,20,0.4,0,0);"
        );

        TextField acc = new TextField();
        acc.setPromptText("Account Number");
        acc.setPrefWidth(300);
        acc.setMaxWidth(300); 
        acc.setStyle("-fx-background-radius:10; -fx-padding:10;");

        PasswordField pin = new PasswordField();
        pin.setPromptText("PIN");
        pin.setPrefWidth(300);
        pin.setMaxWidth(300);  
        pin.setStyle("-fx-background-radius:10; -fx-padding:10;");

        Button login = new Button("LOGIN");
        login.setStyle(
                "-fx-background-color: linear-gradient(to right,#38bdf8,#22c55e);" +
                "-fx-text-fill:black;" +
                "-fx-font-weight:bold;" +
                "-fx-background-radius:12;" +
                "-fx-padding:10 25;"
        );

        Label msg = new Label();
        msg.setStyle("-fx-text-fill:#f87171; -fx-font-weight:bold;");

        VBox box = new VBox(15, title, acc, pin, login, msg);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(30));

        box.setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#020617,#0f172a,#1e1b4b);" +
                "-fx-alignment:center;"
        );

        login.setOnAction(e -> {
            try {
                int a = Integer.parseInt(acc.getText());
                int p = Integer.parseInt(pin.getText());

                Account ac = service.findAccount(a);

                if (ac != null && ac.validatePin(p)) {
                    loggedInAccount = ac;
                    showDashboard(stage);
                } else {
                    msg.setText("Invalid Login ❌");
                }

            } catch (Exception ex) {
                msg.setText("Enter valid data ❌");
            }
        });
      
        stage.setScene(new Scene(box, 400, 400));
        stage.setTitle("Login");
        stage.show();
    }

    // ================= DASHBOARD (FULL UI UPGRADE) =================
    public void showDashboard(Stage stage) {

        // Sidebar
        VBox side = new VBox(15);
        side.setPadding(new Insets(20));
        side.setStyle(
                "-fx-background-color: linear-gradient(to bottom,#0f172a,#020617);" +
                "-fx-spacing:12;"
        );

        Button deposit = createBtn("Deposit");
        Button withdraw = createBtn("Withdraw");
        Button transfer = createBtn("Transfer");
        Button changePin = createBtn("Change PIN");
        Button trans = createBtn("Transactions");
        Button logout = createBtn("Logout");

        side.getChildren().addAll(deposit, withdraw, transfer, changePin, trans);//, logout);
        // Add a spacer that grows to fill space
        //Region spacer = new Region();
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        side.getChildren().add(spacer);

       // Add logout at the very bottom
       side.getChildren().add(logout);
        // Top Card
        Label welcome = new Label("Welcome " + loggedInAccount.getName());
        welcome.setStyle("-fx-font-size:18; -fx-text-fill:#38bdf8;");

        Label balance = new Label("₹ " + loggedInAccount.getBalance());
        balance.setStyle(
                "-fx-font-size:28;" +
                "-fx-text-fill:#22c55e;" +
                "-fx-font-weight:bold;" //+
                //"-fx-effect: dropshadow(gaussian,#22c55e,25,0.5,0,0);"
        );

        VBox card = new VBox(10, welcome, balance);
        card.setPadding(new Insets(20));
        card.setStyle(
                "-fx-background-color: rgba(30,41,59,0.7);" +
                "-fx-background-radius:20;" +
                "-fx-border-color: rgba(56,189,248,0.2);" +
                "-fx-border-radius:20;"
        );

        // Output Area
        TextArea output = new TextArea();
        output.setPrefHeight(150);
        output.setStyle(
                "-fx-control-inner-background:#0f172a;" +
                "-fx-text-fill:#e2e8f0;" +
                "-fx-border-color:#38bdf8;" +
                "-fx-border-radius:10;"
        );

        VBox center = new VBox(20, card, output);
        center.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setLeft(side);
        root.setCenter(center);

        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right,#020617,#0f172a);"
        );
       
        // ================= DEPOSIT =================
        deposit.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setHeaderText("Enter Amount");

            d.showAndWait().ifPresent(val -> {
                try {
                    double amount = Double.parseDouble(val);
                    service.deposit(loggedInAccount, amount);
                    balance.setText("₹ " + loggedInAccount.getBalance());
                    output.setText("💰 Deposit Successful");
                } catch (Exception ex) {
                    output.setText("Invalid input ❌");
                }
            });
        });

        // ================= WITHDRAW =================
        withdraw.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setHeaderText("Enter Amount");

            d.showAndWait().ifPresent(val -> {
                try {
                    double amount = Double.parseDouble(val);
                    service.withdraw(loggedInAccount, amount);
                    balance.setText("₹ " + loggedInAccount.getBalance());
                    output.setText("💸 Withdraw Successful");
                } catch (Exception ex) {
                    output.setText("Failed: " + ex.getMessage());
                }
            });
        });

        // ================= TRANSFER =================
        transfer.setOnAction(e -> {
            TextInputDialog acc = new TextInputDialog();
            acc.setHeaderText("Receiver Account");

            acc.showAndWait().ifPresent(a -> {
                TextInputDialog amt = new TextInputDialog();
                amt.setHeaderText("Amount");

                amt.showAndWait().ifPresent(val -> {

                    Account r = service.findAccount(Integer.parseInt(a));

                    if (r == null) {
                        output.setText("Receiver not found ❌");
                        return;
                    }

                    try {
                        double amount = Double.parseDouble(val);
                        service.transfer(loggedInAccount, r, amount);

                        balance.setText("₹ " + loggedInAccount.getBalance());
                        output.setText("🔄 Transfer Successful");

                    } catch (BankException ex) {
                        output.setText(ex.getMessage());
                    }
                });
            });
        });

        // ================= CHANGE PIN =================
        changePin.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setHeaderText("New PIN");

            d.showAndWait().ifPresent(val -> {
                loggedInAccount.setPin(Integer.parseInt(val));
                output.setText("🔐 PIN Updated");
            });
        });

        // ================= TRANSACTIONS =================
        trans.setOnAction(e -> {
            String history = String.join("\n", loggedInAccount.getTransactions());
            output.setText(history.isEmpty() ? "No transactions" : history);
        });

        // ================= LOGOUT =================
        logout.setOnAction(e -> showLogin(stage));

        stage.setScene(new Scene(root, 800, 500));
        stage.setTitle("Mini Bank ATM");
        stage.show();
    }

    // ================= BUTTON DESIGN =================
    private Button createBtn(String text) {

        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);

        b.setStyle(
                "-fx-background-color:#1e293b;" +
                "-fx-text-fill:white;" +
                "-fx-background-radius:12;" +
                "-fx-padding:15;"+          // increase padding for bigger button
                "-fx-font-size:16;"
        );

        b.setOnMouseEntered(e ->
                b.setStyle(
                        "-fx-background-color: linear-gradient(to right,#38bdf8,#22c55e);" +
                        "-fx-text-fill:black;" +
                        "-fx-font-weight:bold;" +
                        "-fx-background-radius:12;"+
                        "-fx-padding:15;"+          // increase padding for bigger button
                        "-fx-font-size:16;"
                )
        );

        b.setOnMouseExited(e ->
                b.setStyle(
                        "-fx-background-color:#1e293b;" +
                        "-fx-text-fill:white;" +
                        "-fx-background-radius:12;"+
                        "-fx-padding:15;"+          // increase padding for bigger button
                        "-fx-font-size:16;"
                )
        );

        return b;
    }

    public static void main(String[] args) {
        launch();
    }
}