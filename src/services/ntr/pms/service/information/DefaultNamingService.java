package services.ntr.pms.service.information;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.model.information.Player;

@Service
public class DefaultNamingService implements NamingService {

	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public void name(List<? extends Nameable> nameables) {
		
		List<Long> accountIds = new ArrayList<Long>();
		
		boolean noNamesToRetrieve = nameables.size() == 0;
		
		if(noNamesToRetrieve) return;
		
		for (Nameable nameable : nameables) {
			long accountId = nameable.getAccountId();
			accountIds.add(accountId);
		}
		
		Map<Long, Player> playerInformationMap = apiRequestDAO.getPlayerInformationMap(accountIds);
		
		for (Nameable nameable: nameables) {
			long accountId = nameable.getAccountId();
			
			Player player = playerInformationMap.get(accountId);
			String nickname = player.getNickname();
			
			nameable.setNickname(nickname);
			
		}
	}
	
}
