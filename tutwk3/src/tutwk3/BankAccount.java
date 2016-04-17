package tutwk3;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class BankAccount {
	private int balance;
	private int limit;
	private Calendar lastWithdraw;

	public int getBalance() {
		return this.balance;
	}

	public int getLimit() {
		return this.limit;
	}

	public Calendar getLastWithdraw() {
		return this.lastWithdraw;
	}

	public BankAccount() {
		this.balance = 0;
		this.limit = 800;
	}
	
	public BankAccount(int startBalance) {
		this.balance = startBalance;
		this.limit = 800;
	}
	
	public void deposit(int amt) {
		this.balance += amt;
	}
	
	public void withdraw(int amt) {
		if (this.lastWithdraw == null)
			this.lastWithdraw = Calendar.getInstance();
		limitReset(this);
		if (checkLimit(this, amt)) {
			this.balance -= amt;
			this.limit -= amt;
			this.lastWithdraw = new GregorianCalendar();
		} //somehow turn into exception if fails?
		
	}

	private boolean checkLimit(BankAccount b, int amt) {
		return b.limit >= amt && b.balance > amt;
	}
	
	////Checks for whether limit needs to be reset
	private void limitReset(BankAccount b) {
		GregorianCalendar curDate = new GregorianCalendar();
		b.lastWithdraw.roll(GregorianCalendar.DAY_OF_MONTH, 1);
		//not correct - wont be exactly the same; time will be off.
		//Calendar.DAY_OF_MONTH as an argument works; static variable. 
		//you tell java what field to access so either Calendar or c will work
		if (curDate.get(Calendar.DAY_OF_MONTH) == this.lastWithdraw.get(Calendar.DAY_OF_MONTH)) {
			this.limit = 800;
		}
	}
}
