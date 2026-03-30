package service;

import exception.BankException;
import model.*;

public class BankService implements BankOperations {

    Account[] accounts = new Account[10];
    int count = 0;

    public void createAccount(Account acc) {
        accounts[count++] = acc;
        System.out.println("Account Created Successfully!");
    }

    public Account findAccount(int accNo) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].accountNumber == accNo) {
                return accounts[i];
            }
        }
        return null;
    }

    @Override
    public void deposit(Account acc, double amt) {
        acc.balance += amt;
        acc.addTransaction("Deposited: " + amt);
        System.out.println("Deposit Successful!");
    }

    @Override
    public void withdraw(Account acc, double amt) throws BankException {

        if (amt > acc.balance) {
            throw new BankException("Insufficient Balance!");
        }

        if (acc instanceof SavingsAccount) {
            SavingsAccount sa = (SavingsAccount) acc;
            if (!sa.canWithdraw(amt)) {
                throw new BankException("Minimum balance must be maintained!");
            }
        }

        acc.balance -= amt;
        acc.addTransaction("Withdrawn: " + amt);
        System.out.println("Withdrawal Successful!");
    }

    public void showAllAccounts() {
        for (int i = 0; i < count; i++) {
            accounts[i].displayDetails();
            System.out.println("----------------------");
        }
    }
}