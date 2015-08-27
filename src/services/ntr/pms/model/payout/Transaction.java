package services.ntr.pms.model.payout;

public class Transaction {

	private int id;
	private long clanId;
	private long transactionTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransactionId() {
		return id;
	}

	public void setTransactionId(int id) {
		this.id = id;
	}

	public long getClanId() {
		return clanId;
	}

	public void setClanId(long clanId) {
		this.clanId = clanId;
	}

	public long getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(long transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", clanId=" + clanId + ", transactionTime=" + transactionTime + "]";
	}
}
