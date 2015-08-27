package services.ntr.pms.model.payout;

public class PlayerPayoutSummary implements Comparable<PlayerPayoutSummary>  {

	private long accountId;
	private String nickname;
	private int amount;

	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int compareTo(PlayerPayoutSummary otherPlayerPayoutSummary) {

		String otherPlayerNickname = otherPlayerPayoutSummary.getNickname();
		String thisPlayerNickname = this.getNickname();
		
		int compare = String.CASE_INSENSITIVE_ORDER.compare(thisPlayerNickname, otherPlayerNickname);
		
		return compare;
	}

	@Override
	public String toString() {
		return "PlayerPayoutSummary [accountId=" + accountId + ", nickname=" + nickname + ", amount=" + amount + "]";
	}
}
