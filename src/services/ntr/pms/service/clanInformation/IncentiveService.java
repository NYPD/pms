package services.ntr.pms.service.clanInformation;

import java.util.List;
import java.util.Map;

import services.ntr.pms.model.incentive.IncentiveSummaryTableRow;
import services.ntr.pms.model.incentive.IncentivePlayer;
import services.ntr.pms.model.incentive.IncentiveTank;
import services.ntr.pms.model.incentive.IncentiveTankDetail;
import services.ntr.pms.model.incentive.IncentivePayoutAmountsTableRow;
import services.ntr.pms.model.incentive.TankIncentiveDefaultPayout;
import services.ntr.pms.model.information.TankInformation;

public interface IncentiveService {

	List<IncentiveTankDetail> getClanIncentiveTankDetails(long clanId);
	List<IncentivePlayer> getLimitedTopIncentivePlayersByClan(long clanId, int limit);
	List<IncentiveTank> getLimitedTopTanksUnlockedByClan(long clanId, int limit);
	
	List<IncentiveSummaryTableRow> getIncentiveSummaryTableRows(long clanId);
	
	List<IncentivePayoutAmountsTableRow> getClanTankIncentiveAmountTableRows(long clanId);
	List<IncentivePayoutAmountsTableRow> getSpecificTankIncentiveAmountTableRows(List<TankInformation> tankInformationList, long clanId);
	
	Map<Integer, TankIncentiveDefaultPayout> getTankIncentiveDefaultPayoutsAsMap(long clanId);
	
	void saveTankIncentiveDefaultPayouts(List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayouts , long clanId);
}
