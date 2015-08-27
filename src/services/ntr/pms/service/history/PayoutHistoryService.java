package services.ntr.pms.service.history;

import java.util.List;

import services.ntr.pms.model.history.PayoutMonthSummary;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.util.TimeFrame;

public interface PayoutHistoryService{

	List<Payout> getPlayerPayouts(Player player);
	List<PayoutMonthSummary> getMonthlyPayoutHistory(Player player, TimeFrame timeFrame);
	int[] getMonthlyPayoutHistoryAmounts(Player player, TimeFrame timeFrame);
	
}
