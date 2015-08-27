package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import services.ntr.pms.model.incentive.IncentivePlayer;
import services.ntr.pms.model.incentive.IncentiveTank;
import services.ntr.pms.model.incentive.IncentiveTankDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveUnitProfile
@Transactional
public class TankIncentiveDAOTest {

	@Autowired
	private TankIncentiveDAO tankIncentiveDAO;
	@Autowired
	private PayoutDAO payoutDAO;
	
	@Before
	public void setUp() throws Exception
	{}
	
	@Test
	public void shouldgetIncentiveTankDetails(){
		
		long ntrClanId = 1000007315;
		
		List<IncentiveTankDetail> clanIncentiveTankDetails = tankIncentiveDAO.getClanIncentiveTankDetails(ntrClanId);
		
		int clanIncentiveTankDetailsSize = clanIncentiveTankDetails.size();
		int m48UnlockersSize = 0;
		int oneThriteenUnlockersSize = 0;
		
		for (IncentiveTankDetail incentiveTankDetail : clanIncentiveTankDetails) {
			
			String tankUnlockedName = incentiveTankDetail.getTankUnlockedName();
			List<String> tankUnlockerNicknames = incentiveTankDetail.getTankUnlockerNicknames();
			
			boolean isM48 = "M48".equals(tankUnlockedName); 
			boolean is113 = "113".equals(tankUnlockedName);
			
			if(isM48){
				m48UnlockersSize = tankUnlockerNicknames.size();
			}else if(is113){
				oneThriteenUnlockersSize = tankUnlockerNicknames.size();
			}
		}
		
		assertThat(clanIncentiveTankDetailsSize, is(4));
		assertThat(m48UnlockersSize, is(2));
		assertThat(oneThriteenUnlockersSize, is(1));
	}
	
	@Test
	public void shouldgetLimitedTopIncentivePlayers(){
		
		long ntrClanId = 1000007315;
		
		List<IncentivePlayer> limitedTopIncentivePlayersByClan = tankIncentiveDAO.getTopIncentivePlayersByClan(ntrClanId);
		
		int size = limitedTopIncentivePlayersByClan.size();
		
		assertThat(size, is(3));
	}
	
	@Test
	public void shouldgetLimitedTopTanks(){
		
		long ntrClanId = 1000007315;
		
		List<IncentiveTank> limitedTopTanksUnlockedByClan = tankIncentiveDAO.getTopTanksUnlockedByClan(ntrClanId);
		
		int size = limitedTopTanksUnlockedByClan.size();
		
		assertThat(size, is(4));
	}
	
}
