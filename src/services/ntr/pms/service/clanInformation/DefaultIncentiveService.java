package services.ntr.pms.service.clanInformation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.TankIncentiveDAO;
import services.ntr.pms.model.incentive.IncentiveSummaryTableRow;
import services.ntr.pms.model.incentive.IncentivePlayer;
import services.ntr.pms.model.incentive.IncentiveTank;
import services.ntr.pms.model.incentive.IncentiveTankDetail;
import services.ntr.pms.model.incentive.IncentivePayoutAmountsTableRow;
import services.ntr.pms.model.incentive.TankIncentiveDefaultPayout;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanMember;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.TankInformation;
import services.ntr.pms.service.information.TankInformationService;

@Service
public class DefaultIncentiveService implements IncentiveService {

	@Autowired
	private TankIncentiveDAO tankIncentiveDAO;
	@Autowired
	private TankInformationService tankInformationService;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public List<IncentiveTankDetail> getClanIncentiveTankDetails(long clanId) {
		
		ClanInfo clanInformation = apiRequestDAO.getClanInformation(clanId);
		Map<Long, ClanMember> clanMemberInformationMap = clanInformation.getClanMemberInformationMap();
		
		List<IncentiveTankDetail> clansIncentiveCompletionsByTank = tankIncentiveDAO.getClanIncentiveTankDetails(clanId);

		for (IncentiveTankDetail incentiveTankDetail : clansIncentiveCompletionsByTank) {
			
			List<Long> tankUnlockerAccountIds = incentiveTankDetail.getTankUnlockerAccountIds();
			List<Long> exTankUnlockerAccountIds = new ArrayList<>();
			
			List<String> tankUnlockerAccountName = new ArrayList<>();
			incentiveTankDetail.setTankUnlockerNicknames(tankUnlockerAccountName);
			
			for (Long accountId : tankUnlockerAccountIds) {
				
				boolean clanMemberMapContainsAccount = clanMemberInformationMap.containsKey(accountId);
				
				if(clanMemberMapContainsAccount) {
					
					String accountName = clanMemberInformationMap.get(accountId).getAccountName();
					
					tankUnlockerAccountName.add(accountName);
					
				} else exTankUnlockerAccountIds.add(accountId);
				
			}
			
			boolean noExTankUnlockers = exTankUnlockerAccountIds.size() == 0;
			
			if(noExTankUnlockers) continue;
			
			Map<Long, Player> exPlayerInformationMap = apiRequestDAO.getPlayerInformationMap(exTankUnlockerAccountIds);
			
			Collection<Player> exPlayers = exPlayerInformationMap.values();
			
			for (Player exPlayer : exPlayers) {
				
				String exPlayerNickname = exPlayer.getNickname();
				
				tankUnlockerAccountName.add(exPlayerNickname);
			}
			
		}
		
		return clansIncentiveCompletionsByTank;
	}
	
	@Override
	public List<IncentivePlayer> getLimitedTopIncentivePlayersByClan(long clanId, int limit) {
		
		ClanInfo clanInformation = apiRequestDAO.getClanInformation(clanId);
		Map<Long, ClanMember> clanMemberInformationMap = clanInformation.getClanMemberInformationMap();
		
		List<IncentivePlayer> topIncentivePlayers = tankIncentiveDAO.getTopIncentivePlayersByClan(clanId);
		List<IncentivePlayer> topLimitedIncentivePlayers = new ArrayList<>();
		
		for (IncentivePlayer incentivePlayer : topIncentivePlayers) {
			
			long accountId = incentivePlayer.getAccountId();
			
			boolean clanMemberMapContainsAccount = clanMemberInformationMap.containsKey(accountId);
			
			if(clanMemberMapContainsAccount) {
				
				String accountName = clanMemberInformationMap.get(accountId).getAccountName();
				
				incentivePlayer.setNickname(accountName);
				
				topLimitedIncentivePlayers.add(incentivePlayer);
				
				boolean limitReached = topLimitedIncentivePlayers.size() == limit;
				
				if(limitReached) break;
				
			} else continue;
			
		}
		
		return topLimitedIncentivePlayers;
	}

