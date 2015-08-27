package services.ntr.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.access.AllowedClan;
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;

@DefaultDatabase
public interface SecurityDAO {

	AllowedClan getAllowedClan(@Param("clanId") long clanId);
	
	List<User> getAllAdmins();
	List<AllowedClan> getAllAllowedClans();
	List<Role> getAllRoles();
	
	void addAllowedClan(AllowedClan allowedClan);
	void addAdmin(User user);
	
	void editAdmin(User user);
	void deleteAdmin(@Param("accountId") long accountId);
	void deleteClan(@Param("clanId") long clanId);
}
