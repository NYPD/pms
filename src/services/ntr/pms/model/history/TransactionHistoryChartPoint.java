package services.ntr.pms.model.history;

public class TransactionHistoryChartPoint {

	private String type;
	private int amount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "TransactionHistoryPieSlice [type=" + type + ", amount=" + amount + "]";
	}

}
