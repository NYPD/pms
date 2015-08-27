package services.ntr.pms.model.payout;

import java.sql.Timestamp;

import services.ntr.pms.service.information.Nameable;

public class Payout implements Nameable {

	private int id;
	private int transactionId;
	private int amount;
	private String type;
	private long payerAccountId;
	private long payoutTime;
	private String payerNickname;//Does not come from SQL

	public int getPayoutId() {
		return id;
	}
	public void setPayoutId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public long getPayerAccountId() {
		return payerAccountId;
	}
	public void setPayerAccountId(long payerAccountId) {
		this.payerAccountId = payerAccountId;
	}
	public long getPayoutTime() {
		return payoutTime;
	}
	public void setPayoutTime(long payoutTime) {
		this.payoutTime = payoutTime;
	}
	public String getPayerNickname() {
		return payerNickname;
	}
	public void setPayerNickname(String payerNickname) {
		this.payerNickname = payerNickname;
	}
	
	/* Helper Methods----------------------------------------------------------------------------------------- */
	public void setPayoutTimeUsingTimestamp(Timestamp timestamp) {
		this.payoutTime = timestamp.getTime();
	}
	public Timestamp getPayoutTimeAsTimestamp() {
		Timestamp payoutTimeAsTimestamp = new Timestamp(payoutTime);
		return payoutTimeAsTimestamp;
	}
	// Used for Nameable interface
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
		return "Payout [id=" + id + ", transactionId=" + transactionId + ", amount=" + amount + ", type=" + type
				+ ", payerAccountId=" + payerAccountId + ", payoutTime=" + payoutTime + ", payerNickname="
				+ payerNickname + "]";
	}

}
