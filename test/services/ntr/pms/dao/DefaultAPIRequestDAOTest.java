package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.DefaultAPIRequestDAOConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.access.Token;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.MapDetail;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.TankInformation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class,DefaultAPIRequestDAOConfiguration.class,EmbeddedDataSourceConfiguration.class})
@ActiveUnitProfile

public class DefaultAPIRequestDAOTest
{
	@Autowired
	private APIRequestDAO requestDAO;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetMapNamesList(){
		
		Map<String, MapDetail> mapDetailMappedByArenaId = requestDAO.getMapDetailMappedByArenaId();
		
		MapDetail mapDetail = mapDetailMappedByArenaId.get("53_japan");
		
		String nameI18n = mapDetail.getNameI18n();
		String arenaId = mapDetail.getArenaId();
		
		assertThat(arenaId, is("53_japan"));
		assertThat(nameI18n, is("Hidden Village"));
	}
	
	@Test
	public void shouldGetPlayerInformation()
	{
		String accountIdAsString = "1001167560";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		Player playerInformation = requestDAO.getPlayerInformation(accountIdAsString, accessToken);
		long accountId = playerInformation.getAccountId();
		assertThat(accountId, is(1001167560L));
	}
	
	@Test
	public void shouldGetPlayerInformationWithoutAccessToken(){
		String accountIdAsString = "1001167560";
		Player playerInformation = requestDAO.getPlayerInformation(accountIdAsString);
		long accountId = playerInformation.getAccountId();
		assertThat(accountId, is(1001167560L));
	}

	@Test
	public void shoulGetMemberInformation()
	{
		String accountId = "1001167560";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		
    	MembersInfo memberInformation = requestDAO.getMemberInformation(accountId, accessToken);
    	long clanId = memberInformation.getClanId();
    	assertThat(clanId, is(1000007315L));
	}

	@Test
	public void shouldGetClanInformation()
	{
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";

		ClanInfo clanInformation = requestDAO.getClanInformation(clanId, accessToken);
		long expectedClanId = clanInformation.getClanId();	
		assertThat(expectedClanId, is(1000007315L));
	}
	
	@Test
	public void shouldGetClanInformationWithoutAccessToken()
	{
		String clanId = "1000007315";

		ClanInfo clanInformation = requestDAO.getClanInformation(clanId);
		long expectedClanId = clanInformation.getClanId();	
		assertThat(expectedClanId, is(1000007315L));
	}

	@Test
	public void shouldGetClanBattleInformation()
	{
		String clanId = "1000007315";
		String accessToken = "5dc8a468b9ed8319767456733c80cc388a19ba33";
		
		List<ClanBattle> clanBattleInformation = requestDAO.getClanBattleInformation(clanId, accessToken);
		ClanBattle clanBattle = clanBattleInformation.get(0);
		long time = clanBattle.getTime();
		String arenaId = clanBattle.getFirstArena().getArenaId();
		
		assertThat(arenaId, is("06_ensk"));
		assertThat(time, is(1427151603L));
	}
	
	@Test
	public void shouldGetTankInfromationMap()
	{
		Map<Integer, TankInformation> tankInformationMap = requestDAO.getTankInformationMap();
		
		int numberOfTanks = tankInformationMap.size();
		
		assertThat(numberOfTanks, is(21));
	}
	@Test
	public void shouldExtenAccess()
	{
		Token token = new Token();
		token.setAccessToken("2235b4feeecb62e5cd9c86371b4b4bbdf95d0a1b");
		
		Token newToken = requestDAO.extendAccess(token);
		
		String accessToken = newToken.getAccessToken();
		
		assertThat(accessToken, is("da2dbad765eacc55a74cf948358979b09ad703af"));
	}
}
