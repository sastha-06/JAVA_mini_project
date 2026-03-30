package model;

public class CurrentAccount extends Account {

    public CurrentAccount(int accNo, String name, double balance, int pin) {
        super(accNo, name, balance, pin);
    }

    @Override
    public void displayDetails() {
        System.out.println("Account Type: Current");
        super.displayDetails();
    }
}
