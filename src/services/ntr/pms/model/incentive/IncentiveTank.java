package services.ntr.pms.model.incentive;

import java.util.Comparator;
import java.util.List;

public class IncentiveTank {
	
	private static class TimesUnlockedDecendingComparator implements Comparator<IncentiveTank> {

		@Override
		public int compare(IncentiveTank incentiveTank, IncentiveTank otherIncentiveTank) {
			
			int incentiveTankTimesUnlocked = incentiveTank.getTimesUnlocked();
			int otherIncentiveTanktimesUnlocked = otherIncentiveTank.getTimesUnlocked();
			
			int compare = Integer.compare(incentiveTankTimesUnlocked, otherIncentiveTanktimesUnlocked);
			return compare;
		}
	}
	
	public static final Comparator<IncentiveTank> TIMES_UNLOCKED_ORDER = new TimesUnlockedDecendingComparator();
	private String tankUnlockedId;
	private String tankUnlockedName;
	private int timesUnlocked;
	private List<Long> tankUnlockerAccountIds;
	
	public String getTankUnlockedId() {
		return tankUnlockedId;
	}
	public void setTankUnlockedId(String tankUnlockedId) {
		this.tankUnlockedId = tankUnlockedId;
	}
	public String getTankUnlockedName() {
		return tankUnlockedName;
	}
	public void setTankUnlockedName(String tankUnlockedName) {
		this.tankUnlockedName = tankUnlockedName;
	}
	public int getTimesUnlocked() {
		return timesUnlocked;
	}
	public void setTimesUnlocked(int timesUnlocked) {
		this.timesUnlocked = timesUnlocked;
	}
	public List<Long> getTankUnlockerAccountIds() {
		return tankUnlockerAccountIds;
	}
	public void setTankUnlockerAccountIds(List<Long> tankUnlockerAccountIds) {
		this.tankUnlockerAccountIds = tankUnlockerAccountIds;
	}
	
	@Override
	public String toString() {
		return "IncentiveTank [tankUnlockedId=" + tankUnlockedId + ", tankUnlockedName=" + tankUnlockedName
				+ ", timesUnlocked=" + timesUnlocked + "]";
	}
	
	
}
