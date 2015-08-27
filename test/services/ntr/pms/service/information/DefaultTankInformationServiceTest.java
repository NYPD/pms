package services.ntr.pms.service.information;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;
import java.util.Map;

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
import services.ntr.pms.model.information.TankInformation;
import services.ntr.pms.service.information.TankInformationService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultAPIRequestDAOConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultTankInformationServiceTest{

	@Autowired
	TankInformationService tankInformationService;
	
	@Test
	public void shouldGetTankInformationMap(){
		
		Map<Integer, TankInformation> tankInformationMap = tankInformationService.getTankInformationMap();
		
		int numberOfTanks = tankInformationMap.size();
		assertThat(numberOfTanks, is(9));
	}

	@Test
	public void shouldGetTankInfo(){
		
		int tankId = 17153;
		
		TankInformation tankInfo = tankInformationService.getTankInfo(tankId);
		
		String nameI18n = tankInfo.getNameI18n();
		
		assertThat(nameI18n, is("Object 430"));
	}

	@Test
	public void shouldGetTierTenTankInformationList(){
		
		List<TankInformation> tierTenTankInformationList = tankInformationService.getCompleteTankInformationList();
		
		int numberOfTierTenTanks = tierTenTankInformationList.size();
		
		assertThat(numberOfTierTenTanks, is(5));
	}

}
