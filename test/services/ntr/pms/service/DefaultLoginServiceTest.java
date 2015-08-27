package services.ntr.pms.service;


import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.access.Token;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.service.access.DefaultLoginService;
import services.ntr.pms.service.access.LoginService;

public class DefaultLoginServiceTest
{

    private LoginService loginService;
    
	@Before
	public void setUp() throws Exception 
	{
		DefaultLoginService defaultLoginService = new DefaultLoginService();
		APIRequestDAO apiRequestDAOMock = getMockAPIRequestDAO();
		defaultLoginService.setApiRequestDAO(apiRequestDAOMock);
		
		this.loginService = defaultLoginService;
	}
	
    @Test
    public void shoudGetPlayerInformation()
	{
		String accountIdAsString = "1001167560";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";

		Player playerInformation = loginService.getPlayerInformation(accountIdAsString, accessToken);
		long accountId = playerInformation.getAccountId();
		assertThat(accountId, is(1001167560L));
	}

    @Test
	public void shoudGetMemberInformation()
	{
		String accountId = "1001167560";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";

    	MembersInfo memberInformation = loginService.getMemberInformation(accountId, accessToken);
    	long clanId = memberInformation.getClanId();
    	assertThat(clanId, is(1000007315L));
	}

    @Test
	public void shoudGetClanInformation() {
    	
		long clanId = 1000007315;
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";

		ClanInfo clanInformation = loginService.getClanInformation(clanId, accessToken);
		long expectedClanId = clanInformation.getClanId();	
		assertThat(expectedClanId, is(1000007315L));
	}
	@Test
	public void shouldExtenAccess()
	{
		Token token = new Token();
		token.setAccessToken("5dc8a468b9ed8319767456733c80cc388a19ba33");
		
		Token newToken = loginService.extendAccess(token);
		
		String accessToken = newToken.getAccessToken();
		
		assertThat(accessToken, is("da2dbad765eacc55a74cf948358979b09ad703af"));
	}
    
	private APIRequestDAO getMockAPIRequestDAO()
	{
		String clanId = "1000007315";
		String accountId = "1001167560";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		
		Player playerMock = mock(Player.class);
		when(playerMock.getAccountId()).thenReturn(1001167560L);
		
		ClanInfo clanInfoMock = mock(ClanInfo.class);
		when(clanInfoMock.getClanId()).thenReturn(1000007315L);
		
		MembersInfo memberInfoMock = mock(MembersInfo.class);
		when(memberInfoMock.getClanId()).thenReturn(1000007315L);
		
		ClanBattle clanBattleMock = mock(ClanBattle.class);
		when(clanBattleMock.getTime()).thenReturn(1402630743L);
		
		List<ClanBattle> clanBattleInformation = new ArrayList<>();
		clanBattleInformation.add(clanBattleMock);
		
		Token token = new Token();
		token.setAccessToken("da2dbad765eacc55a74cf948358979b09ad703af");
		
		APIRequestDAO apiRequestDAOMock = mock(APIRequestDAO.class);
		when(apiRequestDAOMock.getPlayerInformation(accountId, accessToken)).thenReturn(playerMock);
		when(apiRequestDAOMock.getMemberInformation(accountId, accessToken)).thenReturn(memberInfoMock);
		when(apiRequestDAOMock.getClanInformation(clanId, accessToken)).thenReturn(clanInfoMock);
		when(apiRequestDAOMock.getClanBattleInformation(clanId, accessToken)).thenReturn(clanBattleInformation);
		when(apiRequestDAOMock.extendAccess(isA(Token.class))).thenReturn(token);
		return apiRequestDAOMock;
	}
}
