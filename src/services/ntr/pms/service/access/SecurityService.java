package services.ntr.pms.service.access;

import java.util.List;

import services.ntr.pms.model.access.AllowedClan;
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;

public interface SecurityService {

	boolean checkIfClanAdmin(User user);
	boolean checkIfSystemAdmin(User user);
	boolean checkIfValidClan(long clanId);
	boolean checkIfClanExists(long clanId);
	
	AllowedClan getAllowedClan(long clanId);
	
	List<User> getAllAdmins();
	List<AllowedClan> getAllAllowedClans();
	List<Role> getAllRoles();
	
	void addAdmin(User user);
	void addAllowedClan(AllowedClan allowedClan);
	
	void editAdmin(User user);
	void deleteAdmin(long accountId);
	
	void deleteClan(long clanId);
}
