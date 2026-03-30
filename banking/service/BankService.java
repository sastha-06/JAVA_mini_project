package service;

import exception.BankException;
import model.*;

public class BankService {

    Account[] accounts = new Account[10];
    int count = 0;

    // CREATE ACCOUNT
    public void createAccount(Account acc) {
        if (count >= accounts.length) {
            System.out.println("Bank storage full!");
            return;
        }
        accounts[count++] = acc;
        System.out.println("Account Created Successfully!");
    }

    // FIND ACCOUNT
    public Account findAccount(int accNo) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].accountNumber == accNo) {
                return accounts[i];
            }
        }
        return null;
    }

    // DEPOSIT
    public void deposit(Account acc, double amt) throws BankException {
        if (amt <= 0) {
            throw new BankException("Invalid deposit amount!");
        }

        acc.balance += amt;
        acc.addTransaction("Deposited: " + amt);
        System.out.println("Deposit Successful!");
    }

    // WITHDRAW (FIXED LOGIC)
    public void withdraw(Account acc, double amt) throws BankException {

        if (amt <= 0) {
            throw new BankException("Invalid withdrawal amount!");
        }

        // 1. Check insufficient balance FIRST
        if (amt > acc.balance) {
            throw new BankException("Insufficient Balance!");
        }

        // 2. Check minimum balance ONLY for savings
        if (acc instanceof SavingsAccount) {
            SavingsAccount sa = (SavingsAccount) acc;

            if (!sa.canWithdraw(amt)) {
                throw new BankException("Minimum balance must be maintained!");
            }
        }

        // 3. Perform withdrawal
        acc.balance -= amt;
        acc.addTransaction("Withdrawn: " + amt);
        System.out.println("Withdrawal Successful!");
    }

    // DISPLAY ALL ACCOUNTS
    public void showAllAccounts() {
        if (count == 0) {
            System.out.println("No accounts available.");
            return;
        }

        for (int i = 0; i < count; i++) {
            accounts[i].displayDetails();
            System.out.println("----------------------");
        }
    }
}