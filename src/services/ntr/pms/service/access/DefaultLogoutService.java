package services.ntr.pms.service.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;

/**
 * Service implementation class containing methods that deal logging out a Player
 * from PMS.
 * 
 * @author NYPD
 *
 */
@Service
public class DefaultLogoutService implements LogoutService{
	
	@Autowired
	private APIRequestDAO apiRequestDAO;

	@Override
	public void logoutPlayer(String accessToken){
		apiRequestDAO.logoutPlayer(accessToken);
	}

}
