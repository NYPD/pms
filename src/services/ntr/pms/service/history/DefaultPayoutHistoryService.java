package services.ntr.pms.service.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.model.history.PayoutMonthSummary;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.information.NamingService;
import services.ntr.pms.util.DateUtil;
import services.ntr.pms.util.TimeUtil;

@Service
public class DefaultPayoutHistoryService implements PayoutHistoryService{

	@Autowired
	private PayoutDAO payoutDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	@Override
	public List<Payout> getPlayerPayouts(Player player){
		
		List<Payout> playerPayouts = payoutDAO.getPayoutsByPlayer(player);
		
		namingService.name(playerPayouts);
		
		return playerPayouts;
		
	}
	
	@Override
	public List<PayoutMonthSummary> getMonthlyPayoutHistory(Player player, TimeFrame timeFrame) {
		
		List<PayoutMonthSummary> monthlyPlayerPayoutSummary = payoutDAO.getMonthlyPlayerPayoutSummary(player, timeFrame);
		
		return monthlyPlayerPayoutSummary;
	
	}
	
	@Override
	public int[] getMonthlyPayoutHistoryAmounts(Player player, TimeFrame timeFrame) {
		
		List<PayoutMonthSummary> payoutMonthSummaries = getMonthlyPayoutHistory(player, timeFrame);
		
		long startTime = timeFrame.getStartTime();
		
		DateUtil dateUtil = new DateUtil(startTime);
		
		boolean isCurrentYear = dateUtil.isCurrentYear();
		int currentMonthOfThisYear = TimeUtil.getCurrentMonthOfThisYear();
		
		int numberOfMonths = 12;
		
		if(isCurrentYear)numberOfMonths = currentMonthOfThisYear + 1;
		
		int[] amounts = new int[numberOfMonths];

		for (PayoutMonthSummary payoutMonthSummary : payoutMonthSummaries) {
			
			int month = payoutMonthSummary.getMonth();
			int amount = payoutMonthSummary.getAmount();
			
			amounts[month - 1] = amount;
		}

		return amounts;
	}

	public PayoutDAO getPayoutDAO()
	{
		return payoutDAO;
	}

	public void setPayoutDAO(PayoutDAO payoutDAO)
	{
		this.payoutDAO = payoutDAO;
	}
}
