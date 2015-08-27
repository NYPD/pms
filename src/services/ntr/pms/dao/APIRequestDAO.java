package services.ntr.pms.dao;

import java.util.List;
import java.util.Map;

import services.ntr.pms.model.access.Token;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanProvince;
import services.ntr.pms.model.information.GlobalWarMap;
import services.ntr.pms.model.information.MapDetail;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.TankInformation;

public interface APIRequestDAO {
	
	Player getPlayerInformation(String accountId, String accessToken);
	Player getPlayerInformation(String accountId);
	Player getPlayerInformation(long accountId);
	
	Map<Long, Player> getPlayerInformationMap(List<Long> accountIds);
	
	MembersInfo getMemberInformation(String accountId, String accessToken);
	ClanInfo getClanInformation(String clanId, String accessToken);
	ClanInfo getClanInformation(String clanId);
	ClanInfo getClanInformation(long clanId);
	
	List<ClanBattle> getClanBattleInformation(String clanId, String accessToken);
	Map<Integer, TankInformation> getTankInformationMap();
	Map<String, ClanProvince> getClanProvinces(String clanId, String accessToken);
	List<GlobalWarMap> getGlobalWarMaps(String accessToken);
	void logoutPlayer(String accessToken);
	Token extendAccess(Token token);
	
	Map<String, MapDetail> getMapDetailMappedByArenaId();
	
	String getLoginRedirectURL(boolean rememberMe);
}