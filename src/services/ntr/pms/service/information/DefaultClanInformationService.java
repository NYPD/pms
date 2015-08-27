package services.ntr.pms.service.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.ClanInfo;

@Service
public class DefaultClanInformationService implements ClanInformationService {

	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public ClanInfo getClanInformation(long clanId) {
		
		String clanIdAsString = Long.toString(clanId);
		
		ClanInfo clanInformation = getApiRequestDAO().getClanInformation(clanIdAsString);
		
		return clanInformation;
	}

	public APIRequestDAO getApiRequestDAO() {
		return apiRequestDAO;
	}

	public void setApiRequestDAO(APIRequestDAO apiRequestDAO) {
		this.apiRequestDAO = apiRequestDAO;
	}

}
