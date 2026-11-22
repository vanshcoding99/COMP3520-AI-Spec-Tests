package bench;

public class BankAccount {
    private long balanceCents;

    public BankAccount() {
        this.balanceCents = 0;
    }

    public void deposit(long cents) {
        if (cents <= 0) {
            throw new IllegalArgumentException("deposit must be positive");
        }
        balanceCents += cents;
    }

    public void withdraw(long cents) {
        if (cents <= 0) {
            throw new IllegalArgumentException("withdrawal must be positive");
        }
        if (cents > balanceCents) {
            throw new IllegalStateException("insufficient funds");
        }
        balanceCents -= cents;
    }

    public long getBalance() {
        return balanceCents;
    }
}
