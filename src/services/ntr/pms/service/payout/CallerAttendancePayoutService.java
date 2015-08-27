package services.ntr.pms.service.payout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.exception.NoAttendencesToPayoutException;
import services.ntr.pms.model.checkin.CallerAttendance;
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

@Service("CallerAttendancePayoutService")
public class CallerAttendancePayoutService implements AttendancePayoutService<ClanAccount> {
	
	@Autowired
	private PayoutDAO payoutDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	private static final String TYPE = "Caller Attendance";
	
	@Override
	public List<PayoutGroup> getPayoutGroups(PayoutInformation<ClanAccount>  payoutInformation) {	
		
		ClanAccount clanAccount = payoutInformation.getPayoutInformation();
		
		int payoutAmount = payoutInformation.getAmount();
		long clanId = clanAccount.getClanId();
		
		List<PointSummary> pointSummaries = getPointSummaries(clanId);
		
		boolean noPointsToPayout = pointSummaries.size() == 0;
		
		if(noPointsToPayout) throw new NoAttendencesToPayoutException();
		
		int goldPerShare = payoutAmount;
		
		List<PlayerPayoutSummary> playerPayoutSummaries = extractPayoutSummary(pointSummaries, goldPerShare, payoutInformation);
		
		Map<Integer, PayoutGroup> payoutGroupMappedByAmount = getPayoutGroupMappedByAmount(playerPayoutSummaries);
		
		Collection<PayoutGroup> payoutGroupCollection = payoutGroupMappedByAmount.values();
		List<PayoutGroup> payoutGroups = new ArrayList<>(payoutGroupCollection);

		Collections.sort(payoutGroups, Collections.reverseOrder());  
		
		return payoutGroups;
		
	}
	
	@Override
	public void payout(PayoutInformation<ClanAccount>  payoutInformation) {
		
		List<PayoutGroup> payoutGroups = getPayoutGroups(payoutInformation);
		ClanAccount clanAccount = payoutInformation.getPayoutInformation();
		long clanId = clanAccount.getClanId();
		List<CallerAttendance> callerAttendancesNotPayedOut = getCallerAttendancesNotPayedOut(clanId);
		
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
			
			int avgAmount = payoutGroup.getAvgAmount();
			boolean shouldNotProcessPayoutGroup = avgAmount == 0;
			
			if(shouldNotProcessPayoutGroup) continue;

			addPayout(payout);
			
			int payoutId = payout.getPayoutId();
			
			List<Long> callersAlreadyPayedOut = new ArrayList<>();

			for (CallerAttendance attendance : callerAttendancesNotPayedOut) {
				
				long accountId = attendance.getAccountId();		
				
				boolean callerIsAlreadyPayedOut = callersAlreadyPayedOut.contains(accountId);

				if(callerIsAlreadyPayedOut) continue;
		
				int callerAttendanceId = attendance.getCallerAttendanceId();
				boolean payoutGroupContainsPlayerAccountId = payoutGroup.containsPlayerAccountId(accountId);
				
				if(payoutGroupContainsPlayerAccountId) addCallerPayout(callerAttendanceId, payoutId);
				callersAlreadyPayedOut.add(accountId);
			}
			
		}
		
		payoutAttendancesToZero(payoutInformation, transactionId, payoutTime);
	}

	@Override
	public int getShares(ClanAccount clanAccount){
		
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
	public PayoutDAO getPayoutDAO() {
		return payoutDAO;
	}
	
	public void setPayoutDAO(PayoutDAO payoutDAO) {
		this.payoutDAO = payoutDAO;
	}
	
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
		
		for (PointSummary pointSummary : pointSummaries) {
			int points = pointSummary.getPoints();
			shares += points;
		}
		
		return shares;
	}
	private List<PointSummary> getPointSummaries(long clanId) {
		
		List<PointSummary> pointSummaries = payoutDAO.getCallerPointSummaries(clanId);
		
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
	
	private void payoutAttendancesToZero(PayoutInformation<ClanAccount>  payoutInformation, int transactionId, long payoutTime) {
		
		long clanId = payoutInformation.getPayoutInformation().getClanId();
		
		List<CallerAttendance> callerAttendancesNotPayedOut = getCallerAttendancesNotPayedOut(clanId);
		
		long payerAccountId = payoutInformation.getPayerAccountId();
		
		Payout payout = new Payout();
		payout.setAmount(0);
		payout.setType(TYPE);
		payout.setPayerAccountId(payerAccountId);
		payout.setTransactionId(transactionId);
		payout.setPayoutTime(payoutTime);
		
		addPayout(payout);
		
		int payoutId = payout.getPayoutId();
		
		for (CallerAttendance callerAttendance : callerAttendancesNotPayedOut) {
			
			int callerAttendanceId = callerAttendance.getCallerAttendanceId();
			
			addCallerPayout(callerAttendanceId, payoutId);
		}
	}
	
	private void addCallerPayout(int callerAttendanceId, int payoutId){
		
		payoutDAO.addCallerPayout(callerAttendanceId, payoutId);
	}
	
	private void addPayout(Payout payout){
		
		payoutDAO.addPayout(payout);
	}
	
	private void addTransaction(Transaction transaction){
		
		payoutDAO.addTransaction(transaction);
	}
	
	private List<CallerAttendance> getCallerAttendancesNotPayedOut(long clanId){
		
		return payoutDAO.getCallerAttendancesNotPayedOut(clanId);
	}
}
