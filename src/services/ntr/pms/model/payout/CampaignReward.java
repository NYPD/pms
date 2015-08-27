package services.ntr.pms.model.payout;

import services.ntr.pms.model.util.TimeFrame;

public class CampaignReward {

	private int id;
	private long clanId;
	private String campaignName;
	private TimeFrame timeFrame;
	private long campaignRewardTime;
	private int amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCampaignRewardId() {
		return id;
	}

	public void setCampaignRewardId(int id) {
		this.id = id;
	}

	public long getClanId() {
		return clanId;
	}

	public void setClanId(long clanId) {
		this.clanId = clanId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public TimeFrame getTimeFrame() {
		return timeFrame;
	}

	public void setTimeFrame(TimeFrame timeFrame) {
		this.timeFrame = timeFrame;
	}

	public long getCampaignRewardTime() {
		return campaignRewardTime;
	}

	public void setCampaignRewardTime(long campaignRewardTime) {
		this.campaignRewardTime = campaignRewardTime;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "CampaignReward [id=" + id + ", clanId=" + clanId + ", campaignName=" + campaignName + ", timeFrame="
				+ timeFrame + ", campaignRewardTime=" + campaignRewardTime + ", amount=" + amount + "]";
	}
}
