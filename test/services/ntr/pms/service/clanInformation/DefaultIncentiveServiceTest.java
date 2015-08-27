package services.ntr.pms.service.clanInformation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import services.ntr.pms.model.incentive.IncentivePlayer;
import services.ntr.pms.model.incentive.IncentiveTank;
import services.ntr.pms.model.incentive.IncentiveTankDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultAPIRequestDAOConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultIncentiveServiceTest {

	@Autowired
	private IncentiveService incentiveService;
	
	@Test
	public void shouldGetIncentiveTankDetails(){
		
		long ntrClanId = 1000007315;
		
		List<IncentiveTankDetail> clanIncentiveTankDetails = incentiveService.getClanIncentiveTankDetails(ntrClanId);
		
		int clanIncentiveTankDetailsSize = clanIncentiveTankDetails.size();
		int m48UnlockersSize = 0;
		int oneThriteenUnlockersSize = 0;
		
		for (IncentiveTankDetail incentiveTankDetail : clanIncentiveTankDetails) {
			
			String tankUnlockedName = incentiveTankDetail.getTankUnlockedName();
			List<Long> tankUnlockers = incentiveTankDetail.getTankUnlockerAccountIds();
			
			boolean isM48 = "M48".equals(tankUnlockedName); 
			boolean is113 = "113".equals(tankUnlockedName);
			
			if(isM48){
				m48UnlockersSize = tankUnlockers.size();
			}else if(is113){
				oneThriteenUnlockersSize = tankUnlockers.size();
			}
		}
		
		assertThat(clanIncentiveTankDetailsSize, is(4));
		assertThat(m48UnlockersSize, is(2));
		assertThat(oneThriteenUnlockersSize, is(1));
		
	}
	
	@Test
	public void shouldgetLimitedTopIncentivePlayers(){
		
		long ntrClanId = 1000007315;
		
		List<IncentivePlayer> limitedTopIncentivePlayersByClan = incentiveService.getLimitedTopIncentivePlayersByClan(ntrClanId, 5);
		
		int size = limitedTopIncentivePlayersByClan.size();
		
		assertThat(size, is(3));
	}
	
	@Test
	public void shouldgetLimitedTopTanks(){
		
		long ntrClanId = 1000007315;
		
		List<IncentiveTank> limitedTopTanksUnlockedByClan = incentiveService.getLimitedTopTanksUnlockedByClan(ntrClanId, 5);
		
		int size = limitedTopTanksUnlockedByClan.size();
		
		assertThat(size, is(4));
	}
	
}