package services.ntr.pms.dao;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.information.LocalClanSettings;

@DefaultDatabase
public interface LocalClanSettingsDAO {
	
	LocalClanSettings getLocalClanSettings(@Param("clanId") long clanId);
	
	void updateLocalClanSettings(LocalClanSettings localClanSettings);
	
	void addLocalClanSettings(@Param("clanId") long clanId);
	
	void deleteLocalClanSettings(@Param("clanId") long clanId);

}
