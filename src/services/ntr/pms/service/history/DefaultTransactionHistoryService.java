package services.ntr.pms.service.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.model.history.TransactionHistory;
import services.ntr.pms.model.history.TransactionHistoryChartPoint;
import services.ntr.pms.service.information.NamingService;

@Service
public class DefaultTransactionHistoryService implements TransactionHistoryService {

	@Autowired
	private PayoutDAO payoutDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	
	@Override
	public List<TransactionHistory> getTransactionHistory(long clanId) {
		
		List<TransactionHistory> transactionHistoryList = payoutDAO.getTransactionHistory(clanId);
		
		namingService.name(transactionHistoryList);
		
		return transactionHistoryList;
	}

	@Override
	public List<TransactionHistoryChartPoint> getTransactionHistoryPieChart(long clanId) {
		return payoutDAO.getTransactionHistoryChartPoints(clanId);
	}

}
