package services.ntr.pms.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import services.ntr.pms.model.access.User;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.history.CallerMapFrequency;
import services.ntr.pms.model.incentive.IncentivePayoutAmountsTableRow;
import services.ntr.pms.model.incentive.TankIncentiveDefaultPayout;
import services.ntr.pms.model.information.ClanInfo;
import services.ntr.pms.model.information.ClanMember;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.information.LocalClanSettings;
import services.ntr.pms.model.information.MapDetail;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.CarouselNews;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.TankInformation;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.access.SecurityService;
import services.ntr.pms.service.checkin.AdminPlayerCheckInSerivce;
import services.ntr.pms.service.checkin.CallerCheckInService;
import services.ntr.pms.service.clanInformation.IncentiveService;
import services.ntr.pms.service.history.AttendanceHistoryService;
import services.ntr.pms.service.history.CallerMapFrequencyService;
import services.ntr.pms.service.information.EventService;
import services.ntr.pms.service.information.LocalClanSettingsService;
import services.ntr.pms.service.information.NewsCarouselService;
import services.ntr.pms.service.information.TankInformationService;


@RestController
@RequestMapping(value = "/admin/clan")
public class ClanAdminRestController {
	
	@Autowired
	private NewsCarouselService newsCarouselService;
	@Autowired
	private CallerCheckInService callerCheckInService;
	@Autowired
	@Qualifier(value="CallerAttendanceHistoryService")
	private AttendanceHistoryService attendanceHistoryService;
	@Autowired
	private CallerMapFrequencyService callerMapFrequencyService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private AdminPlayerCheckInSerivce adminPlayerCheckInSerivce;
	@Autowired
	private LocalClanSettingsService localClanSettingsService;
	@Autowired
	private EventService eventService;
	@Autowired
	private TankInformationService tankInformationService;
	@Autowired
	private IncentiveService incentiveService;
	
	private static final String NEWS_CAROUSEL_TOOL = "jsp-fragment/admin-tools/clan-tools/news-carousel";
	private static final String NEWS_CAROUSEL_PUT_FORM = "jsp-fragment/admin-tools/clan-tools/modal-content/news-carousel-put";
	private static final String NEWS_CAROUSEL_DELETE_FORM = "jsp-fragment/admin-tools/clan-tools/modal-content/news-carousel-delete";
	private static final String CALLER_ATTENDANCE_TOOL = "jsp-fragment/admin-tools/clan-tools/caller-attendance";
	private static final String PLAYER_CHECKIN_TOOL = "jsp-fragment/admin-tools/clan-tools/player-checkin";
	private static final String CUSTOM_EVENT_TOOL = "jsp-fragment/admin-tools/clan-tools/custom-event";
	private static final String CUSTOM_EVENT_PUT_FORM = "jsp-fragment/admin-tools/clan-tools/modal-content/event-put";
	private static final String CUSTOM_EVENT_DELETE_FORM = "jsp-fragment/admin-tools/clan-tools/modal-content/event-delete";
	private static final String CLAN_SETTINGS_TOOL = "jsp-fragment/admin-tools/clan-tools/clan-settings";
	private static final String INCENTIVE_DEFAULTS_TOOL = "jsp-fragment/admin-tools/clan-tools/incentive-defaults";
	
