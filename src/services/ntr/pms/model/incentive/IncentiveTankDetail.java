package services.ntr.pms.model.incentive;

import java.util.List;

public class IncentiveTankDetail {
	
	private int tankUnlockedId;
	private String tankUnlockedName;
	private List<Long> tankUnlockerAccountIds;
	private int totalGoldGiven;
	
	private List<String> tankUnlockerNicknames; //Not from the database
	
	public int getTankUnlockedId() {
		return tankUnlockedId;
	}
	public void setTankUnlockedId(int tankUnlockedId) {
		this.tankUnlockedId = tankUnlockedId;
	}
	public String getTankUnlockedName() {
		return tankUnlockedName;
	}
	public void setTankUnlockedName(String tankUnlockedName) {
		this.tankUnlockedName = tankUnlockedName;
	}
	public int getTotalGoldGiven() {
		return totalGoldGiven;
	}
	public void setTotalGoldGiven(int totalGoldGiven) {
		this.totalGoldGiven = totalGoldGiven;
	}
	public List<Long> getTankUnlockerAccountIds() {
		return tankUnlockerAccountIds;
	}
	public void setTankUnlockerAccountIds(List<Long> tankUnlockerAccountIds) {
		this.tankUnlockerAccountIds = tankUnlockerAccountIds;
	}
	public List<String> getTankUnlockerNicknames() {
		return tankUnlockerNicknames;
	}
	public void setTankUnlockerNicknames(List<String> tankUnlockerNicknames) {
		this.tankUnlockerNicknames = tankUnlockerNicknames;
	}
	
	@Override
	public String toString() {
		return "IncentiveTankDetail [tankUnlockedId=" + tankUnlockedId + ", tankUnlockedName=" + tankUnlockedName
				+ ", tankUnlockerAccountIds=" + tankUnlockerAccountIds + ", totalGoldGiven=" + totalGoldGiven
				+ ", tankUnlockerNicknames=" + tankUnlockerNicknames + "]";
	}
	
}
