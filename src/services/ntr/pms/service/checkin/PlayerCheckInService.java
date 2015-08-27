package services.ntr.pms.service.checkin;

import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.util.TimeFrame;

public interface PlayerCheckInService
{
	void checkIn(Player player, MembersInfo membersInfo);
	
	Attendance getLastCheckIn(Player player);
	
	boolean checkIfUserIsWithinTimeFrame(Player player, MembersInfo membersInfo);
	
	TimeFrame getCheckInEventTimeFrame(long clanId);
}
