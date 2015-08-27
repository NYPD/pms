package services.ntr.pms.service.information;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.TankIncentiveDAO;
import services.ntr.pms.model.information.TankInformation;

@Service
public class DefaultTankInformationService implements TankInformationService{

	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private TankIncentiveDAO tankIncentiveDAO;
	
	@Override
	public Map<Integer, TankInformation> getTankInformationMap(){
		
		Map<Integer, TankInformation> tankInformationMap = apiRequestDAO.getTankInformationMap();
		
		return tankInformationMap;
	}

	@Override
	public TankInformation getTankInfo(int tankId){
		
		Map<Integer, TankInformation> tankInformationMap = getTankInformationMap();
		
		TankInformation tankInformation = tankInformationMap.get(tankId);
		
		//TODO throw tankIdInvalid Exception
		
		return tankInformation;
	}

	@Override
	public List<TankInformation> getCompleteTankInformationList(){
		
		Map<Integer, TankInformation> tankInformationMap = getTankInformationMap();
		List<TankInformation> completeTankInformationList = new ArrayList<TankInformation>(tankInformationMap.values());
		
		return completeTankInformationList;
	}

	@Override
	public List<TankInformation> getSpecificTiersTankInformationList(Integer[] specificTierArray) {
		
		List<Integer> specificTierList = Arrays.asList(specificTierArray);
		
		List<TankInformation> allTankInformationList = getCompleteTankInformationList();
		List<TankInformation> specificTierTankInformationList = new ArrayList<TankInformation>();
		
		for (TankInformation tankInformation : allTankInformationList){
			
			int level = tankInformation.getLevel();
			
			boolean isSpecifiedTier = specificTierList.contains(level);
			
			if(isSpecifiedTier) specificTierTankInformationList.add(tankInformation);
		}
		
		return specificTierTankInformationList;
	}

}
