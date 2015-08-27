package services.ntr.pms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import services.ntr.pms.annotation.DefaultDatabase;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.util.TimeFrame;

@DefaultDatabase
public interface PlayerAttendanceDAO
{
	@Results(value = {@Result(property = "checkInTimeUsingTimestamp", column = "checkInTime")})
	@Select("SELECT * from PMS.ATTENDANCE ATTE")
	List<Attendance> getAttendances(); //DONT USE THIS
	
	@Results(value = {@Result(property = "checkInTimeUsingTimestamp", column = "checkInTime")})
	@Select("SELECT * FROM PMS.ATTENDANCE ATTE WHERE ATTE.AccountId = #{accountId}")
	List<Attendance> getAttendanceForPlayer(Player player);

	@Results(value = {@Result(property = "checkInTimeUsingTimestamp", column = "checkInTime")})
	@Select("SELECT * FROM pms.attendance WHERE AccountId = #{accountId} ORDER BY AttendanceId DESC LIMIT 1;")
	Attendance getLastAttendanceForPlayer(Player player);
	
	List<Attendance>  getAttendancesForPlayer(@Param("playerAccountId") long playerAccountId, @Param("timeFrame") TimeFrame timeFrame);
	
	void addAttendance(Attendance attendance);
	
	void addAttendanceWithCheckIn(Attendance attendance);

	List<AttendanceHistory> getClanAttendanceHistory(@Param("clanId") long clanId, @Param("timeFrame") TimeFrame timeFrame);
}
