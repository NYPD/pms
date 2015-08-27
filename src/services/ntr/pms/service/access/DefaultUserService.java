package services.ntr.pms.service.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.UserDAO;
import services.ntr.pms.model.access.Role;
import services.ntr.pms.model.access.User;

@Service
public class DefaultUserService implements UserService {

	@Autowired
	UserDAO userDAO;
	
	@Override
	public User getUser(long accountId) {
		
		User user = userDAO.getUser(accountId);
		
		boolean notInUserTable = user == null;
		
		if(notInUserTable) user = new User();
		
		Role role = user.getRole();
		
		boolean hasNoUserRole = role == null;
		
		if(hasNoUserRole) role = new Role();
		
		user.setRole(role);
		
		return user;

	}

}
