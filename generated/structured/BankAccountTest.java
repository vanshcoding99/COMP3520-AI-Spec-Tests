package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class BankAccountTest {

    // Requirement: new account has balance 0
    @Test
    public void newAccountHasZeroBalance() {
        assertEquals(0L, new BankAccount().getBalance());
    }

    // Requirement: deposit adds cents to the balance
    @Test
    public void depositAddsToBalance() {
        BankAccount a = new BankAccount();
        a.deposit(500);
        assertEquals(500L, a.getBalance());
    }

    // Requirement: deposit of 1 cent (smallest valid) is accepted
    @Test
    public void depositOneCent() {
        BankAccount a = new BankAccount();
        a.deposit(1);
        assertEquals(1L, a.getBalance());
    }

    // Requirement: multiple deposits accumulate
    @Test
    public void multipleDepositsAccumulate() {
        BankAccount a = new BankAccount();
        a.deposit(100);
        a.deposit(250);
        assertEquals(350L, a.getBalance());
    }

    // Requirement: deposit throws IllegalArgumentException if cents <= 0 (zero)
    @Test
    public void depositZeroThrows() {
        BankAccount a = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> a.deposit(0));
    }

    // Requirement: deposit throws IllegalArgumentException if cents <= 0 (negative)
    @Test
    public void depositNegativeThrows() {
        BankAccount a = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> a.deposit(-1));
    }

    // Requirement: failed deposit leaves balance unchanged
    @Test
    public void failedDepositLeavesBalanceUnchanged() {
        BankAccount a = new BankAccount();
        a.deposit(300);
        assertThrows(IllegalArgumentException.class, () -> a.deposit(0));
        assertEquals(300L, a.getBalance());
    }

    // Requirement: failed negative deposit leaves balance unchanged
    @Test
    public void failedNegativeDepositLeavesBalanceUnchanged() {
        BankAccount a = new BankAccount();
        a.deposit(300);
        assertThrows(IllegalArgumentException.class, () -> a.deposit(-50));
        assertEquals(300L, a.getBalance());
    }

    // Requirement: withdraw subtracts cents from the balance
    @Test
    public void withdrawSubtractsFromBalance() {
        BankAccount a = new BankAccount();
        a.deposit(1000);
        a.withdraw(400);
        assertEquals(600L, a.getBalance());
    }

    // Requirement: withdrawing exactly the full balance is allowed
    @Test
    public void withdrawFullBalanceAllowed() {
        BankAccount a = new BankAccount();
        a.deposit(1000);
        a.withdraw(1000);
        assertEquals(0L, a.getBalance());
    }

    // Requirement: withdrawing one cent less than balance is allowed
    @Test
    public void withdrawJustUnderBalance() {
        BankAccount a = new BankAccount();
        a.deposit(1000);
        a.withdraw(999);
        assertEquals(1L, a.getBalance());
    }

    // Requirement: withdraw of 1 cent (smallest valid) is accepted
    @Test
    public void withdrawOneCent() {
        BankAccount a = new BankAccount();
        a.deposit(10);
        a.withdraw(1);
        assertEquals(9L, a.getBalance());
    }

    // Requirement: withdraw throws IllegalStateException if cents > balance (just above)
    @Test
    public void withdrawOneMoreThanBalanceThrows() {
        BankAccount a = new BankAccount();
        a.deposit(1000);
        assertThrows(IllegalStateException.class, () -> a.withdraw(1001));
    }

    // Requirement: overdraw leaves balance unchanged
    @Test
    public void overdrawLeavesBalanceUnchanged() {
        BankAccount a = new BankAccount();
        a.deposit(1000);
        assertThrows(IllegalStateException.class, () -> a.withdraw(1001));
        assertEquals(1000L, a.getBalance());
    }

    // Requirement: withdraw from new (zero balance) account throws IllegalStateException
    @Test
    public void withdrawFromEmptyAccountThrows() {
        BankAccount a = new BankAccount();
        assertThrows(IllegalStateException.class, () -> a.withdraw(1));
    }

    // Requirement: failed withdraw from empty account leaves balance at 0
    @Test
    public void withdrawFromEmptyAccountLeavesZero() {
        BankAccount a = new BankAccount();
        assertThrows(IllegalStateException.class, () -> a.withdraw(1));
        assertEquals(0L, a.getBalance());
    }

    // Requirement: withdraw throws IllegalArgumentException if cents <= 0 (zero)
    @Test
    public void withdrawZeroThrows() {
        BankAccount a = new BankAccount();
        a.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> a.withdraw(0));
    }

    // Requirement: withdraw throws IllegalArgumentException if cents <= 0 (negative)
    @Test
    public void withdrawNegativeThrows() {
        BankAccount a = new BankAccount();
        a.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> a.withdraw(-1));
    }

    // Requirement: failed withdraw with invalid amount leaves balance unchanged
    @Test
    public void invalidWithdrawLeavesBalanceUnchanged() {
        BankAccount a = new BankAccount();
        a.deposit(100);
        assertThrows(IllegalArgumentException.class, () -> a.withdraw(-1));
        assertEquals(100L, a.getBalance());
    }

    // Requirement: deposit and withdraw sequence tracks balance
    @Test
    public void sequenceOfOperations() {
        BankAccount a = new BankAccount();
        a.deposit(500);
        a.withdraw(200);
        a.deposit(50);
        assertEquals(350L, a.getBalance());
    }

    // Requirement: balance held as long cents supports values beyond int range
    @Test
    public void largeDepositBeyondIntRange() {
        BankAccount a = new BankAccount();
        long big = 5_000_000_000L;
        a.deposit(big);
        assertEquals(big, a.getBalance());
    }
}
