package services.ntr.pms.service.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.ClanProvince;

@Service
public class DefaultClanProvincesService implements ClanProvincesService {
	
	@Autowired
	APIRequestDAO apiRequestDAO;
	
	@Override
	public Map<String, ClanProvince> getClanProvincesMap(String clanId, String accessToken) {

		Map<String, ClanProvince> clanProvincesMap = apiRequestDAO.getClanProvinces(clanId, accessToken);
		
		return clanProvincesMap;
	}

	@Override
	public List<ClanProvince> getClanProvincesList(String clanId, String accessToken) {

		Map<String, ClanProvince> clanProvincesMap = getClanProvincesMap(clanId, accessToken);
		
		List<ClanProvince> clanProvincesList = new ArrayList<ClanProvince>(clanProvincesMap.values());
		
		return clanProvincesList;
	}

}
