package services.ntr.pms.service.information;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.service.information.BattleService;
import services.ntr.pms.service.information.DefaultBattleService;

public class DefaultBattleServiceTest {
	
	private BattleService battleService;
	
	@Before
	public void setUp() throws Exception {
		
		DefaultBattleService defaultBattleService = new DefaultBattleService();
		APIRequestDAO apiRequestDAOMock = getMockAPIRequestDAO();
		defaultBattleService.setApiRequestDAO(apiRequestDAOMock);
		
		this.battleService = defaultBattleService;
	}

	@Test
	public void shouldGetClanBattleInformation() {
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		
		List<ClanBattle> clanBattleInformation = battleService.getClanBattleInformation(clanId, accessToken);
		ClanBattle clanBattle = clanBattleInformation.get(0);
		long time = clanBattle.getTime();
		
		assertThat(time, is(1402630743L));
	}
    
	private APIRequestDAO getMockAPIRequestDAO() {
		
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
	
		ClanBattle clanBattleMock = mock(ClanBattle.class);
		when(clanBattleMock.getTime()).thenReturn(1402630743L);
		
		List<ClanBattle> clanBattleInformation = new ArrayList<>();
		clanBattleInformation.add(clanBattleMock);
		
		APIRequestDAO apiRequestDAOMock = mock(APIRequestDAO.class);

		when(apiRequestDAOMock.getClanBattleInformation(clanId, accessToken)).thenReturn(clanBattleInformation);
		return apiRequestDAOMock;
	}

}
