package services.ntr.pms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import services.ntr.pms.dao.PayoutDAO;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.checkin.PlayerCheckInService;
import services.ntr.pms.service.history.PayoutHistoryService;

@RestController
public class HomeRestController {

	@Autowired
	private PlayerCheckInService playerCheckInService;
	@Autowired
	private PayoutHistoryService payoutHistoryService;
	@Autowired
	private PayoutDAO payoutDAO;
	
	@RequestMapping(value = "/check-in")
	public void checkIn(HttpSession session) {

		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		playerCheckInService.checkIn(player, membersInfo);
		
	}
	
	@RequestMapping(value = "/get-updated-highchart-information")
	public int[] getUpdatedHighchartInformation(TimeFrame timeFrame, HttpSession session) {
		
		Player player = (Player) session.getAttribute("player");
		
		int[] monthlyPayoutHistoryAmounts = payoutHistoryService.getMonthlyPayoutHistoryAmounts(player, timeFrame);
		
		return monthlyPayoutHistoryAmounts;
		
	}
}
