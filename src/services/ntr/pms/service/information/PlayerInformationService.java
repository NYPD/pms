package services.ntr.pms.service.information;

import services.ntr.pms.model.information.Player;

public interface PlayerInformationService {

	Player getPlayerInformation(long accountId);
	
}
