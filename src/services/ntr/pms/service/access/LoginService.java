package services.ntr.pms.service.access;

import services.ntr.pms.model.access.Token;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;

public interface LoginService {
	
	Player getPlayerInformation(String accountId, String accessToken);
	MembersInfo getMemberInformation(String accountId, String accessToken);
	ClanInfo getClanInformation(long clanId, String accessToken);
	Token extendAccess(Token token);
	String getLoginRedirectURL(boolean rememberMe);
	
}
