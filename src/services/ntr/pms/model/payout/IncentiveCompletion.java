package services.ntr.pms.model.payout;

public class IncentiveCompletion {
	
	private int id;
	private long accountId;
	private long clanId;
	private long incentiveTime;
	private String tankUnlockedName;
	private int tankUnlockedId;

	public int getId() {
		return id;
	}
	public int getIncentiveId() {
		return id;
	}
	public void setIncentiveId(int id) {
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
	public long getIncentiveTime() {
		return incentiveTime;
	}
	public void setIncentiveTime(long incentiveTime) {
		this.incentiveTime = incentiveTime;
	}
	public String getTankUnlockedName() {
		return tankUnlockedName;
	}
	public void setTankUnlockedName(String tankUnlockedName) {
		this.tankUnlockedName = tankUnlockedName;
	}
	public int getTankUnlockedId() {
		return tankUnlockedId;
	}
	public void setTankUnlockedId(int tankUnlockedId) {
		this.tankUnlockedId = tankUnlockedId;
	}
	
	@Override
	public String toString() {
		return "IncentiveCompletion [id=" + id + ", accountId=" + accountId + ", clanId=" + clanId + ", incentiveTime="
				+ incentiveTime + ", tankUnlockedName=" + tankUnlockedName + ", tankUnlockedId=" + tankUnlockedId + "]";
	}
	
}
