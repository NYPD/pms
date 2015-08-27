package services.ntr.pms.service.information;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import services.ntr.pms.model.information.LocalClanSettings;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultLocalClanSettingsServiceTest {
	
	@Autowired
	private LocalClanSettingsService localClanSettingsService;

	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetLocalClanSettingsByClanId(){
		
		long clanId = 1000007315;
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		int id = localClanSettings.getId();
		
		assertThat(id, is(1));
	}
	
	@Test
	public void shouldUpdateLocalClanSettings(){
		
		long clanId = 1000007315;
		
		LocalClanSettings localClanSettings = new LocalClanSettings();
		
		localClanSettings.setCallerAmountPerBattle(69);
		localClanSettings.setClanId(clanId);
		
		localClanSettingsService.updateLocalClanSettings(localClanSettings);
		
		LocalClanSettings updatedLocalClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		int callerAmountPerBattle = updatedLocalClanSettings.getCallerAmountPerBattle();
		
		assertThat(callerAmountPerBattle, is(69));
	}
	
	@Test
	public void shouldUpdateLocalClanSettingsFromAustraliaUser(){
		
		long clanId = 1000007315;
		
		LocalClanSettings localClanSettings = new LocalClanSettings();
		
		localClanSettings.setCallerAmountPerBattle(69);
		localClanSettings.setClanId(clanId);
		localClanSettings.setBattleDayGmtOffset(21.5);
		
		localClanSettingsService.updateLocalClanSettings(localClanSettings);
		
		LocalClanSettings updatedLocalClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		int callerAmountPerBattle = updatedLocalClanSettings.getCallerAmountPerBattle();
		double battleDayGmtOffset = updatedLocalClanSettings.getBattleDayGmtOffset();
		
		assertThat(callerAmountPerBattle, is(69));
		assertThat(battleDayGmtOffset, is(21.5));
	}
	
	@Test
	public void shouldAddLocalClanSettings(){
		
		LocalClanSettings localClanSettings = new LocalClanSettings();
		
		long clanId = 1111111112; //Random test clan
		
		localClanSettingsService.addLocalClanSettings(clanId);
		
		localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		int battleTimeRangeBeforeMinutes = localClanSettings.getBattleTimeRangeBeforeMinutes();
		int battleTimeRangeAfterMinutes = localClanSettings.getBattleTimeRangeAfterMinutes();
		
		assertThat(battleTimeRangeBeforeMinutes, is(30));
		assertThat(battleTimeRangeAfterMinutes, is(30));
	}
	
	@Test
	public void shouldDeleteLocalClanSettings(){
		
		long clanId = 1000007315; //Random test clan
		
		localClanSettingsService.deleteLocalClanSettings(clanId);
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		boolean localClanSettingDoesNotExist = localClanSettings == null;
		
		assertThat(localClanSettingDoesNotExist, is(true));
	}

}
