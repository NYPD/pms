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
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.payout.PayoutGroup;
import services.ntr.pms.model.payout.PayoutInformation;
import services.ntr.pms.model.payout.PlayerBonus;
import services.ntr.pms.model.payout.StandardPayoutInformation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultPlayerBonusPayoutServiceTest
{

	@Autowired
	private PlayerBonusPayoutService payoutService;
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetPayoutGroups()
	{
		PlayerBonus playerBonus =  new PlayerBonus();
		playerBonus.setAccountId(1001167560);
		playerBonus.setClanId(1000007315);
		playerBonus.setReason("Is A Cool Guy");
		
		PayoutInformation<PlayerBonus> payoutInformation = new StandardPayoutInformation<>();
		payoutInformation.setAmount(100);
		payoutInformation.setPayerAccountId(1000122895);
		payoutInformation.setPayerNickname("Zazie");
		payoutInformation.setPayoutInformation(playerBonus);
		
		List<PayoutGroup> payoutGroups = payoutService.getPayoutGroups(payoutInformation);
		
		PayoutGroup payoutGroup = payoutGroups.get(0);
		
		int numberOfPayoutGroups = payoutGroups.size();
		int avgAmount = payoutGroup.getAvgAmount();
		
		assertThat(numberOfPayoutGroups, is(1));
		assertThat(avgAmount, is(100));
	}

	@Test
	public void shouldPayout()
	{
		PlayerBonus playerBonus =  new PlayerBonus();
		playerBonus.setAccountId(1001167560);
		playerBonus.setClanId(1000007315);
		playerBonus.setReason("Is A Cool Guy");
		
		PayoutInformation<PlayerBonus> payoutInformation = new StandardPayoutInformation<>();
		payoutInformation.setAmount(100);
		payoutInformation.setPayerAccountId(1000122895);
		payoutInformation.setPayerNickname("Zazie");
		payoutInformation.setPayoutInformation(playerBonus);
		
		payoutService.payout(payoutInformation);
		
		Player player = new Player();
		player.setAccountId(1001167560);
		List<Payout> payouts = payoutService.getPayoutByPlayer(player);
		
		int playerBonusId = playerBonus.getPlayerBonusId();
		int numberOfPayouts = payouts.size();

		assertThat(playerBonusId, is(2));
		assertThat(numberOfPayouts, is(2));

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
