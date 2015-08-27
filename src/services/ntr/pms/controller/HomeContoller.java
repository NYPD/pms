package services.ntr.pms.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.annotation.DefaultController;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.CarouselNews;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.payout.Payout;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.checkin.PlayerCheckInService;
import services.ntr.pms.service.history.PayoutHistoryService;
import services.ntr.pms.service.information.BattleService;
import services.ntr.pms.service.information.EventService;
import services.ntr.pms.service.information.NewsCarouselService;
import services.ntr.pms.util.TimeUtil;

/**
 * Default controller which handles all non-ajax request regarding the home page
 * @author NYPD
 *
 */
@DefaultController
public class HomeContoller {

	@Autowired
	private BattleService battleService;
	@Autowired
	private PayoutHistoryService payoutHistoryService;
	@Autowired
	private NewsCarouselService newsCarouselService;
	@Autowired
	private PlayerCheckInService playerCheckInService;
	@Autowired
	private EventService eventService;
	
	@RequestMapping(value = "/home")
	public ModelAndView getHomePage(HttpServletRequest request, HttpSession session) {

		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		String clanIdString = membersInfo.getClanIdAsString();
		String accessToken = player.getAccessToken();
		long clanId = membersInfo.getClanId();
		
		TimeFrame checkInEventTimeFrame = playerCheckInService.getCheckInEventTimeFrame(clanId);
		
		List<Event> events = eventService.getEventsByTimeFrame(clanId, checkInEventTimeFrame);
		List<ClanBattle> clanBattles = battleService.getClanBattleInformation(clanIdString, accessToken);
		List<Payout> payouts = payoutHistoryService.getPlayerPayouts(player);
		List<CarouselNews> clanNews = newsCarouselService.getActiveClanNews(clanId);
		List<Integer> yearsSincePmsCreation = TimeUtil.getYearsSincePmsCreation();
			
		Collections.sort(clanBattles);
		int clanNewsSize = clanNews.size();
		
		ModelAndView mav = new ModelAndView("home");
		
		mav.addObject("clanBattles", clanBattles);
		mav.addObject("events", events);
		mav.addObject("payouts", payouts);
		mav.addObject("yearsSincePmsCreation", yearsSincePmsCreation);
		mav.addObject("clanNews", clanNews);
		mav.addObject("clanNewsSize", clanNewsSize);
		
		return mav;

	}
	
}
	
	
