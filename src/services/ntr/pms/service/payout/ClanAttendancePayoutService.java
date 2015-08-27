package services.ntr.pms.service.payout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.exception.NoAttendencesToPayoutException;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanMember;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.ClanAccount;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.PlayerPayoutSummary;
import services.ntr.pms.model.payout.PointSummary;
import services.ntr.pms.model.payout.Transaction;
import services.ntr.pms.service.information.NamingService;

@Service
@Primary
public class ClanAttendancePayoutService implements AttendancePayoutService<ClanAccount> {
	
	@Autowired
	private PayoutDAO payoutDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	private static final String TYPE = "Clan Attendance";
	
	@Override
	public List<PayoutGroup> getPayoutGroups(PayoutInformation<ClanAccount>  payoutInformation) {	
		
		ClanAccount clanAccount = payoutInformation.getPayoutInformation();
		
		int payoutAmount = payoutInformation.getAmount();
		long clanId = clanAccount.getClanId();
		
		List<PointSummary> pointSummaries = getPointSummaries(clanId);
		
		int shares = getShares(pointSummaries);
		
		boolean noSharesToPayout = shares == 0;
		
		if(noSharesToPayout) throw new NoAttendencesToPayoutException();
		
		int goldPerShare = payoutAmount / shares;
		
		List<PlayerPayoutSummary> playerPayoutSummaries = extractPayoutSummary(pointSummaries, goldPerShare, payoutInformation);
		Collections.sort(playerPayoutSummaries);  
		
		Map<Integer, PayoutGroup> payoutGroupMappedByAmount = getPayoutGroupMappedByAmount(playerPayoutSummaries);
		
		Collection<PayoutGroup> payoutGroupCollection = payoutGroupMappedByAmount.values();
		List<PayoutGroup> payoutGroups = new ArrayList<>(payoutGroupCollection);
		
		Collections.sort(payoutGroups, Collections.reverseOrder());  
		
		return payoutGroups;
		
	}
	
	@Override
	public void payout(PayoutInformation<ClanAccount>  payoutInformation)
	{
		List<PayoutGroup> payoutGroups = getPayoutGroups(payoutInformation);
		ClanAccount clanAccount = payoutInformation.getPayoutInformation();
		long clanId = clanAccount.getClanId();
		List<Attendance> attendancesNotPayedOut = getAttendancesNotPayedOut(clanId);

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
			
			for (Attendance attendance : attendancesNotPayedOut) {
				long accountId = attendance.getAccountId();
				int attendanceId = attendance.getAttendanceId();
				boolean payoutGroupContainsPlayerAccountId = payoutGroup.containsPlayerAccountId(accountId);
				
				if(payoutGroupContainsPlayerAccountId)addAttendancePayout(attendanceId, payoutId);
			}
		}
	}

	@Override
	public int getShares(ClanAccount clanAccount) {
		long clanId = clanAccount.getClanId();
		List<PointSummary> pointSummaries = getPointSummaries(clanId);
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

		for (PlayerPayoutSummary playerPayoutSummary : playerPayoutSummaries) {
			int amount = playerPayoutSummary.getAmount();
			boolean doesNotContainsKey = !payoutGroupMappedByAmount.containsKey(amount);

			if(doesNotContainsKey) payoutGroupMappedByAmount.put(amount, new PayoutGroup());

			PayoutGroup payoutGroup = payoutGroupMappedByAmount.get(amount);
			payoutGroup.addPayoutSummary(playerPayoutSummary);
		}
		
		return payoutGroupMappedByAmount;
	}
	
	private List<PlayerPayoutSummary> extractPayoutSummary(List<PointSummary> pointSummaries, int goldPerShare, PayoutInformation<ClanAccount> playerPayout) {
		
		List<PlayerPayoutSummary> playerPayoutSummaries = new ArrayList<>();
		
		for (PointSummary pointSummary : pointSummaries) {
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
	
	private List<PointSummary> getPointSummaries(long clanId) {

		List<PointSummary> pointSummaries = payoutDAO.getPointSummaries(clanId);
		
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
	
	private void addAttendancePayout(int attendanceId, int payoutId) {
		payoutDAO.addAttendancePayout(attendanceId, payoutId);
	}

	private void addPayout(Payout payout) {
		payoutDAO.addPayout(payout);
	}
	
	private void addTransaction(Transaction transaction){
		payoutDAO.addTransaction(transaction);
	}
	
	private List<Attendance> getAttendancesNotPayedOut(long clanId) {
		return payoutDAO.getAttendancesNotPayedOut(clanId);
	}
	
	public PayoutDAO getPayoutDAO() {
		return payoutDAO;
	}
	
	public void setPayoutDAO(PayoutDAO payoutDAO) {
		this.payoutDAO = payoutDAO;
	}
}
