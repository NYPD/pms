package services.ntr.pms.model.payout;

import java.util.ArrayList;
import java.util.List;

public class PayoutGroup implements Comparable<PayoutGroup> {
	
	private List<PlayerPayoutSummary> playerPayoutSummaries;

	public List<PlayerPayoutSummary> getPlayerPayoutSummaries() {
		return playerPayoutSummaries;
	}
	
	public void setPlayerPayoutSummaries(List<PlayerPayoutSummary> playerPayoutSummaries) {
		this.playerPayoutSummaries = playerPayoutSummaries;
	}
	
	public int getAvgAmount() {

		int totalAmount = getTotalAmount();
		int numberOfPlayerSummaries = playerPayoutSummaries.size();

		int avgAmount = totalAmount / numberOfPlayerSummaries;

		return avgAmount;
	}
	
	public int getTotalAmount() {

		int totalAmount = 0;

		for (PlayerPayoutSummary playerPayoutSummary : playerPayoutSummaries) {

			totalAmount += playerPayoutSummary.getAmount();
		}
		return totalAmount;
	}
	
	public void addPayoutSummary(PlayerPayoutSummary payoutSummary) {
		setEmptyListIfApplicable();
		this.playerPayoutSummaries.add(payoutSummary);
	}
	
	private void setEmptyListIfApplicable() {
		boolean playerPayoutSummaryListIsNull = playerPayoutSummaries == null;
		if (playerPayoutSummaryListIsNull)
			playerPayoutSummaries = new ArrayList<PlayerPayoutSummary>();
	}
	
	public List<Long> getPlayerAccountIds() {
		List<Long> playerAccountIds = new ArrayList<>();
		List<PlayerPayoutSummary> playerPayoutSummaries = getPlayerPayoutSummaries();
		for (PlayerPayoutSummary playerPayoutSummary : playerPayoutSummaries) {
			long accountId = playerPayoutSummary.getAccountId();
			playerAccountIds.add(accountId);
		}

		return playerAccountIds;
	}
	public boolean containsPlayerAccountId(long accountId) {
		List<Long> playerAccountIds = getPlayerAccountIds();
		boolean containsPlayerAccountId = playerAccountIds.contains(accountId);
		return containsPlayerAccountId;
	}
	
	@Override
	public int compareTo(PayoutGroup otherPayoutGroup) {

		int otherPayoutGroupAvgAmount = otherPayoutGroup.getAvgAmount();
		int payoutGroupAvgAmount = this.getAvgAmount();

		int compare = Integer.compare(payoutGroupAvgAmount, otherPayoutGroupAvgAmount);
		return compare;
	}

	@Override
	public String toString() {
		return "PayoutGroup [playerPayoutSummaries=" + playerPayoutSummaries + " totalAmount=" + getTotalAmount()
				+ " avgAmount=" + getAvgAmount() + "]";
	}

	

}
