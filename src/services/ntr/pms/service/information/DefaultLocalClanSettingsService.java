package services.ntr.pms.service.information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.LocalClanSettingsDAO;
import services.ntr.pms.model.information.LocalClanSettings;

@Service
public class DefaultLocalClanSettingsService implements LocalClanSettingsService {

	@Autowired
	LocalClanSettingsDAO localClanSettingsDAO;
	
	@Override
	public LocalClanSettings getLocalClanSettings(long clanId) {

		LocalClanSettings localClanSettings = localClanSettingsDAO.getLocalClanSettings(clanId);
		
		return localClanSettings;
	}

	@Override
	public void updateLocalClanSettings(LocalClanSettings localClanSettings) {
		
		localClanSettingsDAO.updateLocalClanSettings(localClanSettings);

	}

	@Override
	public void addLocalClanSettings(long clanId) {
		
		localClanSettingsDAO.addLocalClanSettings(clanId);
		
	}

	@Override
	public void deleteLocalClanSettings(long clanId) {
		
		localClanSettingsDAO.deleteLocalClanSettings(clanId);
		
	}

}
