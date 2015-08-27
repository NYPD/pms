package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.CampaignReward;
import services.ntr.pms.model.payout.IncentiveCompletion;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PlayerBonus;
import services.ntr.pms.model.payout.PointSummary;
import services.ntr.pms.model.payout.Transaction;
import services.ntr.pms.model.util.TimeFrame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveUnitProfile
@Transactional

public class PayoutDAOTest
{

	@Autowired
	private PayoutDAO payoutDAO;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetAttendancesNotPayedOut()
	{
		List<Attendance> attendanceNotPayedOut = payoutDAO.getAttendancesNotPayedOut(1000007315);
		int numberOfAttendanceHistory = attendanceNotPayedOut.size();
		
		assertThat(numberOfAttendanceHistory, is(8));
	}
	
	@Test
	public void shouldGetPayoutByPlayer()
	{
		Player player = new Player();
		player.setAccountId(1001167560);
		List<Payout> payouts = payoutDAO.getPayoutsByPlayer(player);
		int numberOfPayouts = payouts.size();
		Payout payout = payouts.get(0);
		int id = payout.getId();
		
		assertThat(numberOfPayouts, is(1));
		assertThat(id, is(1));
	}
	@Test
	public void shouldAddPayedOut()
	{
		Payout payout = new Payout();
		payout.setPayerAccountId(1111111111);
		payout.setAmount(100);
		payout.setType("ClanAttendance");
		payout.setTransactionId(2);
		
		payoutDAO.addPayout(payout);
		
	
		int id = payout.getId();
		assertThat(id, is(5));
	}
	@Test
	public void shouldGetPointSummaries()
	{
		List<PointSummary> pointSummaries = payoutDAO.getPointSummaries(1000007315);
		
		int numberOfPointSummaries = pointSummaries.size();
		PointSummary pointSummary = pointSummaries.get(0);
		int points = pointSummary.getPoints();
	
		assertThat(numberOfPointSummaries, is(2));
		assertThat(points, is(7));
	}
	@Test
	
	public void shouldGetPointSummariesByTimeFrame()
	{
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);
		
		long clanId = 1000007315;
		
		List<PointSummary> pointSummaries = payoutDAO.getPointSummariesByTimeFrame(clanId, timeFrame);
		
		int numberOfPointSummaries = pointSummaries.size();
		PointSummary pointSummary = pointSummaries.get(0);
		int points = pointSummary.getPoints();
	
		assertThat(numberOfPointSummaries, is(2));
		assertThat(points, is(7));
	}
	@Test
	public void shouldAddIncentiveCompletion()
	{
		IncentiveCompletion incentiveCompletion = new IncentiveCompletion();
		incentiveCompletion.setAccountId(1001167560);
		incentiveCompletion.setClanId(1000007315);
		incentiveCompletion.setTankUnlockedName("T-34");
		incentiveCompletion.setTankUnlockedId(1);
		
		payoutDAO.addIncentiveCompletion(incentiveCompletion);
		
		int incentiveId = incentiveCompletion.getIncentiveId();

		assertThat(incentiveId, is(6));
	}
	@Test
	public void shouldAddPlayerBonus()
	{
		PlayerBonus playerBonus =  new PlayerBonus();
		playerBonus.setAccountId(1001167560);
		playerBonus.setClanId(1000007315);
		playerBonus.setReason("Is A Cool Guy");
		
		payoutDAO.addPlayerBonus(playerBonus);
		int id = playerBonus.getId();
		assertThat(id, is(2));
	}
	
	@Test
	public void shouldAddCampaignReward()
	{
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);
		
		CampaignReward campaignReward = new CampaignReward();
		campaignReward.setClanId(1000007315);
		campaignReward.setCampaignName("Some Campaign Name");
		campaignReward.setTimeFrame(timeFrame);
		campaignReward.setAmount(500);
		
		payoutDAO.addCampaignReward(campaignReward);
		int id = campaignReward.getId();
		assertThat(id, is(1));
	}
	
	@Test
	public void shouldAddTransaction()
	{
		long clanId = 1000007315;
		Transaction transaction = new Transaction();
		transaction.setClanId(clanId);
		
		payoutDAO.addTransaction(transaction);
		
		int nextTransactionNumber = transaction.getTransactionId();
		assertThat(nextTransactionNumber, is(5));
	}
	
	@Test
	public void shouldCallerGetAttendancesNotPayedOut()
	{
		List<CallerAttendance> callerAttendances = payoutDAO.getCallerAttendancesNotPayedOut(1000007315);
		int numberOfCallerAttendance = callerAttendances.size();
		
		assertThat(numberOfCallerAttendance, is(5));
	}
	
	@Test
	public void shouldCallerPointSummaries()
	{
		long clanId = 1000007315;
		List<PointSummary> callerPointSummaries = payoutDAO.getCallerPointSummaries(clanId);
		PointSummary pointSummary = callerPointSummaries.get(0);
		
		int numberOfCallerPointSummaries = callerPointSummaries.size();
		int points = pointSummary.getPoints();
		
		assertThat(numberOfCallerPointSummaries, is(3));
		assertThat(points, is(2));
		
		
	}
	
	
}
