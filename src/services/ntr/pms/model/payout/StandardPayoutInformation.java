package services.ntr.pms.model.payout;

import org.springframework.format.annotation.NumberFormat;

public class StandardPayoutInformation<T> implements PayoutInformation<T> {

	private T payoutInformation;
	@NumberFormat
	private int amount;
	private long payerAccountId;
	private String payerNickname;

	@Override
	public T getPayoutInformation() {
		return payoutInformation;
	}
	@Override
	public void setPayoutInformation(T payoutInformation) {
		this.payoutInformation = payoutInformation;
	}
	@Override
	public int getAmount() {
		return amount;
	}
	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public long getPayerAccountId() {
		return payerAccountId;
	}
	@Override
	public void setPayerAccountId(long payerId) {
		this.payerAccountId = payerId;
	}
	@Override
	public String getPayerNickname() {
		return payerNickname;
	}
	@Override
	public void setPayerNickname(String payerNickname) {
		this.payerNickname = payerNickname;
	}

	@Override
	public String toString() {
		return "StandardPayoutInformation [payoutInformation=" + payoutInformation + ", amount=" + amount
				+ ", payerAccountId=" + payerAccountId + ", payerNickname=" + payerNickname + "]";
	}

}