	@RequestMapping(value = "/get-news-carousel-tool", method = RequestMethod.GET)
	public ModelAndView getNewsCarouselTool(HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		List<CarouselNews> clanNews = newsCarouselService.getClanNews(clanId);
		
		ModelAndView mav = new ModelAndView(NEWS_CAROUSEL_TOOL);
		mav.addObject("clanNews", clanNews);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-news-carousel-add-modal", method = RequestMethod.GET)
	public ModelAndView getNewsCarouselAddModal(HttpSession session) {

		ModelAndView mav = new ModelAndView(NEWS_CAROUSEL_PUT_FORM);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-news-carousel-edit-modal", method = RequestMethod.GET)
	public ModelAndView getNewsCarouselEditModal(@RequestParam(value = "newsCarouselId") int newsCarouselId, HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		CarouselNews carouselNews = newsCarouselService.getClanNewsById(clanId, newsCarouselId);

		ModelAndView mav = new ModelAndView(NEWS_CAROUSEL_PUT_FORM);
		mav.addObject("carouselNews", carouselNews);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-news-carousel-copy-modal", method = RequestMethod.GET)
	public ModelAndView getNewsCarouselCopyModal(int newsCarouselId, HttpSession session) {
		
		ModelAndView mav = getNewsCarouselEditModal(newsCarouselId, session);
		
		ModelMap modelMap = mav.getModelMap();
		
		CarouselNews carouselNews = (CarouselNews) modelMap.get("carouselNews");
		carouselNews.setNewsCarouselId(0);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-news-carousel-delete-modal", method = RequestMethod.GET)
	public ModelAndView getNewsCarouselDeleteModal(@RequestParam(value = "newsCarouselId") int newsCarouselId, HttpSession session) {
		
		ModelAndView mav = new ModelAndView(NEWS_CAROUSEL_DELETE_FORM);
		mav.addObject("newsCarouselId", newsCarouselId);
		
		return mav;
	}
	
	@RequestMapping(value = {"/put-news-carousel"}, method = RequestMethod.POST)
	public void editNewsCarousel(CarouselNews carouselNews, HttpSession session){
		
		User user = (User) session.getAttribute("user");
		boolean shouldExitMethod = isAuthorizedToNewsFunction(carouselNews, user);
		if(shouldExitMethod ) return;

		Player player = (Player) session.getAttribute("player");
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long accountId = player.getAccountId();
		long clanId = membersInfo.getClanId();
		
		carouselNews.setCreatedByAccountId(accountId);
		carouselNews.setModifiedByAccountId(accountId);
		carouselNews.setClanId(clanId);

		newsCarouselService.putNews(carouselNews);
	}
	
	@RequestMapping(value = "/delete-news-carousel", method = RequestMethod.POST)
	public void deleteNewsCarousel(int newsCarouselId, HttpSession session) {
		
		User user = (User) session.getAttribute("user");
		boolean shouldExitMethod = isAuthorizedToNewsFunction(newsCarouselId, user);
		if(shouldExitMethod ) return;
		
		newsCarouselService.removeNews(newsCarouselId);
	}
	
	private boolean isAuthorizedToNewsFunction(int newsCarouselId, User user){
		CarouselNews carouselNews = new CarouselNews();
		carouselNews.setNewsCarouselId(newsCarouselId);
		return isAuthorizedToNewsFunction(carouselNews , user);
	}
	private boolean isAuthorizedToNewsFunction(CarouselNews carouselNews, User user){
		int newsCarouselId = carouselNews.getNewsCarouselId();
		CarouselNews storedCarouselNews = newsCarouselService.getNewsById(newsCarouselId);
		boolean isGlobalNews = carouselNews.isGlobal();
		boolean userIsSystemAdmin = securityService.checkIfSystemAdmin(user);
		boolean isCurrentlyGlobalNews = storedCarouselNews != null && storedCarouselNews.isGlobal();
		boolean isNotAuthorizedToNews = !userIsSystemAdmin && (isGlobalNews || isCurrentlyGlobalNews);
		return isNotAuthorizedToNews;
	}
	
	@RequestMapping(value = "/get-caller-attendance-tool", method = RequestMethod.GET)
	public ModelAndView getCallerAttendanceTool(HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		ClanInfo clanInfo = (ClanInfo) session.getAttribute("clanInfo");
		
		long clanId = membersInfo.getClanId();
		
		List<ClanMember> clanMemberList = clanInfo.getMembers();
		List<CallerMapFrequency> callerMapFrequencies = callerMapFrequencyService.getCallerMapFrequencies(clanId);
		Map<String, MapDetail> mapDetailMappedByArenaId = callerCheckInService.getMapNames();
		
		Collection<MapDetail> mapDetailCollection = mapDetailMappedByArenaId.values();//TODO why dont we use this right away?
		List<MapDetail> mapDetails = new ArrayList<MapDetail>(mapDetailCollection);
		
		ModelAndView mav = new ModelAndView(CALLER_ATTENDANCE_TOOL);
		
		Collections.sort(clanMemberList, ClanMember.ACCOUNT_NAME_ORDER);
		Collections.sort(mapDetails);
		
		mav.addObject("clanMemberList", clanMemberList);
		mav.addObject("mapDetails", mapDetails);
		mav.addObject("callerMapFrequencies", callerMapFrequencies);
		
		return mav;
	}
	
	@RequestMapping(value = "/caller-check-in", method = RequestMethod.POST)
	public void callerCheckIn(CallerAttendance callerAttendance, HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		long clanId = membersInfo.getClanId();
		
		callerAttendance.setClanId(clanId);
		
		callerCheckInService.checkIn(callerAttendance);
		
	}
	
	@RequestMapping(value = "/retrieve-caller-attendance-history", method = RequestMethod.GET)
	public List<AttendanceHistory> retrieveCallerAttendanceHistory(TimeFrame timeFrame, HttpSession session) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		List<AttendanceHistory> callerAttendanceHistory = attendanceHistoryService.getAttendanceHistory(clanId, timeFrame);
		
		return callerAttendanceHistory;
	}
	
	@RequestMapping(value = "/get-player-checkin-tool", method = RequestMethod.GET)
	public ModelAndView getPlayerCheckinTool(HttpSession session) {
		
		ClanInfo clanInfo = (ClanInfo) session.getAttribute("clanInfo");
		
		List<ClanMember> clanMemberList = clanInfo.getMembers();
		
		Collections.sort(clanMemberList, ClanMember.ACCOUNT_NAME_ORDER);
		
		ModelAndView mav = new ModelAndView(PLAYER_CHECKIN_TOOL);
		mav.addObject("clanMemberList", clanMemberList);
		
		return mav;
	}
	
	@RequestMapping(value = "/manual-player-check-in", method = RequestMethod.POST)
	public void manualPlayerCheckIn(Attendance attendance, HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		//TODO if clanMember is null throw a invalid accountId exception. Do this for all other places too
		
		attendance.setClanId(clanId);
		
		adminPlayerCheckInSerivce.manualCheckIn(attendance);
		
	}
	
	@RequestMapping(value = "/get-custom-event-tool", method = RequestMethod.GET)
	public ModelAndView getCustomEventTool(HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		long clanId = membersInfo.getClanId();
		
		List<Event> upcomingEvents = eventService.getUpcomingEvents(clanId);
		
		ModelAndView mav = new ModelAndView(CUSTOM_EVENT_TOOL);
		mav.addObject("upcomingEvents", upcomingEvents);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-upcoming-event-add-modal", method = RequestMethod.GET)
	public ModelAndView getUpcomingEventAddModal(HttpSession session){
		
		ModelAndView mav = new ModelAndView(CUSTOM_EVENT_PUT_FORM);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/get-upcoming-event-edit-modal", method = RequestMethod.GET)
	public ModelAndView getUpcomingEventEditModal(int eventId, HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		Event event = eventService.getClanEventById(clanId, eventId);
		
		ModelAndView mav = new ModelAndView(CUSTOM_EVENT_PUT_FORM);
		mav.addObject("event", event);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/get-upcoming-event-delete-modal", method = RequestMethod.GET)
	public ModelAndView getUpcomingEventDeleteModal(int eventId, HttpSession session){
		
		ModelAndView mav = new ModelAndView(CUSTOM_EVENT_DELETE_FORM);
		mav.addObject("eventId", eventId);
		
		return mav;
		
	}
	
	@RequestMapping(value = "/put-event", method = RequestMethod.POST)
	public void putEvent(Event event, HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		event.setClanId(clanId);
		
		eventService.putEvent(event);
		
	}
	
	@RequestMapping(value = "/delete-event", method = RequestMethod.POST)
	public void deleteEvent(int eventId, HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		eventService.deleteClanEvent(clanId, eventId);
		
	}
	
	@RequestMapping(value = "/get-clan-settings-tool", method = RequestMethod.GET)
	public ModelAndView getClanSettingsTool(HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		ModelAndView mav = new ModelAndView(CLAN_SETTINGS_TOOL);
		mav.addObject("localClanSettings", localClanSettings);
		
		return mav;
	}
	
	@RequestMapping(value = "/put-clan-settings", method = RequestMethod.POST)
	public void putClanSettings(LocalClanSettings localClanSettings, HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		
		long clanId = membersInfo.getClanId();
		
		localClanSettings.setClanId(clanId);
		
		localClanSettingsService.updateLocalClanSettings(localClanSettings);
		
	}
	
	@RequestMapping(value = "/get-incentive-default-tool", method = RequestMethod.GET)
	public ModelAndView getIncentiveDefaultTool(HttpSession session){
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		long clanId = membersInfo.getClanId();
		
		List<TankInformation> tierTenTankInformationList = tankInformationService.getSpecificTiersTankInformationList(new Integer[] {10});
		List<IncentivePayoutAmountsTableRow> incentivePayoutAmountsTableRows = incentiveService.getSpecificTankIncentiveAmountTableRows(tierTenTankInformationList, clanId);
		
		ModelAndView mav = new ModelAndView(INCENTIVE_DEFAULTS_TOOL);
		mav.addObject("tankIncentiveAmountTableRows", incentivePayoutAmountsTableRows);
		
		return mav;
	}
	
	@RequestMapping(value = "/get-incentive-payout-amounts-table-rows-by-tier", method = RequestMethod.GET)
	public List<IncentivePayoutAmountsTableRow> getIncentivePayoutAmountsTableRowsByTier(HttpSession session, @Param(value="tankTiers") Integer[] tankTiers) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		long clanId = membersInfo.getClanId();
		
		List<TankInformation> tankInformationList = tankInformationService.getSpecificTiersTankInformationList(tankTiers);
		List<IncentivePayoutAmountsTableRow> incentivePayoutAmountsTableRows = incentiveService.getSpecificTankIncentiveAmountTableRows(tankInformationList, clanId);
		
		return incentivePayoutAmountsTableRows;
	}

	
	@RequestMapping(value = "/save-tank-incentive-default-payouts", method = RequestMethod.POST)
	public void saveTankIncentiveDefaultPayouts(HttpSession session, @RequestBody List<TankIncentiveDefaultPayout> tankIncentiveDefaultPayouts) {
		
		MembersInfo membersInfo = (MembersInfo) session.getAttribute("memberInfo");
		long clanId = membersInfo.getClanId();
		
		incentiveService.saveTankIncentiveDefaultPayouts(tankIncentiveDefaultPayouts, clanId);
	}
	
}
