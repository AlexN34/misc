package tutwk3;
import java.util.Calendar;
public class InternetAccount extends BankAccount {
	private int internetWithdrawal;
	private Calendar lastInternetWithdrawal;

	@Override
	public void withdraw(int amt) {
		Calendar today = Calendar.getInstance();
		if (this.lastInternetWithdrawal == null)
			this.lastInternetWithdrawal = Calendar.getInstance();
		if (this.lastInternetWithdrawal.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
			if (this.internetWithdrawal < 10) {
				super.withdraw(amt);
				this.internetWithdrawal++;
			}
		} else {
			this.internetWithdrawal = 0;
		}
	}
}