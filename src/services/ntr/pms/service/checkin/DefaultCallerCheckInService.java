package services.ntr.pms.service.checkin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.CallerAttendanceDAO;
import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.information.MapDetail;

@Service
public class DefaultCallerCheckInService implements CallerCheckInService {

	@Autowired
	private CallerAttendanceDAO callerAttendanceDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	
	@Override
	public Map<String, MapDetail> getMapNames() {

		Map<String, MapDetail> mapDetailsMap = apiRequestDAO.getMapDetailMappedByArenaId();
		
		return mapDetailsMap;
	}

	@Override
	public void checkIn(CallerAttendance callerAttendance) {
		
		callerAttendanceDAO.addCallerAttendance(callerAttendance);
	}

}
