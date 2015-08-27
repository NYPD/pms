package services.ntr.pms.model.payout;

public interface PayoutInformation<T> {
	T getPayoutInformation();

	void setPayoutInformation(T t);

	int getAmount();

	void setAmount(int amount);

	long getPayerAccountId();

	void setPayerAccountId(long payerId);

	String getPayerNickname();

	void setPayerNickname(String payerNickname);
}
