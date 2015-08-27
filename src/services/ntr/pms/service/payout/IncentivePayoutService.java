package services.ntr.pms.service.payout;

import java.util.List;

import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.IncentiveCompletion;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;

public interface IncentivePayoutService{
	
	void payout(PayoutInformation<IncentiveCompletion> incentivePayout);

	List<PayoutGroup> getPayoutGroups(PayoutInformation<IncentiveCompletion> payoutInformation);
	List<Payout> getPayoutByPlayer(Player player);
}