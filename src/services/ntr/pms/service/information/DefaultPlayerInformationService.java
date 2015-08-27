package services.ntr.pms.service.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.Player;

@Service
public class DefaultPlayerInformationService implements PlayerInformationService {

	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public Player getPlayerInformation(long accountId) {
		
		
		String accountIdAsString = Long.toString(accountId);
		
		Player playerInformation = getApiRequestDAO().getPlayerInformation(accountIdAsString);
		
		
		return playerInformation;
	}
	
	public APIRequestDAO getApiRequestDAO() {
		return apiRequestDAO;
	}
	public void setApiRequestDAO(APIRequestDAO apiRequestDAO) {
		this.apiRequestDAO = apiRequestDAO;
	}

}
