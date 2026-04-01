package main;

import exception.BankException;
import java.util.Scanner;
import model.*;
import service.*;

public class BankApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankService service = new BankService();
        System.out.println("Run BankUI for GUI version");
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
                    System.out.println("1. Savings\n2. Current");
                    int type = sc.nextInt();

                    System.out.print("Acc No: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Balance: ");
                    double bal = sc.nextDouble();

                    System.out.print("PIN: ");
                    int pin = sc.nextInt();

                    if (type == 1)
                        service.createAccount(new SavingsAccount(accNo, name, bal, pin));
                    else
                        service.createAccount(new CurrentAccount(accNo, name, bal, pin));
                    break;

                case 2:
                    System.out.print("Acc No: ");
                    accNo = sc.nextInt();

                    Account acc = service.findAccount(accNo);

                    if (acc == null) {
                        System.out.println("Account not found!");
                        break;
                    }

                    System.out.print("PIN: ");
                    if (!acc.validatePin(sc.nextInt())) {
                        System.out.println("Wrong PIN!");
                        break;
                    }
                    
                   int op;
                    do {
                        System.out.println("\n1.Deposit\n2.Withdraw\n3.Balance\n4.Transactions\n5.Logout");
                        op = sc.nextInt();

                        switch (op) {
                            case 1:
                                System.out.print("Amount: ");
                                service.deposit(acc, sc.nextDouble());
                                break;

                            case 2:
                                System.out.print("Amount: ");
                                try {
                                    service.withdraw(acc, sc.nextDouble());
                                } catch (BankException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;

                            case 3:
                                System.out.println("Balance: " + acc.getBalance());
                                 break;
                                

                            case 4:
                                acc.showTransactions();
                                break;
                        }

                    } while (op != 5);
                    break;


                case 3:
                    service.showAllAccounts();
                    break;

                case 4:
                    System.out.println("Thank You!");
                    break;
            }

        } while (choice != 4);

        sc.close();
    }
}