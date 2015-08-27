package services.ntr.pms.configuration;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import services.ntr.pms.annotation.UnitProfile;
import services.ntr.pms.dao.DefaultAPIRequestDAO;
import services.ntr.pms.dao.DefaultAPIRequestDAOTest;
import services.ntr.pms.proxy.contentprovider.ContentProvider;
@Configuration
@UnitProfile
public class DefaultAPIRequestDAOConfiguration{	
	@Bean
	public ContentProvider contentProvider(){ 

		ContentProvider contentProviderMock = null;
		InputStream accountInfoStream = null;
		InputStream clanMembersInfoStream = null;
		InputStream clanInfoStream = null;
		InputStream clanBattlesStream = null;
		InputStream globalwarMapsStream = null;
		InputStream listOfVehiclesStream = null;
		InputStream listOfClanProvincesStream = null;
		InputStream authProlongateStream = null;
		InputStream mapDetailsStream = null;

		try {
			ClassLoader classLoader = DefaultAPIRequestDAOTest.class.getClassLoader();
			accountInfoStream = classLoader.getResourceAsStream("setup/account_info.txt");
			clanMembersInfoStream = classLoader.getResourceAsStream("setup/clan_membersinfo.txt");
			clanInfoStream = classLoader.getResourceAsStream("setup/clan_info.txt");
			clanBattlesStream = classLoader.getResourceAsStream("setup/clan_battles.txt");
			globalwarMapsStream = classLoader.getResourceAsStream("setup/globalwar_maps.txt");
			listOfVehiclesStream = classLoader.getResourceAsStream("setup/list_of_vehicles.txt");
			listOfClanProvincesStream = classLoader.getResourceAsStream("setup/clan_provinces.txt");
			authProlongateStream = classLoader.getResourceAsStream("setup/auth_prolongate.txt");
			mapDetailsStream = classLoader.getResourceAsStream("setup/map_details.txt");
			
			byte[] accountInfoBytes = IOUtils.toByteArray(accountInfoStream);
			byte[] clanMembersInfoBytes = IOUtils.toByteArray(clanMembersInfoStream);
			byte[] clanInfoBytes = IOUtils.toByteArray(clanInfoStream);
			byte[] clanBattlesBytes = IOUtils.toByteArray(clanBattlesStream);
			byte[] globalwarMapsBytes = IOUtils.toByteArray(globalwarMapsStream);
			byte[] listOfVehiclesBytes = IOUtils.toByteArray(listOfVehiclesStream);
			byte[] listOfClanProvincesBytes = IOUtils.toByteArray(listOfClanProvincesStream);
			byte[] authProlongateBytes = IOUtils.toByteArray(authProlongateStream);
			byte[] mapDetailsBytes = IOUtils.toByteArray(mapDetailsStream);
			
			contentProviderMock = mock(ContentProvider.class);
	
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.PLAYER_PERSONAL_DATA_PATH)))
			.thenReturn(accountInfoBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.CLAN_MEMBER_PATH)))
			.thenReturn(clanMembersInfoBytes);
	
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.CLAN_DETAILS_PATH)))
			.thenReturn(clanInfoBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.CLAN_BATTLES_PATH)))
			.thenReturn(clanBattlesBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.GLOBAL_WAR_MAPS)))
			.thenReturn(globalwarMapsBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.LIST_OF_VEHICLES)))
			.thenReturn(listOfVehiclesBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.CLAN_PROVINCES)))
			.thenReturn(listOfClanProvincesBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.EXTEND_ACCESS)))
			.thenReturn(authProlongateBytes);
			
			when(contentProviderMock.execute(anyMapOf(String.class, String.class), eq(DefaultAPIRequestDAO.MAP_DETAILS)))
			.thenReturn(mapDetailsBytes);
			
		}
		catch (IOException exception)
		{
			//TODO Log
		}
		finally
		{
			closeInputStream(accountInfoStream);
			closeInputStream(clanMembersInfoStream);
			closeInputStream(clanInfoStream);
			closeInputStream(clanBattlesStream);
			closeInputStream(globalwarMapsStream);
			closeInputStream(listOfVehiclesStream);
			closeInputStream(listOfClanProvincesStream);
			closeInputStream(authProlongateStream);
			closeInputStream(mapDetailsStream);
		}

		return contentProviderMock;
	}
	
	private void closeInputStream(InputStream inputStream)
	{
		try
		{
			inputStream.close();
		} catch (IOException exception)
		{
			//TODO Log
		} catch (Exception exception)
		{
			//TODO Log
		}
	}
}
