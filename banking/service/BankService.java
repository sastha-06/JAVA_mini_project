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

    public void transfer(Account from,Account to,double amt) throws BankException{

        if(from==null || to==null){
            throw new BankException("Account not found");
        }

        if(amt>from.getBalance()){
            throw new BankException("Insufficient balance");
        }

        if(from instanceof SavingsAccount){
            SavingsAccount sa=(SavingsAccount)from;

            if(!sa.canWithdraw(amt)){
                throw new BankException("Minimum balance must be maintained");
            }
        }

        from.setBalance(from.getBalance()-amt);

        to.setBalance(to.getBalance()+amt);

        from.addTransaction("Transfer to "+to.accountNumber+" ₹ "+amt);

        to.addTransaction("Received from "+from.accountNumber+" ₹ "+amt);

    }

    public void showAllAccounts() {
        for (int i = 0; i < count; i++) {
            accounts[i].displayDetails();
            System.out.println("----------------------");
        }
    }
}