package services.ntr.pms.model.history;

public class PayoutMonthSummary {

	private int month;
	private int amount;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PayoutMonthSummary [month=" + month + ", monthAmount=" + amount + "]";
	}
}
