package services.ntr.pms.service.information;

import java.util.List;
import java.util.Map;

import services.ntr.pms.model.information.ClanProvince;

public interface ClanProvincesService {

	Map<String, ClanProvince> getClanProvincesMap(String clanId, String accessToken);
	List<ClanProvince> getClanProvincesList(String clanId, String accessToken);
}
