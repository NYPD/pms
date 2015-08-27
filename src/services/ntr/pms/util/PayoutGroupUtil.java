package services.ntr.pms.util;

import java.util.List;

import services.ntr.pms.model.payout.PayoutGroup;

public class PayoutGroupUtil{

	public static int getTotalPayoutAmount(List<PayoutGroup> payoutGroups){
		
		int totalPayoutAmount = 0;
		
		for (PayoutGroup payoutGroup : payoutGroups){
			totalPayoutAmount += payoutGroup.getTotalAmount();
		}
		
		return totalPayoutAmount;
	}
}
