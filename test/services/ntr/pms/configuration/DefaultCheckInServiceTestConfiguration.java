package services.ntr.pms.configuration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import services.ntr.pms.annotation.UnitProfile;
import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.information.EventService;

@Configuration
@UnitProfile
public class DefaultCheckInServiceTestConfiguration {
	
	@Bean
	@Primary
	public APIRequestDAO getMockAPIRequestDAO() {
		
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		String accessTokenValid = "62ea598a8bdbe29502b02d1cee8844e5924e4079";
		String accessTokenEventZeroBattles = "ec7d403bceae094334a7986f9a49d245f4d37395";
	
		List<ClanBattle> validBattles = getValidBattles();
		List<ClanBattle> invalidBattles = getInvalidBattles();
		List<ClanBattle> noBattles = new ArrayList<ClanBattle>();
		
		APIRequestDAO apiRequestDAOMock = mock(APIRequestDAO.class);
		when(apiRequestDAOMock.getClanBattleInformation(clanId, accessToken)).thenReturn(invalidBattles);
		when(apiRequestDAOMock.getClanBattleInformation(clanId, accessTokenValid)).thenReturn(validBattles);
		when(apiRequestDAOMock.getClanBattleInformation(clanId, accessTokenEventZeroBattles)).thenReturn(noBattles);
		return apiRequestDAOMock;
	}
	
	@Bean
	@Primary
	public EventService getEventService() {

		long clanId = 1000007315;
		EventService eventServiceMock = mock(EventService.class);
		List<Event> events = getValidEvents();
		
		when(eventServiceMock.getEventsByTimeFrame(eq(clanId), any(TimeFrame.class))).thenReturn(events);
		return eventServiceMock;
	}

	public List<ClanBattle> getValidBattles() {
		
		ClanBattle battle = new ClanBattle();
		ClanBattle battle2 = new ClanBattle();
		
		//Battle Starts Now
		battle.setTime(System.currentTimeMillis() / 1000L);
		//Battle Starts in one hour
		battle2.setTime(System.currentTimeMillis() / 1000L + 3600L);//3600 is one hour, 7200 is two hours
		
		List<ClanBattle> clanBattles = new ArrayList<ClanBattle>();
		clanBattles.add(battle);
		clanBattles.add(battle2);
		
		return clanBattles;
	}
	
	public List<ClanBattle> getInvalidBattles() {
		
		ClanBattle battle = new ClanBattle();
		ClanBattle battle2 = new ClanBattle();
		
		//Battle Starts Now
		battle.setTime(System.currentTimeMillis() / 1000 + 7200);
		//Battle Starts in one hour
		battle2.setTime(System.currentTimeMillis() / 1000 + 3600 + 7200);//3600000 is one hour, 7200000 is two hours
		
		List<ClanBattle> clanBattles = new ArrayList<ClanBattle>();
		clanBattles.add(battle);
		clanBattles.add(battle2);
		
		return clanBattles;
	}
	
	private List<Event> getValidEvents(){
		
		List<Event> events = new ArrayList<Event>();
		
		Event event = new Event();
		event.setStartTime(System.currentTimeMillis());
		
		events.add(event);
		
		return events;
		
	}
}
