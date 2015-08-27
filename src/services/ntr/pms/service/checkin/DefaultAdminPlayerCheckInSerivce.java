package services.ntr.pms.service.checkin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.PlayerAttendanceDAO;
import services.ntr.pms.exception.PlayerHasAlreadyBeenCheckedInException;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.util.DateUtil;

@Service
public class DefaultAdminPlayerCheckInSerivce implements AdminPlayerCheckInSerivce {

	@Autowired
	PlayerAttendanceDAO playerAttendanceDAO;
	
	@Override
	public void manualCheckIn(Attendance attendance) {
		
		boolean playerHasAlreadyCheckedIn = checkIfPlayerHasAlreadyBeenCheckedIn(attendance);
		
		if(playerHasAlreadyCheckedIn) throw new PlayerHasAlreadyBeenCheckedInException(attendance);
		
		playerAttendanceDAO.addAttendanceWithCheckIn(attendance);

	}

	private boolean checkIfPlayerHasAlreadyBeenCheckedIn(Attendance attendance) {
		
		long playerAccountId = attendance.getAccountId();
		
		long checkInTime = attendance.getCheckInTime();
		
		DateUtil dateUtil = new DateUtil(checkInTime);
		
		long startOfDay = dateUtil.getStartOfDay();
		long endOfDay = dateUtil.getEndOfDay();
		
		TimeFrame timeFrame = new TimeFrame();
		
		timeFrame.setStartTime(startOfDay);
		timeFrame.setEndTime(endOfDay);
		
		List<Attendance> attendancesForPlayer = playerAttendanceDAO.getAttendancesForPlayer(playerAccountId, timeFrame);
		
		boolean hasAlreadyBeenCheckedIn = !attendancesForPlayer.isEmpty();
		
		return hasAlreadyBeenCheckedIn;
	}

}
