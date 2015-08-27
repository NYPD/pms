package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.access.AllowedClan;
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveUnitProfile
@Transactional
public class SecurityDAOTest {

	@Autowired
	private SecurityDAO securityDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetAllowedClan(){
		long clanId = 1000007315;
		AllowedClan allowedClan = securityDAO.getAllowedClan(clanId);
		
		boolean notNullAllowedclan = allowedClan != null;
		
		assertThat(notNullAllowedclan, is(true));
	}
	
	@Test
	public void shouldAddAllowedClan(){
		
		AllowedClan allowedClan = new AllowedClan();
		
		long clanId = 1000008000L;
		String clanName = "NTR";
		
		allowedClan.setClanId(clanId);
		allowedClan.setClanName(clanName);
		
		securityDAO.addAllowedClan(allowedClan);
		
		int allowedClanId = allowedClan.getAllowedClanId();
		
		assertThat(allowedClanId, is(2));
	}
	
	@Test
	public void shouldGetAllRoles(){

		List<Role> allRoles = securityDAO.getAllRoles();
		
		
		int amountOfRoles = allRoles.size();
		
		assertThat(amountOfRoles, is(3));
	}
	
	@Test
	public void shouldGetAllAdmins(){
		
		List<User> allAdmins = securityDAO.getAllAdmins();
		int adminCount = allAdmins.size();
	
		assertThat(adminCount, is(2));
	}
	
	@Test
	public void shouldGetAllAllowedClans(){
		
		List<AllowedClan> allAllowedClans = securityDAO.getAllAllowedClans();
		
		int allowedClansCount = allAllowedClans.size();
	
		assertThat(allowedClansCount, is(1));
	}
	
	@Test
	public void shouldAddAdmin(){
		
		User user = new User();
		user.setAccountId(1425415963);
		
		Role role = new Role();
		
		role.setRoleId(1);
		
		user.setRole(role);
		
		securityDAO.addAdmin(user);
		
		List<User> allAdmins = securityDAO.getAllAdmins();
		int adminCount = allAdmins.size();
	
		assertThat(adminCount, is(3));
	}
	
	@Test
	public void shouldEditAdmin(){
		
		User user = new User();
		user.setAccountId(1001167560);
		
		Role role = new Role();
		
		role.setRoleId(1);
		
		user.setRole(role);
		
		securityDAO.editAdmin(user);
		
		user = userDAO.getUser(1001167560);
		
		int roleId = user.getRole().getRoleId();
		
		assertThat(roleId, is(1));
	}
	
	@Test
	public void shouldDeleteAdmin(){
		
		securityDAO.deleteAdmin(1001167560);
		
		List<User> allAdmins = securityDAO.getAllAdmins();
		int adminCount = allAdmins.size();
	
		assertThat(adminCount, is(1));
	}
	
	@Test
	public void shouldDeleteAllowedClan(){
		
		securityDAO.deleteClan(1000007315);
		
		List<AllowedClan> allAllowedClans = securityDAO.getAllAllowedClans();
		
		int allowedClansCount = allAllowedClans.size();
	
		assertThat(allowedClansCount, is(0));
	}
}


