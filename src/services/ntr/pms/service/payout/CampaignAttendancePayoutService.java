package services.ntr.pms.service.payout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.exception.NoAttendencesToPayoutException;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanMember;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.CampaignReward;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.PlayerPayoutSummary;
import services.ntr.pms.model.payout.PointSummary;
import services.ntr.pms.model.payout.Transaction;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.information.NamingService;
import services.ntr.pms.util.PayoutGroupUtil;

@Service
public class CampaignAttendancePayoutService implements AttendancePayoutService<CampaignReward> {
	
	@Autowired
	private PayoutDAO payoutDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	private static final String TYPE = "Campaign Attendance";
	
	@Override
	public List<PayoutGroup> getPayoutGroups(PayoutInformation<CampaignReward> payoutInformation) {	
		
		CampaignReward campaignReward = payoutInformation.getPayoutInformation();
		TimeFrame timeFrame = campaignReward.getTimeFrame();
		
		int payoutAmount = payoutInformation.getAmount();
		long clanId = campaignReward.getClanId();
		
		List<PointSummary> pointSummaries = getPointSummariesByTimeFrame(clanId, timeFrame);
		
		int shares = getShares(pointSummaries);
		
		boolean noSharesToPayout = shares == 0;
		
		if(noSharesToPayout) throw new NoAttendencesToPayoutException();
		
		int goldPerShare = payoutAmount / shares;
		
		List<PlayerPayoutSummary> playerPayoutSummaries = extractPayoutSummary(pointSummaries, goldPerShare, payoutInformation);
		
		Map<Integer, PayoutGroup> payoutGroupMappedByAmount = getPayoutGroupMappedByAmount(playerPayoutSummaries);
		
		Collection<PayoutGroup> payoutGroupCollection = payoutGroupMappedByAmount.values();
		List<PayoutGroup> payoutGroups = new ArrayList<>(payoutGroupCollection);
		Comparator<Object> reverseOrder = Collections.reverseOrder();
		Collections.sort(payoutGroups, reverseOrder);  
		
		return payoutGroups;
		
	}
	
	@Override
	public void payout(PayoutInformation<CampaignReward> payoutInformation) {
		List<PayoutGroup> payoutGroups = getPayoutGroups(payoutInformation);
		CampaignReward campaignReward = payoutInformation.getPayoutInformation();
		
		int totalPayoutAmount = PayoutGroupUtil.getTotalPayoutAmount(payoutGroups);
		
		campaignReward.setAmount(totalPayoutAmount);
		
		addCampaignReward(campaignReward);
		
		int campaignRewardId = campaignReward.getCampaignRewardId();
		long clanId = campaignReward.getClanId();
		
		Transaction transaction = new Transaction();
		transaction.setClanId(clanId);
		
		addTransaction(transaction);
		
		int transactionId = transaction.getTransactionId();
		long payoutTime = System.currentTimeMillis();
		
		for (PayoutGroup payoutGroup : payoutGroups) {
			long payerAccountId = payoutInformation.getPayerAccountId();
			int amount = payoutGroup.getAvgAmount();
			Payout payout = new Payout();
			payout.setAmount(amount);
			payout.setType(TYPE);
			payout.setPayerAccountId(payerAccountId);
			payout.setTransactionId(transactionId);
			payout.setPayoutTime(payoutTime);
			
			addPayout(payout);
			
			int payoutId = payout.getPayoutId();
			
			List<Long> playerAccountIds = payoutGroup.getPlayerAccountIds(); 
			
			for (Long accountId : playerAccountIds)
			{
				addCampaignRewardPayout(campaignRewardId, payoutId, accountId);
			}
		}
	}
	
	@Override
	public int getShares(CampaignReward campaignRewardint) {
		TimeFrame timeFrame = campaignRewardint.getTimeFrame();
		long clanId = campaignRewardint.getClanId();
		
		List<PointSummary> pointSummaries = getPointSummariesByTimeFrame(clanId, timeFrame);
		int shares = getShares(pointSummaries);
		
		return shares;
	}
	@Override
	public List<Payout> getPayoutByPlayer(Player player) {
		return payoutDAO.getPayoutsByPlayer(player);
	}
	
