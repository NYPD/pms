package services.ntr.pms.model.history;

import java.sql.Timestamp;

import services.ntr.pms.service.information.Nameable;

public class TransactionHistory implements Nameable{

	private int transactionId;
	private int amount;
	private String type;
	private long payoutTime;
	private long payerAccountId;
	private String payerNickname; //This does not come from the SQL

	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getPayoutTime() {
		return payoutTime;
	}
	public void setPayoutTime(long payoutTime) {
		this.payoutTime = payoutTime;
	}
	public long getPayerAccountId() {
		return payerAccountId;
	}
	public void setPayerAccountId(long payerAccountId) {
		this.payerAccountId = payerAccountId;
	}
	public String getPayerNickname() {
		return payerNickname;
	}
	public void setPayerNickname(String payerNickname) {
		this.payerNickname = payerNickname;
	}
	
	/* Helper Methods------------------------------------------------- */
	public void setPayoutTimeUsingTimestamp(Timestamp timestamp) {
		long time = timestamp.getTime();
		long payoutTime = time;
		this.payoutTime = payoutTime;
	}
	//Used for Namable interface
	@Override
	public void setNickname(String nickname) {
		this.payerNickname = nickname;
	}
	@Override
	public String getNickname() {
		return this.payerNickname;
	}
	@Override
	public long getAccountId() {
		return this.payerAccountId;
	}
	
	@Override
	public String toString() {
		return "TransactionHistory [transactionId=" + transactionId + ", amount=" + amount + ", type=" + type
				+ ", payoutTime=" + payoutTime + ", payerAccountId=" + payerAccountId + "]";
	}
	
	

}
