package services.ntr.pms.service.information;

import java.util.List;
import java.util.Map;

import services.ntr.pms.model.information.TankInformation;

public interface TankInformationService{

	Map<Integer, TankInformation> getTankInformationMap();
	List<TankInformation> getCompleteTankInformationList();
	List<TankInformation> getSpecificTiersTankInformationList(Integer[] tierList);
	TankInformation getTankInfo(int tankId);
}
