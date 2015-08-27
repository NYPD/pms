package services.ntr.pms.service.information;

import services.ntr.pms.model.information.LocalClanSettings;

public interface LocalClanSettingsService {
	
	LocalClanSettings getLocalClanSettings(long clanId);
	
	void updateLocalClanSettings(LocalClanSettings localClanSettings);
	
	void addLocalClanSettings(long clanId);
	
	void deleteLocalClanSettings(long clanId);

}
