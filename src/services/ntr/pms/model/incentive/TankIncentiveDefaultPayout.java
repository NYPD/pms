package services.ntr.pms.model.incentive;

public class TankIncentiveDefaultPayout {
	
	private int tankIncentiveDefaultPayoutId;
	private int tankId;
	private long clanId;
	private int amount;
	
	public int getTankIncentiveDefaultPayoutId() {
		return tankIncentiveDefaultPayoutId;
	}
	public void setTankIncentiveDefaultPayoutId(int tankIncentiveDefaultPayoutId) {
		this.tankIncentiveDefaultPayoutId = tankIncentiveDefaultPayoutId;
	}
	public int getTankId() {
		return tankId;
	}
	public void setTankId(int tankId) {
		this.tankId = tankId;
	}
	public long getClanId() {
		return clanId;
	}
	public void setClanId(long clanId) {
		this.clanId = clanId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "TankIncentiveDefaultPayout [tankIncentiveDefaultPayoutId=" + tankIncentiveDefaultPayoutId + ", tankId="
				+ tankId + ", clanId=" + clanId + ", amount=" + amount + "]";
	}
	
}
