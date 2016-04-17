package tutwk3;

import static org.junit.Assert.*;

import org.junit.*;


public class BankAccountTest {

	@Test
	public void testBankAccount() {
		BankAccount test = new BankAccount();
		assertEquals(test.getBalance(), 0);
		assertEquals(test.getLimit(), 800);
	}

	@Test
	public void testBankAccountInt() {
		int amt = 100;
		BankAccount test = new BankAccount(amt);
		assertEquals(test.getBalance(), amt);
		assertEquals(test.getLimit(), 800);
	}

	@Test
	public void testDeposit() {
		int amt = 100;
		int deposit = 100;
		BankAccount test = new BankAccount(amt);
		assertEquals(test.getBalance(), amt);
		assertEquals(test.getLimit(), 800);
		test.deposit(deposit);
		assertEquals(test.getBalance(), amt + deposit);
	}

	@Test
	public void testWithdraw() {
		int amt = 100;
		int amtWD = 10;
		BankAccount test = new BankAccount(amt);
		assertEquals(test.getBalance(), amt);
		assertEquals(test.getLimit(), 800);
		int curLim = test.getLimit();
		test.withdraw(amtWD);
		assertEquals("testmessage", test.getBalance(), amt - amtWD);
		assertEquals(test.getLimit(), curLim - amtWD);
		test.withdraw(1000);
		assertEquals(test.getBalance(), amt - amtWD);
		assertEquals(test.getLimit(), curLim - amtWD);
	}

}
