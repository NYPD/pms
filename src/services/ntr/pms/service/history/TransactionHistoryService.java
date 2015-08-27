package services.ntr.pms.service.history;

import java.util.List;

import services.ntr.pms.model.history.TransactionHistory;
import services.ntr.pms.model.history.TransactionHistoryChartPoint;

public interface TransactionHistoryService {
	
	List<TransactionHistory> getTransactionHistory(long clanId);
	List<TransactionHistoryChartPoint> getTransactionHistoryPieChart(long clanId);
}
