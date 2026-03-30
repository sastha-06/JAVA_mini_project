package model;

public class SavingsAccount extends Account {

    private final double MIN_BALANCE = 1000;

    public SavingsAccount(int accNo, String name, double balance, int pin) {
        super(accNo, name, balance, pin);
    }

    @Override
    public void displayDetails() {
        System.out.println("Account Type: Savings");
        super.displayDetails();
    }

    public boolean canWithdraw(double amount) {
        return (balance - amount) >= MIN_BALANCE;
    }
}
