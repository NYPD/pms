package services.ntr.pms.service.payout;

import java.util.List;

import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;

public interface AttendancePayoutService<T> {

	List<PayoutGroup> getPayoutGroups(PayoutInformation<T> payoutInformation);
	int getShares(T payoutInformation);
	void payout(PayoutInformation<T> payoutInformation);
	List<Payout> getPayoutByPlayer(Player player);
}
