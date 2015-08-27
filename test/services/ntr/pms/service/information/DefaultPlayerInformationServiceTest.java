package services.ntr.pms.service.information;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.Player;

public class DefaultPlayerInformationServiceTest {

	private PlayerInformationService playerInformationService;
	
	@Before
	public void setUp() throws Exception 
	{
		DefaultPlayerInformationService defaultPlayerInformationService = new DefaultPlayerInformationService();
		APIRequestDAO apiRequestDAOMock = getMockAPIRequestDAO();
		defaultPlayerInformationService.setApiRequestDAO(apiRequestDAOMock);
		
		this.playerInformationService = defaultPlayerInformationService;
	}
	
	@Test
	public void testGetPlayerInformation() {
		
		Player playerInformation = playerInformationService.getPlayerInformation(1001167560L);
		
		long accountId = playerInformation.getAccountId();
		String playerNickname = playerInformation.getNickname();
		
		assertThat(accountId, is(1001167560L));
		assertThat(playerNickname, is("NYPD"));
	}
	
	private APIRequestDAO getMockAPIRequestDAO()
	{
		long accountId = 1001167560;
		
		Player playerNYPD = new Player();
		
		playerNYPD.setAccountId(accountId);
		playerNYPD.setNickname("NYPD");
		
		String accountIdAsString = Long.toString(accountId);
		
		APIRequestDAO apiRequestDAOMock = mock(APIRequestDAO.class);
		when(apiRequestDAOMock.getPlayerInformation(accountIdAsString)).thenReturn(playerNYPD);
		return apiRequestDAOMock;
	}

}
