package services.ntr.pms.service.information;

import java.util.List;

import services.ntr.pms.model.information.ClanBattle;

public interface BattleService{

	List<ClanBattle> getClanBattleInformation(String clanId, String accessToken);
}
