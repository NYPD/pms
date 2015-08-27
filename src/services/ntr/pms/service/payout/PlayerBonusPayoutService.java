package services.ntr.pms.service.payout;

import java.util.List;

import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.PlayerBonus;

public interface PlayerBonusPayoutService
{
	void payout(PayoutInformation<PlayerBonus> payoutInformation);

	List<PayoutGroup> getPayoutGroups(PayoutInformation<PlayerBonus> payoutInformation);

	List<Payout> getPayoutByPlayer(Player player);
}