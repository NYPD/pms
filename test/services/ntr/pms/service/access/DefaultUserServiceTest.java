package services.ntr.pms.service.access;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultUserServiceTest {
	
	@Autowired
	UserService userService;
	
	@Before
	public void setUp() throws Exception
	{}
	
	@Test
	public void shouldGetUserRole(){
		
		int nypdAccountId = 1001167560;
		
		User user = userService.getUser(nypdAccountId);
		
		Role role = user.getRole();
		
		int roleId = role.getRoleId();
		
		assertThat(roleId, is(3));
		
	}
	
}
