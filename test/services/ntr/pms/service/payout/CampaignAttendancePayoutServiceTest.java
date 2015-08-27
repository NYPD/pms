package services.ntr.pms.service.payout;

import static org.hamcrest.CoreMatchers.is;
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
import services.ntr.pms.configuration.DefaultAPIRequestDAOConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.CampaignReward;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.StandardPayoutInformation;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.payout.AttendancePayoutService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultAPIRequestDAOConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile

public class CampaignAttendancePayoutServiceTest
{

	@Autowired
	private AttendancePayoutService<CampaignReward> payoutService;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetPayoutGroups()
	{
		PayoutInformation<CampaignReward> playerPayout = new StandardPayoutInformation<CampaignReward>(); 
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);
		
		CampaignReward campaignReward = new CampaignReward();
		campaignReward.setClanId(1000007315);
		campaignReward.setCampaignName("Some Campaign Name");
		campaignReward.setTimeFrame(timeFrame);
		campaignReward.setAmount(700);
		
		playerPayout.setAmount(700);
		playerPayout.setPayerAccountId(1111111111);
		playerPayout.setPayerNickname("Santolife");
		playerPayout.setPayoutInformation(campaignReward);
		
		List<PayoutGroup> payoutGroups = payoutService.getPayoutGroups(playerPayout);

		int numberOfPayoutGroups = payoutGroups.size();
		PayoutGroup payoutGroup = payoutGroups.get(0);
		int payoutAmount = payoutGroup.getAvgAmount();

		assertThat(numberOfPayoutGroups, is(2));
		assertThat(payoutAmount, is(700));
	}

	@Test
	public void shouldPayout()
	{
		PayoutInformation<CampaignReward> playerPayout = new StandardPayoutInformation<CampaignReward>(); 
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);
		
		CampaignReward campaignReward = new CampaignReward();
		campaignReward.setClanId(1000007315);
		campaignReward.setCampaignName("Some Campaign Name");
		campaignReward.setTimeFrame(timeFrame);
		campaignReward.setAmount(700);
		
		playerPayout.setAmount(700);
		playerPayout.setPayerAccountId(1111111111);
		playerPayout.setPayerNickname("Santolife");
		playerPayout.setPayoutInformation(campaignReward);
		
		payoutService.payout(playerPayout);
		
		Player player = new Player();
		player.setAccountId(1001167560);
		List<Payout> payouts = payoutService.getPayoutByPlayer(player);
		
		int numberOfPayouts = payouts.size();
		Payout payout = payouts.get(1);
		int id = payout.getId();
		int amount = payout.getAmount();
		
		assertThat(numberOfPayouts, is(2));
		assertThat(id, is(5));
		assertThat(amount, is(700));
	}

	@Test
	public void shouldGetShares()
	{
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);

		CampaignReward campaignReward = new CampaignReward();
		campaignReward.setClanId(1000007315);
		campaignReward.setTimeFrame(timeFrame);
		
		int shares = payoutService.getShares(campaignReward);

		assertThat(shares, is(7));
	}

	@Test
	public void shouldGetPayoutByPlayer()
	{
		Player player = new Player();
		player.setAccountId(1001167560);
		List<Payout> payouts = payoutService.getPayoutByPlayer(player);
		int numberOfPayouts = payouts.size();
		Payout payout = payouts.get(0);
		int id = payout.getId();
		
		assertThat(numberOfPayouts, is(1));
		assertThat(id, is(1));
	}
	

}
