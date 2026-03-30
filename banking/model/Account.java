package model;

public class Account {
    public int accountNumber;
    protected String name;
    public double balance;
    protected int pin;

    protected String[] transactions = new String[20];
    protected int tCount = 0;

    public Account(int accNo, String name, double balance, int pin) {
        this.accountNumber = accNo;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    public void addTransaction(String msg) {
        if (tCount < transactions.length) {
            transactions[tCount++] = msg;
        }
    }

    public void showTransactions() {
        System.out.println("Transaction History:");
        for (int i = 0; i < tCount; i++) {
            System.out.println(transactions[i]);
        }
    }

    public boolean validatePin(int inputPin) {
        return this.pin == inputPin;
    }

    public void displayDetails() {
        System.out.println("Account No: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Balance: " + balance);
    }
}