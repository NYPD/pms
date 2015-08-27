package services.ntr.pms.service.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.access.Token;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanProvince;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;

@Service
public class DefaultLoginService implements LoginService {
	
	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public Player getPlayerInformation(String accountId, String accessToken) {
		Player player = apiRequestDAO.getPlayerInformation(accountId, accessToken);
		return player;
	}
	
	@Override
	public MembersInfo getMemberInformation(String accountId, String accessToken) {
		MembersInfo membersInfo = apiRequestDAO.getMemberInformation(accountId, accessToken);
		return membersInfo;
	}
	
	@Override
	public ClanInfo getClanInformation(long clanId, String accessToken) {
		
		String clanIdAsString = String.valueOf(clanId);
		
		ClanInfo clanInfo = apiRequestDAO.getClanInformation(clanIdAsString, accessToken);
		
		Map<String, ClanProvince> clanProvincesMap = apiRequestDAO.getClanProvinces(clanIdAsString, accessToken);
		List<ClanProvince> clanProvincesList = new ArrayList<ClanProvince>(clanProvincesMap.values());
		
		clanInfo.setClanProvinces(clanProvincesList);
		
		return clanInfo;
	}
	
	@Override
	public Token extendAccess(Token token) {
		return apiRequestDAO.extendAccess(token);
	}

	@Override
	public String getLoginRedirectURL(boolean rememberMe) {
		String loginRedirectURL = apiRequestDAO.getLoginRedirectURL(rememberMe);
		
		return loginRedirectURL;
	}

	public APIRequestDAO getApiRequestDAO() {
		return apiRequestDAO;
	}
	public void setApiRequestDAO(APIRequestDAO apiRequestDAO) {
		this.apiRequestDAO = apiRequestDAO;
	}

}