	//Helper Methods
	private Map<Integer, PayoutGroup> getPayoutGroupMappedByAmount(List<PlayerPayoutSummary> playerPayoutSummaries) {
		Map<Integer, PayoutGroup> payoutGroupMappedByAmount = new HashMap<Integer, PayoutGroup>();

		for (PlayerPayoutSummary playerPayoutSummary : playerPayoutSummaries)
		{
			int amount = playerPayoutSummary.getAmount();
			boolean doesNotContainsKey = !payoutGroupMappedByAmount.containsKey(amount);

			if(doesNotContainsKey) payoutGroupMappedByAmount.put(amount, new PayoutGroup());

			PayoutGroup payoutGroup = payoutGroupMappedByAmount.get(amount);
			payoutGroup.addPayoutSummary(playerPayoutSummary);
		}
		
		return payoutGroupMappedByAmount;
	}
	
	private List<PlayerPayoutSummary> extractPayoutSummary(List<PointSummary> pointSummaries, int goldPerShare, PayoutInformation<CampaignReward>playerPayout) {
		
		List<PlayerPayoutSummary> playerPayoutSummaries = new ArrayList<>();
		
		for (PointSummary pointSummary : pointSummaries)
		{
			int points = pointSummary.getPoints();
			int amount = goldPerShare * points;
			long accountId = pointSummary.getAccountId();
			String nickname = pointSummary.getNickname();
			
			PlayerPayoutSummary playerPayoutSummary = new PlayerPayoutSummary();
			
			playerPayoutSummary.setAmount(amount);
			playerPayoutSummary.setNickname(nickname);
			playerPayoutSummary.setAccountId(accountId);
			
			playerPayoutSummaries.add(playerPayoutSummary);
		}
		return playerPayoutSummaries;
	}
	
	private int getShares(List<PointSummary> pointSummaries) {
		int shares = 0;
		
		for (PointSummary pointSummary : pointSummaries)
		{
			int points = pointSummary.getPoints();
			shares += points;
		}
		
		return shares;
	}
	
	private List<PointSummary> getPointSummariesByTimeFrame(long clanId, TimeFrame timeFrame) {
		
		List<PointSummary> pointSummaries = payoutDAO.getPointSummariesByTimeFrame(clanId, timeFrame);
		
		ClanInfo clanInformation = apiRequestDAO.getClanInformation(clanId);
		Map<Long, ClanMember> clanMembersAsMap = clanInformation.getClanMemberInformationMap();
		
		setPointsToZeroForMembersNotInClan(clanMembersAsMap, pointSummaries);
		
		namingService.name(pointSummaries);
		
		return pointSummaries;
		
	}
	
	private void setPointsToZeroForMembersNotInClan(Map<Long, ClanMember> clanMembers, List<PointSummary> pointSummaries) {
		
		for (PointSummary pointSummary : pointSummaries) {
			
			long accountId = pointSummary.getAccountId();
			boolean isNotClanMember = !clanMembers.containsKey(accountId);
			if(isNotClanMember) pointSummary.setPoints(0);
		}
	}
	
	private void addTransaction(Transaction transaction) {
		payoutDAO.addTransaction(transaction);
	}
	
	private void addCampaignReward(CampaignReward campaignReward) {
		payoutDAO.addCampaignReward(campaignReward);
	}

	private void addCampaignRewardPayout(int campaignRewardId, int payoutId, long accountId) {
		payoutDAO.addCampaignRewardPayout(campaignRewardId, payoutId, accountId);
	}
	
	private void addPayout(Payout payout) {
		payoutDAO.addPayout(payout);
	}
	
	public PayoutDAO getPayoutDAO() {
		return payoutDAO;
	}
	public void setPayoutDAO(PayoutDAO payoutDAO) {
		this.payoutDAO = payoutDAO;
	}
}
