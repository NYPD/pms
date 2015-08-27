package services.ntr.pms.service.history;

import java.util.List;

import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.util.TimeFrame;

public interface AttendanceHistoryService{

	List<AttendanceHistory> getAttendanceHistory(long clanId, TimeFrame timeFrame);
}
