package service;

import model.Account;
import exception.BankException;

public interface BankOperations {
    void deposit(Account acc, double amt);
    void withdraw(Account acc, double amt) throws BankException;
}