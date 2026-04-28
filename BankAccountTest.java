package bankAccount;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    /**
     * Runs before each individual test method.
     * Ensures every test starts with a fresh account and a consistent balance.
     */
    @BeforeEach
    void setUp() {
        // Lab Requirement: Starts each test with a fresh account of 100.0
        account = new BankAccount(100.0);
    }

    /**
     * Add an @AfterEach annotation and method.
     * Runs after each test method to clean up resources.
     */
    @AfterEach
    void tearDown() {
        // Set the reference to null to make the object eligible for garbage collection
        account = null; 
    }

    /**
     * Test a valid deposit.
     */
    @Test
    void testDeposit() {
        account.deposit(50.0);
        // Verify that 100.0 + 50.0 results in exactly 150.0
        assertEquals(150.0, account.getBalance(), "The balance should be 150 after depositing 50");
    }

    /**
     * Test a valid withdrawal.
     */
    @Test
    void testWithdraw() {
        account.withdraw(40.0);
        // Verify that 100.0 - 40.0 results in exactly 60.0
        assertEquals(60.0, account.getBalance(), "The balance should be 60 after withdrawing 40");
    }

    /**
     * Test that an invalid (negative) deposit throws an exception.
     */
    @Test
    void testInvalidDeposit() {
        // assertThrows ensures the BankAccount logic correctly rejects negative numbers
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-10.0);
        }, "Depositing a negative amount should throw an IllegalArgumentException");
    }

    /**
     * Test that an overdraft (withdrawing more than balance) throws an exception.
     */
    @Test
    void testOverdraft() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(200.0);
        }, "Withdrawing more than the balance should throw an IllegalArgumentException");
    }

    /**
     * Test that the constructor rejects negative initial balances.
     */
    @Test
    void testNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BankAccount(-50.0);
        }, "Creating an account with a negative balance should throw an exception");
    }

    /**
     * Test the added transfer method.
     */
    @Test
    void testTransfer() {
        // Create a second account to receive the transfer
        BankAccount otherAccount = new BankAccount(50.0);
        
        // Transfer 30 from the primary account (100) to the other account (50)
        account.transfer(otherAccount, 30.0);
        
        assertEquals(70.0, account.getBalance(), "Source account should have 70 left");
        // Verify the destination account was credited
        assertEquals(80.0, otherAccount.getBalance(), "Destination account should now have 80");
    }

    /**
     * Fail if any value in an array is less than 20.
     */
    @Test
    void testArrayValues() {
        int[] values = {25, 30, 21, 40};
        for (int val : values) {
            // assertTrue will cause the test to fail if it encounters a value like 19
            assertTrue(val >= 20, "Value " + val + " was less than 20");
        }
    }

    /**
     * Pass only if two strings contain the same characters.
     */
    @Test
    void testStringEquality() {
        String strOne = "hello";
        String strTwo = new String("hello");
        
        // assertEquals checks content equality (like strOne.equals(strTwo))
        assertEquals(strOne, strTwo, "Strings must contain identical characters to pass");
    }
    
    // Question 4: Yes, the other methods will still execute if the first test fails.
}