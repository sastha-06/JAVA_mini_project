package main;

import exception.*;
import java.util.Scanner;
import model.*;
import service.*;

public class BankApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();

        int choice;

        do {
            System.out.println("\n--- MINI BANK SYSTEM ---");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. View All Accounts");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Select Account Type:");
                    System.out.println("1. Savings");
                    System.out.println("2. Current");

                    int type = sc.nextInt();

                    System.out.print("Enter Account No: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Balance: ");
                    double bal = sc.nextDouble();

                    System.out.print("Set PIN: ");
                    int pin = sc.nextInt();

                    if (type == 1) {
                        service.createAccount(new SavingsAccount(accNo, name, bal, pin));
                    } else if (type == 2) {
                        service.createAccount(new CurrentAccount(accNo, name, bal, pin));
                    } else {
                        System.out.println("Invalid type!");
                    }
                    break;

                case 2:
                    System.out.print("Enter Account No: ");
                    accNo = sc.nextInt();

                    Account acc = service.findAccount(accNo);

                    if (acc == null) {
                        System.out.println("Account not found!");
                        break;
                    }

                    System.out.print("Enter PIN: ");
                    int inputPin = sc.nextInt();

                    if (!acc.validatePin(inputPin)) {
                        System.out.println("Wrong PIN!");
                        break;
                    }

                    int op;
                    do {
                        System.out.println("\n1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Balance");
                        System.out.println("4. Transactions");
                        System.out.println("5. Logout");

                        System.out.print("Enter choice: ");
                        op = sc.nextInt();

                        switch (op) {

                            case 1:
                                System.out.print("Amount: ");
                                double d = sc.nextDouble();
                                try {
                                    service.deposit(acc, d);
                                } catch (BankException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 2:
                                System.out.print("Amount: ");
                                double w = sc.nextDouble();
                                try {
                                    service.withdraw(acc, w);
                                } catch (BankException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 3:
                                System.out.println("Balance: " + acc.balance);
                                break;

                            case 4:
                                acc.showTransactions();
                                break;

                            case 5:
                                System.out.println("Logged out successfully!");
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }

                    } while (op != 5);
                    break;

                case 3:
                    service.showAllAccounts();
                    break;

                case 4:
                    System.out.println("Thank You!");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 4);

        sc.close(); 
    }
}