	@Override
	public List<IncentiveTank> getLimitedTopTanksUnlockedByClan(long clanId, int limit) {

		ClanInfo clanInformation = apiRequestDAO.getClanInformation(clanId);
		Map<Long, ClanMember> clanMemberInformationMap = clanInformation.getClanMemberInformationMap();
		
		List<IncentiveTank> topTanksUnlockedByClan = tankIncentiveDAO.getTopTanksUnlockedByClan(clanId);
		
		for (IncentiveTank incentiveTank : topTanksUnlockedByClan) {
			
			int timesUnlocked = incentiveTank.getTimesUnlocked();
			
			List<Long> tankUnlockerAccountIds = incentiveTank.getTankUnlockerAccountIds();
			
			for (Long tankUnlockerAccountId : tankUnlockerAccountIds) {
				
				boolean accountIdIsInNotInMap = !clanMemberInformationMap.containsKey(tankUnlockerAccountId);
				
				if(accountIdIsInNotInMap) timesUnlocked -= 1;
				
			}
			
			incentiveTank.setTimesUnlocked(timesUnlocked);
			
		}
		
		Collections.sort(topTanksUnlockedByClan, Collections.reverseOrder(IncentiveTank.TIMES_UNLOCKED_ORDER));
		List<IncentiveTank> topLimitedTanksUnlockedByClan = new ArrayList<>();
		
		for (IncentiveTank incentiveTank : topTanksUnlockedByClan) {
			
			topLimitedTanksUnlockedByClan.add(incentiveTank);
			
			boolean limitReached = topLimitedTanksUnlockedByClan.size() == limit;
			
			if(limitReached) break;
			
		}
		
		return topLimitedTanksUnlockedByClan;
	}

	@Override
	public List<IncentivePayoutAmountsTableRow> getClanTankIncentiveAmountTableRows(long clanId) {
		
		List<IncentivePayoutAmountsTableRow> incentivePayoutAmountsTableRows = new ArrayList<>();
		
		Map<Integer, TankIncentiveDefaultPayout> tankIncentiveDefaultPayouts = tankIncentiveDAO.getTankIncentiveDefaultPayouts(clanId);
		Map<Integer, TankInformation> tankInformationMap = tankInformationService.getTankInformationMap();
		
		Collection<TankIncentiveDefaultPayout> tankIncentiveDefaultPayoutsCollection = tankIncentiveDefaultPayouts.values();
		
		for (TankIncentiveDefaultPayout tankIncentiveDefaultPayout : tankIncentiveDefaultPayoutsCollection) {
			
			int tankId = tankIncentiveDefaultPayout.getTankId();
			boolean doesNotContainKey = !tankInformationMap.containsKey(tankId);
			
			if(doesNotContainKey) continue;
			
			TankInformation tankInformation = tankInformationMap.get(tankId);
			
			IncentivePayoutAmountsTableRow incentivePayoutAmountsTableRow = new IncentivePayoutAmountsTableRow();
			
			incentivePayoutAmountsTableRow.setTankIncentiveDefaultPayout(tankIncentiveDefaultPayout);
			incentivePayoutAmountsTableRow.setTankInformation(tankInformation);
			
			incentivePayoutAmountsTableRows.add(incentivePayoutAmountsTableRow);
		}
		
		return incentivePayoutAmountsTableRows;
	}
	
	@Override
	public Map<Integer, TankIncentiveDefaultPayout> getTankIncentiveDefaultPayoutsAsMap(long clanId) {
		
		Map<Integer, TankIncentiveDefaultPayout> tankIncentiveDefaultPayoutMap = tankIncentiveDAO.getTankIncentiveDefaultPayouts(clanId);
		
		return tankIncentiveDefaultPayoutMap;
	}

