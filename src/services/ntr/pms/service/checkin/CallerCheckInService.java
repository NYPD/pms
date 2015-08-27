package services.ntr.pms.service.checkin;

import java.util.Map;

import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.information.MapDetail;

public interface CallerCheckInService {
	
	Map<String, MapDetail> getMapNames();
	
	void checkIn(CallerAttendance callerAttendance);

}
