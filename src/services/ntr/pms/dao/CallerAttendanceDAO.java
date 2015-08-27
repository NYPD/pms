package services.ntr.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.history.CallerMapFrequency;
import services.ntr.pms.model.util.TimeFrame;

@DefaultDatabase
public interface CallerAttendanceDAO {

	List<AttendanceHistory> getCallerAttendanceHistory(@Param("clanId") long clanId, @Param("timeFrame") TimeFrame timeFrame);
	List<CallerMapFrequency> getCallerMapFrequencies(@Param("clanId") long clanId);
	
	void addCallerAttendance(CallerAttendance callerAttendance);
	
}
