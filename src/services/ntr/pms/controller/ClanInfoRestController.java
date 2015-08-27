package services.ntr.pms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.incentive.IncentivePlayer;
import services.ntr.pms.model.incentive.IncentiveTank;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.clanInformation.IncentiveService;
import services.ntr.pms.service.history.AttendanceHistoryService;

@RestController
public class ClanInfoRestController{

	@Autowired
	@Qualifier(value="PlayerAttendanceHistoryService")
	private AttendanceHistoryService attendanceHistoryService;
	
	@Autowired
	private IncentiveService incentiveService;
	
	@RequestMapping(value = "/retrieve-attendance-history")
	public List<AttendanceHistory> retrieveAttendanceHistory(TimeFrame timeFrame, HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();

		List<AttendanceHistory> clanAttendanceHistory = attendanceHistoryService.getAttendanceHistory(clanId, timeFrame);

		return clanAttendanceHistory;
	}
	
	@RequestMapping(value = "/top-incentive-players")
	public List<IncentivePlayer> getTopIncentivePlayers(HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();

		List<IncentivePlayer> topIncentivePlayers = incentiveService.getLimitedTopIncentivePlayersByClan(clanId, 5);

		return topIncentivePlayers;
	}
	
	@RequestMapping(value = "/top-incentive-tanks")
	public List<IncentiveTank> getTopIncentiveTanks(HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();

		List<IncentiveTank> topTanksUnlocked = incentiveService.getLimitedTopTanksUnlockedByClan(clanId, 5);

		return topTanksUnlocked;
	}
	
}

