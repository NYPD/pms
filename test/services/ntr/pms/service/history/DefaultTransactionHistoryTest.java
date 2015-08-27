package services.ntr.pms.service.history;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.DefaultAPIRequestDAOConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.history.TransactionHistory;
import services.ntr.pms.model.history.TransactionHistoryChartPoint;
import services.ntr.pms.service.history.TransactionHistoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultAPIRequestDAOConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultTransactionHistoryTest {
	
	@Autowired
	TransactionHistoryService transactionHistoryService;
	
	@Test
	public void shouldGetTransactionHistoryList(){
		
		long clanId = 1000007315;
		
		List<TransactionHistory> transactionHistoryList = transactionHistoryService.getTransactionHistory(clanId);
		
		int numberofTransactionHisotryRows = transactionHistoryList.size();
		int amount = transactionHistoryList.get(0).getAmount();
		assertThat(numberofTransactionHisotryRows, is(1));
		assertThat(amount, is(1000));
	}
	
	@Test
	public void shouldGetTransactionHistoryPieChart(){
		
		long clanId = 1000009754;
		
		List<TransactionHistoryChartPoint> transactionHistoryPieChart = transactionHistoryService.getTransactionHistoryPieChart(clanId);
		
		int clanPayout = transactionHistoryPieChart.get(0).getAmount();
		int playerBonus = transactionHistoryPieChart.get(1).getAmount();

		
		assertThat(clanPayout, is(1500));
		assertThat(playerBonus, is(2000));
	}

}
