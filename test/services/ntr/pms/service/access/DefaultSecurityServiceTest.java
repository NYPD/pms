package services.ntr.pms.service.access;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
import services.ntr.pms.service.access.SecurityService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultSecurityServiceTest {

	@Autowired
	SecurityService securityService;
	@Autowired
	UserService userService;
	
	@Before
	public void setUp() throws Exception
	{}
	
	@Test
	public void shouldGetAllowedClan(){
		long clanId = 1000007315;
		AllowedClan allowedClan = securityService.getAllowedClan(clanId);
		
		boolean notNullAllowedclan = allowedClan != null;
		
		assertThat(notNullAllowedclan, is(true));
	}
	
	@Test
	public void shouldVerifyClanAdmin(){
		
		User user = new User();
		Role userRole = new Role();
		
		userRole.setRoleId(2);
		
		user.setRole(userRole);
		
		boolean isAdmin = securityService.checkIfClanAdmin(user);
		
		assertThat(isAdmin, is(true));
	}
	
	@Test
	public void shouldVerifySystemAdmin(){
		
		User user = new User();
		Role userRole = new Role();
		
		userRole.setRoleId(3);
		
		user.setRole(userRole);
		
		boolean isSytemAdmin = securityService.checkIfSystemAdmin(user);
		
		assertThat(isSytemAdmin, is(true));
	}
	
	@Test
	public void shouldNotVerifyAdmin(){
		
		User user = new User();
		Role userRole = new Role();
		
		userRole.setRoleId(0);
		
		user.setRole(userRole);
		
		boolean isAdmin = securityService.checkIfClanAdmin(user);
		
		assertThat(isAdmin, is(false));
	}
	
	@Test
	public void shouldNotVerifySystemAdmin(){
		
		User user = new User();
		Role userRole = new Role();
		
		userRole.setRoleId(1);
		
		user.setRole(userRole);
		
		boolean isSytemAdmin = securityService.checkIfSystemAdmin(user);
		
		assertThat(isSytemAdmin, is(false));
	}
	
	@Test
	public void shouldVerifyClan(){
		long clanId = 1000007315;
		
		boolean isValidClan = securityService.checkIfValidClan(clanId);
		
		assertThat(isValidClan, is(true));
	}
	
	@Test//Same as above but maybe we will use it diffrently in the future
	public void shouldCheckIfClanExists(){
		long clanId = 1000007315;
		
		boolean clanExists = securityService.checkIfClanExists(clanId);
		
		assertThat(clanExists, is(true));
	}
	
	@Test
	public void shouldNotVerifyClan(){
		
		long clanId = 1000007314;
		
		boolean isValidClan = securityService.checkIfValidClan(clanId);
		
		assertThat(isValidClan, is(false));
	}
	
	@Test
	public void shouldAddAllowedClan(){
		
		AllowedClan allowedClan = new AllowedClan();
		
		long clanId = 1000008000L;
		String clanName = "TESTCLAN";
		
		allowedClan.setClanId(clanId);
		allowedClan.setClanName(clanName);
		
		securityService.addAllowedClan(allowedClan);
		
		int allowedClanId = allowedClan.getAllowedClanId();
		
		assertThat(allowedClanId, is(2));
	}
	
	@Test
	public void shouldGetAllAdmins(){
		
		List<User> allAdmins = securityService.getAllAdmins();
		
		int numberOfAdmins = allAdmins.size();
		
		assertThat(numberOfAdmins, is(2));
		
	}
	
	@Test
	public void shouldGetAllAllowedClans(){
		
		List<AllowedClan> allAllowedClans = securityService.getAllAllowedClans();
		
		int numberOfAllowedClans = allAllowedClans.size();
		
		assertThat(numberOfAllowedClans, is(1));
		
	}
	
	@Test
	public void shouldGetAllRoles(){
		
		List<Role> allRoles = securityService.getAllRoles();
		
		int amountOfRoles = allRoles.size();
		
		assertThat(amountOfRoles, is(3));
		
	}
	
	@Test
	public void shouldAddAdmin(){
		
		long fakeAccountId = 9999999999L;
		
		User user = new User();
		user.setAccountId(fakeAccountId);
		
		Role role = new Role();
		
		role.setRoleId(1);
		
		user.setRole(role);
		
		securityService.addAdmin(user);
		
		List<User> allAdmins = securityService.getAllAdmins();
		
		int numberOfAdmins = allAdmins.size();
		
		assertThat(numberOfAdmins, is(3));
		
	}
	
	@Test
	public void shouldEditAdmin(){
		
		User user = new User();
		user.setAccountId(1001167560);
		
		Role role = new Role();
		
		role.setRoleId(1);
		
		user.setRole(role);
		
		securityService.editAdmin(user);
		
		user = userService.getUser(1001167560);
		
		int roleId = user.getRole().getRoleId();
		
		assertThat(roleId, is(1));
	}
	
	@Test
	public void shouldDeleteAdmin(){
		
		securityService.deleteAdmin(1001167560);
		
		List<User> allAdmins = securityService.getAllAdmins();
		int adminCount = allAdmins.size();
	
		assertThat(adminCount, is(1));
	}
	
	@Test
	public void shouldDeleteAllowedClan(){
		
		securityService.deleteClan(1000007315);
		
		List<AllowedClan> allAllowedClans = securityService.getAllAllowedClans();
		
		int allowedClansCount = allAllowedClans.size();
	
		assertThat(allowedClansCount, is(0));
	}
}
