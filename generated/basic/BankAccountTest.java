package bench;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class BankAccountTest {

    private BankAccount account;

    @Before
    public void setUp() {
        account = new BankAccount();
    }

    @Test
    public void newAccountHasZeroBalance() {
        assertEquals(0L, account.getBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        account.deposit(500);
        assertEquals(500L, account.getBalance());
    }

    @Test
    public void multipleDepositsAccumulate() {
        account.deposit(100);
        account.deposit(250);
        account.deposit(1);
        assertEquals(351L, account.getBalance());
    }

    @Test
    public void depositSmallestAmount() {
        account.deposit(1);
        assertEquals(1L, account.getBalance());
    }

    @Test
    public void largeDeposit() {
        account.deposit(10_000_000_000L);
        assertEquals(10_000_000_000L, account.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositZeroThrows() {
        account.deposit(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeThrows() {
        account.deposit(-100);
    }

    @Test
    public void invalidDepositLeavesBalanceUnchanged() {
        account.deposit(300);
        try {
            account.deposit(0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // ok
        }
        try {
            account.deposit(-5);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // ok
        }
        assertEquals(300L, account.getBalance());
    }

    @Test
    public void withdrawDecreasesBalance() {
        account.deposit(1000);
        account.withdraw(300);
        assertEquals(700L, account.getBalance());
    }

    @Test
    public void withdrawFullBalanceAllowed() {
        account.deposit(1000);
        account.withdraw(1000);
        assertEquals(0L, account.getBalance());
    }

    @Test
    public void multipleWithdrawals() {
        account.deposit(1000);
        account.withdraw(100);
        account.withdraw(200);
        assertEquals(700L, account.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroThrows() {
        account.deposit(100);
        account.withdraw(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeThrows() {
        account.deposit(100);
        account.withdraw(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroOnEmptyAccountThrowsIllegalArgument() {
        account.withdraw(0);
    }

    @Test(expected = IllegalStateException.class)
    public void withdrawMoreThanBalanceThrows() {
        account.deposit(100);
        account.withdraw(101);
    }

    @Test(expected = IllegalStateException.class)
    public void withdrawFromEmptyAccountThrows() {
        account.withdraw(1);
    }

    @Test
    public void overdraftLeavesBalanceUnchanged() {
        account.deposit(100);
        try {
            account.withdraw(500);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // ok
        }
        assertEquals(100L, account.getBalance());
    }

    @Test
    public void invalidWithdrawLeavesBalanceUnchanged() {
        account.deposit(100);
        try {
            account.withdraw(-10);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // ok
        }
        assertEquals(100L, account.getBalance());
    }

    @Test
    public void depositAfterFailedWithdrawWorks() {
        account.deposit(50);
        try {
            account.withdraw(60);
            fail("Expected IllegalStateException");
        } catch (IllegalStateException expected) {
            // ok
        }
        account.deposit(10);
        account.withdraw(60);
        assertEquals(0L, account.getBalance());
    }

    @Test
    public void accountsAreIndependent() {
        BankAccount other = new BankAccount();
        account.deposit(100);
        assertEquals(0L, other.getBalance());
    }
}
