package services.ntr.pms.service.information;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.ClanInfo;

public class DefaultClanInformationServiceTest {

	private ClanInformationService clanInformationService;
	
	@Before
	public void setUp() throws Exception 
	{
		DefaultClanInformationService defaultClanInformationService = new DefaultClanInformationService();
		APIRequestDAO apiRequestDAOMock = getMockAPIRequestDAO();
		defaultClanInformationService.setApiRequestDAO(apiRequestDAOMock);
		
		this.clanInformationService = defaultClanInformationService;
	}
	
	@Test
	public void testGetClanInformation() {
		ClanInfo clanInformation = clanInformationService.getClanInformation(1000007315L);
		
		long clanId = clanInformation.getClanId();
		String clanName = clanInformation.getName();
		
		assertThat(clanId, is(1000007315L));
		assertThat(clanName, is("NTR"));
	}
	
	private APIRequestDAO getMockAPIRequestDAO()
	{
		long clanId = 1000007315;
		
		ClanInfo clanInfo = new ClanInfo();
		
		clanInfo.setClanId(clanId);
		clanInfo.setName("NTR");
		
		String clanIdAsString = Long.toString(clanId);
		
		APIRequestDAO apiRequestDAOMock = mock(APIRequestDAO.class);
		when(apiRequestDAOMock.getClanInformation(clanIdAsString)).thenReturn(clanInfo);
		return apiRequestDAOMock;
	}

}
