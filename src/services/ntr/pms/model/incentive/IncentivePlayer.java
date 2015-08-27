package services.ntr.pms.model.incentive;

public class IncentivePlayer {

	private long accountId;
	private int tanksUnlocked;
	private int totalGoldAmount;
	private String nickname;//Not from the database
	
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public int getTanksUnlocked() {
		return tanksUnlocked;
	}
	public void setTanksUnlocked(int tanksUnlocked) {
		this.tanksUnlocked = tanksUnlocked;
	}
	public int getTotalGoldAmount() {
		return totalGoldAmount;
	}
	public void setTotalGoldAmount(int totalGoldAmount) {
		this.totalGoldAmount = totalGoldAmount;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "IncentivePlayer [accountId=" + accountId + ", tanksUnlocked=" + tanksUnlocked + ", totalGoldAmount="
				+ totalGoldAmount + ", nickname=" + nickname + "]";
	}

}