	@Override
	public List<IncentiveSummaryTableRow> getIncentiveSummaryTableRows(long clanId) {
		
		List<IncentiveSummaryTableRow> incentiveSummaryTableRows = new ArrayList<>();
		
		Map<Integer, TankInformation> tankInformationMap = tankInformationService.getTankInformationMap();
		
		List<IncentiveTankDetail> clanIncentiveTankDetails = getClanIncentiveTankDetails(clanId);
		
		for (IncentiveTankDetail incentiveTankDetail : clanIncentiveTankDetails) {
			
			IncentiveSummaryTableRow incentiveSummaryTableRow = new IncentiveSummaryTableRow();
			
			int tankId = incentiveTankDetail.getTankUnlockedId();
			
			boolean tankInformationMapDoesNotContainKey = !tankInformationMap.containsKey(tankId);
			
			if(tankInformationMapDoesNotContainKey) continue;
			
			TankInformation tankInformation = tankInformationMap.get(tankId);
			
			incentiveSummaryTableRow.setIncentiveTankDetail(incentiveTankDetail);
			incentiveSummaryTableRow.setTankInformation(tankInformation);
			
			incentiveSummaryTableRows.add(incentiveSummaryTableRow);
			
		}
		
		return incentiveSummaryTableRows;
	}
	
	@Override
	public List<IncentivePayoutAmountsTableRow> getSpecificTankIncentiveAmountTableRows(List<TankInformation> tankInformationList, long clanId) {
		
		List<IncentivePayoutAmountsTableRow> incentivePayoutAmountsTableRows = new ArrayList<>();
		Map<Integer, TankIncentiveDefaultPayout> tankIncentiveDefaultPayoutMap = getTankIncentiveDefaultPayoutsAsMap(clanId);
		
		boolean clanHasNoDefaultIncentiveAmounts = tankIncentiveDefaultPayoutMap == null;
		
		if(clanHasNoDefaultIncentiveAmounts) tankIncentiveDefaultPayoutMap = new HashMap<Integer, TankIncentiveDefaultPayout>();
		
		for (TankInformation tankInformation : tankInformationList) {
			
			TankIncentiveDefaultPayout tankIncentiveDefaultPayout;
			
			int tankId = tankInformation.getTankId();
			boolean containsKey = tankIncentiveDefaultPayoutMap.containsKey(tankId);
			
			tankIncentiveDefaultPayout = containsKey ? tankIncentiveDefaultPayoutMap.get(tankId) : new TankIncentiveDefaultPayout();
			
			IncentivePayoutAmountsTableRow incentivePayoutAmountsTableRow = new IncentivePayoutAmountsTableRow();
			
			incentivePayoutAmountsTableRow.setTankIncentiveDefaultPayout(tankIncentiveDefaultPayout);
			incentivePayoutAmountsTableRow.setTankInformation(tankInformation);
			
			incentivePayoutAmountsTableRows.add(incentivePayoutAmountsTableRow);
			
		}
		
		return incentivePayoutAmountsTableRows;
	}
	
	@Override
	public void saveTankIncentiveDefaultPayouts(List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayouts, long clanId) {
		
		List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayoutUpdates = new ArrayList<>();
		List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayoutInserts = new ArrayList<>();
		
		for (TankIncentiveDefaultPayout tankIncentiveDefaultPayout : tankIncentiveDefaultPayouts) {
			
			tankIncentiveDefaultPayout.setClanId(clanId);
			
			int tankIncentiveDefaultPayoutId = tankIncentiveDefaultPayout.getTankIncentiveDefaultPayoutId();
			
			boolean isUpdate = tankIncentiveDefaultPayoutId > 0;
			
			if(isUpdate) tankIncentiveDefaultPayoutUpdates.add(tankIncentiveDefaultPayout); 
			else tankIncentiveDefaultPayoutInserts.add(tankIncentiveDefaultPayout);
			
		}
		
		boolean notEmptyInsertList = tankIncentiveDefaultPayoutInserts.size() > 0;
		boolean notEmptyUpdateList = tankIncentiveDefaultPayoutUpdates.size() > 0;
		
		if(notEmptyInsertList) tankIncentiveDAO.insertTankIncentiveDefaultPayouts(tankIncentiveDefaultPayoutInserts);
		if(notEmptyUpdateList) tankIncentiveDAO.updateTankIncentiveDefaultPayouts(tankIncentiveDefaultPayoutUpdates);
		
	}

}
