package services.ntr.pms.service.information;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.ClanBattle;

@Service
public class DefaultBattleService implements BattleService{

	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public List<ClanBattle> getClanBattleInformation(String clanId,  String accessToken) {
		return apiRequestDAO.getClanBattleInformation(clanId, accessToken);
	}

	public APIRequestDAO getApiRequestDAO() {
		return apiRequestDAO;
	}

	public void setApiRequestDAO(APIRequestDAO apiRequestDAO) {
		this.apiRequestDAO = apiRequestDAO;
	}

}
