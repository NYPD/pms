package services.ntr.pms.service.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.CallerAttendanceDAO;
import services.ntr.pms.model.history.CallerMapFrequency;
import services.ntr.pms.service.information.NamingService;

@Service
public class DefaultCallerMapFrequencyService implements CallerMapFrequencyService {

	@Autowired
	private CallerAttendanceDAO callerAttendanceDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	@Override
	public List<CallerMapFrequency> getCallerMapFrequencies(long clanId) {
		
		List<CallerMapFrequency> callerMapFrequencyList = callerAttendanceDAO.getCallerMapFrequencies(clanId);
		
		namingService.name(callerMapFrequencyList);
		
		return callerMapFrequencyList;
	}

}
