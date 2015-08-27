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
import services.ntr.pms.model.information.ClanProvince;
import services.ntr.pms.service.information.ClanProvincesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultAPIRequestDAOConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultClanProvincesServiceTest {

	@Autowired
	ClanProvincesService clanProvincesService;
	
	@Test
	public void shouldGetClanProvincesMap(){
		
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		
		Map<String, ClanProvince> tankInformationMap = clanProvincesService.getClanProvincesMap(clanId, accessToken);
		
		int numberOfProvinces = tankInformationMap.size();
		assertThat(numberOfProvinces, is(6));
	}

	@Test
	public void shouldGetClanProvincesList(){
		
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		
		List<ClanProvince> clanProvincesList = clanProvincesService.getClanProvincesList(clanId, accessToken);
		String arenaName = clanProvincesList.get(0).getArenaI18n();
		
		int numberOfProvinces = clanProvincesList.size();
		assertThat(numberOfProvinces, is(6));
		assertThat(arenaName, is("South Coast"));
	}
}
