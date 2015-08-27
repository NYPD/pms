package services.ntr.pms.service.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.CallerAttendanceDAO;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.information.NamingService;

@Service
@Qualifier(value="CallerAttendanceHistoryService")
public class CallerAttendanceHistoryService implements AttendanceHistoryService {
	
	@Autowired
	private CallerAttendanceDAO callerAttendanceDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;

	@Override
	public List<AttendanceHistory> getAttendanceHistory(long clanId, TimeFrame timeFrame) {
		
		List<AttendanceHistory> callerAttendanceHistory = callerAttendanceDAO.getCallerAttendanceHistory(clanId, timeFrame);
		
		namingService.name(callerAttendanceHistory);
		
		return callerAttendanceHistory;
	}

}
