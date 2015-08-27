package services.ntr.pms.service.history;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PlayerAttendanceDAO;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.information.NamingService;

@Service
@Qualifier(value="PlayerAttendanceHistoryService")
public class PlayerAttendanceHistoryService implements AttendanceHistoryService{

	@Autowired
	private PlayerAttendanceDAO playerAttendanceDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private NamingService namingService;
	
	@Override
	public List<AttendanceHistory> getAttendanceHistory(long clanId, TimeFrame timeFrame){
		
		List<AttendanceHistory> clanAttendanceHistory = playerAttendanceDAO.getClanAttendanceHistory(clanId, timeFrame);
		
		namingService.name(clanAttendanceHistory);
		return clanAttendanceHistory;
		
	}

}