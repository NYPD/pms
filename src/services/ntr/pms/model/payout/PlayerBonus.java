package services.ntr.pms.model.payout;

public class PlayerBonus {
	
	private int id;
	private long accountId;
	private long clanId;
	private String reason;
	private long playerBonusTime;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPlayerBonusId() {
		return id;
	}
	public void setPlayerBonusId(int id) {
		this.id = id;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public long getPlayerBonusTime() {
		return playerBonusTime;
	}
	public void setPlayerBonusTime(long playerBonusTime) {
		this.playerBonusTime = playerBonusTime;
	}
	
	@Override
	public String toString() {
		return "PlayerBonus [id=" + id + ", accountId=" + accountId + ", clanId=" + clanId + ", reason=" + reason
				+ ", playerBonusTime=" + playerBonusTime + "]";
	}
	
}
