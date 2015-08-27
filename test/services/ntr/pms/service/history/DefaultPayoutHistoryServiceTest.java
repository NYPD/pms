package services.ntr.pms.service.history;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.history.PayoutMonthSummary;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.util.TimeFrame;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile

public class DefaultPayoutHistoryServiceTest
{

	@Autowired
	private DefaultPayoutHistoryService payoutHistoryService;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetPlayerPayouts()
	{
		Player player = new Player();
		player.setAccountId(1001167560);
		
		List<Payout> playerPayouts = payoutHistoryService.getPlayerPayouts(player);
		int numberOfPayout = playerPayouts.size();
		
		assertThat(numberOfPayout, is(1));
	}
	
	@Test
	public void shouldGetMontlyPlayerPayoutSummaries()
	{
		Player player = new Player();
		player.setAccountId(1001627822);
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1388552400000L);//Jan 1 2014 at Midnight
		timeFrame.setEndTime(1420088399000L);//Dec 31 2014 at a minute beforeMidnight
		
		List<PayoutMonthSummary> payoutMonthSummaries = payoutHistoryService.getMonthlyPayoutHistory(player, timeFrame);
		
		PayoutMonthSummary payoutMonthSummaryJanuary = payoutMonthSummaries.get(0);
		PayoutMonthSummary payoutMonthSummaryJune = payoutMonthSummaries.get(1);
		
		int amountForJanuary = payoutMonthSummaryJanuary.getAmount();
		int amountForJune = payoutMonthSummaryJune.getAmount();
		
		int numberOfPayoutMonthSummary = payoutMonthSummaries.size();
	
		assertThat(numberOfPayoutMonthSummary, is(2));
		assertThat(amountForJanuary, is(2000));
		assertThat(amountForJune, is(1500));
	}
	
	@Test
	public void shouldGetMontlyPlayerPayoutSummaryAmounts()
	{
		Player player = new Player();
		player.setAccountId(1001627822);
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1388552400000L);//Jan 1 2014 at Midnight
		timeFrame.setEndTime(1420088399000L);//Dec 31 2014 at a minute beforeMidnight
		
		int[] monthlyAmounts = payoutHistoryService.getMonthlyPayoutHistoryAmounts(player, timeFrame);
		
		int amountForJanuary = monthlyAmounts[0];
		int amountForJuly = monthlyAmounts[5];
	
		assertThat(amountForJanuary, is(2000));
		assertThat(amountForJuly, is(1500));
		
	}

}