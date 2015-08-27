package services.ntr.pms.service.access;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.SecurityDAO;
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.AllowedClan;
import services.ntr.pms.model.access.User;
import services.ntr.pms.service.information.LocalClanSettingsService;
import services.ntr.pms.service.information.NamingService;
import services.ntr.pms.util.UserRole;

@Service
public class DefaultSecurityService implements SecurityService{

	@Autowired
	private SecurityDAO securityDAO;
	@Autowired
	private LocalClanSettingsService localClanSettingsService;
	@Autowired
	private NamingService namingService;
	
	@Override
	public boolean checkIfClanAdmin(User user) {

		Role role = user.getRole();
		
		int roleId = role.getRoleId();
		
		boolean isClanAdmin = roleId == UserRole.CLAN_ADMIN.getId();
		
		return isClanAdmin;
	}
	
	@Override
	public boolean checkIfSystemAdmin(User user) {
		
		Role role = user.getRole();
		
		int roleId = role.getRoleId();
		
		boolean isSystemAdmin = roleId == UserRole.SYSTEM_ADMIN.getId();
		
		return isSystemAdmin;
	}

	@Override
	public boolean checkIfValidClan(long clanId) {
		
		AllowedClan allowedClan = securityDAO.getAllowedClan(clanId);
		
		boolean clanExists = allowedClan != null;
		
		return clanExists;
	}
	
	@Override
	public boolean checkIfClanExists(long clanId) {
		
		AllowedClan allowedClan = securityDAO.getAllowedClan(clanId);
		
		boolean clanExists = allowedClan != null;
		
		return clanExists;
		
	}

	@Override
	public void addAllowedClan(AllowedClan allowedClan) {
		
		securityDAO.addAllowedClan(allowedClan);
		
		long clanId = allowedClan.getClanId();
		
		localClanSettingsService.addLocalClanSettings(clanId);
	}
	
	@Override
	public List<User> getAllAdmins() {
		
		List<User> adminList = securityDAO.getAllAdmins();
		
		namingService.name(adminList);
		
		return adminList;
	}
	
	@Override
	public AllowedClan getAllowedClan(long clanId) {
		
		AllowedClan allowedClan = securityDAO.getAllowedClan(clanId);
		
		return allowedClan;
	}
	
	@Override
	public List<AllowedClan> getAllAllowedClans() {
		
		List<AllowedClan> allowedClans = securityDAO.getAllAllowedClans();
		
		return allowedClans;
	}

	@Override
	public List<Role> getAllRoles() {

		List<Role> roles = securityDAO.getAllRoles();
		
		return roles;
	}

	@Override
	public void addAdmin(User user) {

		securityDAO.addAdmin(user);
	}

	@Override
	public void editAdmin(User user) {
		
		securityDAO.editAdmin(user);
		
	}

	@Override
	public void deleteAdmin(long accountId) {
		
		securityDAO.deleteAdmin(accountId);
		
	}

	@Override
	public void deleteClan(long clanId) {

		securityDAO.deleteClan(clanId);
		
		localClanSettingsService.deleteLocalClanSettings(clanId);
	}


}
