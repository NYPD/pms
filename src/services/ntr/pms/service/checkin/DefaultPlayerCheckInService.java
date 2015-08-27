package services.ntr.pms.service.checkin;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import services.ntr.pms.dao.APIRequestDAO;
import services.ntr.pms.dao.PlayerAttendanceDAO;
import services.ntr.pms.exception.AlreadyCheckedInException;
import services.ntr.pms.exception.CheckInNotWithinBattleTimeFrame;
import services.ntr.pms.exception.PlayerIsBannedException;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.information.ClanBattle;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.information.LocalClanSettings;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.Private;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.information.EventService;
import services.ntr.pms.service.information.LocalClanSettingsService;

@Service
public class DefaultPlayerCheckInService implements PlayerCheckInService {
	
	private static Logger logger = Logger.getLogger(DefaultPlayerCheckInService.class);

	@Autowired
	private PlayerAttendanceDAO playerAttendanceDAO;
	@Autowired
	private APIRequestDAO apiRequestDAO;
	@Autowired
	private LocalClanSettingsService localClanSettingsService;
	@Autowired
	private EventService eventService;

	@Override
	public void checkIn(Player player, MembersInfo membersInfo){
		
		logger.debug("Starting check-in process");
		
		Private personal = player.getPrivate();
		long clanId = membersInfo.getClanId();
		
		boolean isPlayerBanned = personal.isBanned();
		if(isPlayerBanned) throw new PlayerIsBannedException(player);
		
		logger.debug("isPlayerBanned: " + isPlayerBanned);
		
		boolean playerHasAlreadyCheckedIn = checkIfUserHasAlreadyCheckedIn(player, clanId);
		if(playerHasAlreadyCheckedIn) throw new AlreadyCheckedInException(player);
		
		logger.debug("playerHasAlreadyCheckedIn: " + playerHasAlreadyCheckedIn);
		
		boolean playerIsNotWithinBattleTimeFrame = !checkIfUserIsWithinTimeFrame(player, membersInfo);
		if(playerIsNotWithinBattleTimeFrame) throw new CheckInNotWithinBattleTimeFrame(player);
		
		logger.debug("playerIsNotWithinBattleTimeFrame: " + playerIsNotWithinBattleTimeFrame);
		
		long accountId = player.getAccountId();

		Attendance newAttendance = new Attendance();
		newAttendance.setAccountId(accountId);
		newAttendance.setClanId(clanId);
		
		addAttendance(newAttendance);
		
		logger.debug("attendance succesfully added for player: " + membersInfo.getAccountId());
	}

	@Override
	public Attendance getLastCheckIn(Player player) {
		Attendance attendance = playerAttendanceDAO.getLastAttendanceForPlayer(player);
		
		return attendance;
	}
	
	@Override
	public boolean checkIfUserIsWithinTimeFrame(Player player, MembersInfo membersInfo){
		
		boolean isWithinBattleTimeFrame = false;
		boolean isWithinEventTimeFrame = false;
		
		String accessToken = player.getAccessToken();
		long clanId = membersInfo.getClanId();
		String clanIdAsString = membersInfo.getClanIdAsString();
		
		List<ClanBattle> clanBattles; 
		
		/**
		 * If at first you don't succeed, try again and hope wargaming ain't lame with their api
		 */
		try{
			clanBattles = getApiRequestDAO().getClanBattleInformation(clanIdAsString, accessToken);
		}catch(Exception ex){
			logger.error(ex);
			logger.info("Trying to get clan battles again...");
			clanBattles = getApiRequestDAO().getClanBattleInformation(clanIdAsString, accessToken);
			logger.info("Success!");
		}
		
		TimeFrame checkInEventTimeFrame = getCheckInEventTimeFrame(clanId);
		
		List<Event> upcomingEvents = getEventService().getEventsByTimeFrame(clanId, checkInEventTimeFrame);
		
		boolean noClanBattles = clanBattles.size() == 0;
		boolean noEvents = upcomingEvents.size() == 0;
		
		boolean thereIsNoBattlesOrEvents = noClanBattles && noEvents;
		if(thereIsNoBattlesOrEvents) return false;
		
		long currentTime = System.currentTimeMillis();
		
		if(!noClanBattles){
			TimeFrame battleTimeFrame = getCheckInTimeFrameForBattles(clanBattles, clanId);
			
			long earliestAllowedBattleCheckInTime = battleTimeFrame.getStartTime();
			long latestAllowedBattleCheckInTime = battleTimeFrame.getEndTime();
			
			isWithinBattleTimeFrame = currentTime >= earliestAllowedBattleCheckInTime && currentTime <= latestAllowedBattleCheckInTime;
		}
		
		if(!noEvents){
			TimeFrame eventTimeFrame = getCheckInTimeFrameForEvents(upcomingEvents , clanId);
			
			long earliestAllowedEventCheckInTime = eventTimeFrame.getStartTime();
			long latestAllowedEventCheckInTime = eventTimeFrame.getEndTime();
			
			isWithinEventTimeFrame = currentTime >= earliestAllowedEventCheckInTime && currentTime <= latestAllowedEventCheckInTime;
		}
		
		boolean isWithinTimeFrame = isWithinBattleTimeFrame || isWithinEventTimeFrame;
		
		return isWithinTimeFrame;
		
	}
	
	public void addAttendance(Attendance attendance){
		playerAttendanceDAO.addAttendance(attendance);
	}

	public PlayerAttendanceDAO getAttendanceDAO(){
		return playerAttendanceDAO;
	}

	public void setAttendanceDAO(PlayerAttendanceDAO playerAttendanceDAO){
		this.playerAttendanceDAO = playerAttendanceDAO;
	}
	
	private boolean checkIfUserHasAlreadyCheckedIn(Player player, long clanId){
		
		boolean hasAlreadyCheckedIn = false;
		
		Attendance attendance = playerAttendanceDAO.getLastAttendanceForPlayer(player);
		
		boolean playerHasNoPreviousAttendance = attendance == null;
		
		if(playerHasNoPreviousAttendance) return false;
		
		long lastCheckInTime = attendance.getCheckInTime();
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		TimeZone battleDayGmtOffsetAsTimeZone = localClanSettings.getBattleDayGmtOffsetAsTimeZone();
		
		Calendar currentDayAtMidnightInTheLandOfTanks = new GregorianCalendar(battleDayGmtOffsetAsTimeZone);
		currentDayAtMidnightInTheLandOfTanks.set(Calendar.HOUR_OF_DAY, 0);
		currentDayAtMidnightInTheLandOfTanks.set(Calendar.MINUTE, 0);
		currentDayAtMidnightInTheLandOfTanks.set(Calendar.SECOND, 0);
		
		Calendar lastCheckInCalendar = new GregorianCalendar();
		lastCheckInCalendar.setTimeInMillis(lastCheckInTime);

		hasAlreadyCheckedIn = lastCheckInCalendar.after(currentDayAtMidnightInTheLandOfTanks);
					
		return hasAlreadyCheckedIn;
		
	}
	
	private TimeFrame getCheckInTimeFrameForBattles(List<ClanBattle> clanBattles, long clanId){
		
		TimeFrame timeFrame = new TimeFrame();
		
		long earliestCheckInTime = 0;
		long maxCheckInTime = 0;
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		long battleTimeRangeBeforeMinutesInMilis = localClanSettings.getBattleTimeRangeBeforeMinutesInMilis();
		long battleTimeRangeAfterMinutesInMilis = localClanSettings.getBattleTimeRangeAfterMinutesInMilis();
		
		for (ClanBattle clanBattle : clanBattles){
			
			long battleTime = clanBattle.getTimeAsMilis();
			boolean isEarlierTime = battleTime < earliestCheckInTime || earliestCheckInTime == 0;
			boolean isLaterTime = battleTime > maxCheckInTime || maxCheckInTime == 0;
			
			if(isEarlierTime){
				earliestCheckInTime = battleTime;
				timeFrame.setStartTime(earliestCheckInTime - battleTimeRangeBeforeMinutesInMilis);
			}
			if(isLaterTime){
				maxCheckInTime = battleTime;
				timeFrame.setEndTime(maxCheckInTime + battleTimeRangeAfterMinutesInMilis);
			}
			
		}

		return timeFrame;
		
	}
	
	private TimeFrame getCheckInTimeFrameForEvents(List<Event> events, long clanId){
		
		TimeFrame timeFrame = new TimeFrame();
		
		long earliestCheckInTime = 0;
		long maxCheckInTime = 0;
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		long eventTimeRangeBeforeMinutesInMilis = localClanSettings.getBattleTimeRangeBeforeMinutesInMilis();
		long eventTimeRangeAfterMinutesInMilis = localClanSettings.getBattleTimeRangeAfterMinutesInMilis();
		
		for (Event event : events){
			
			long eventTime = event.getStartTime();
			boolean isEarlierTime = eventTime < earliestCheckInTime || earliestCheckInTime == 0;
			boolean isLaterTime = eventTime > maxCheckInTime || maxCheckInTime == 0;
			
			if(isEarlierTime){
				earliestCheckInTime = eventTime;
				timeFrame.setStartTime(earliestCheckInTime - eventTimeRangeBeforeMinutesInMilis);
			}
			if(isLaterTime){
				maxCheckInTime = eventTime;
				timeFrame.setEndTime(maxCheckInTime + eventTimeRangeAfterMinutesInMilis);
			}
			
		}

		return timeFrame;
		
	}

	public APIRequestDAO getApiRequestDAO(){
		return apiRequestDAO;
	}

	public void setApiRequestDAO(APIRequestDAO apiRequestDAO){
		this.apiRequestDAO = apiRequestDAO;
	}

	@Override
	public TimeFrame getCheckInEventTimeFrame(long clanId) {
		
		TimeFrame timeFrame = new TimeFrame();
		
		long currentTimeMillis = System.currentTimeMillis();
		
		LocalClanSettings localClanSettings = localClanSettingsService.getLocalClanSettings(clanId);
		
		long battleTimeRangeBeforeMinutesInMilis = localClanSettings.getBattleTimeRangeBeforeMinutesInMilis();
		
		long startTime = currentTimeMillis - battleTimeRangeBeforeMinutesInMilis;
		long endTime = currentTimeMillis + (24 * 60 * 60 * 1000); //24 Hours in millis
		
		timeFrame.setStartTime(startTime);
		timeFrame.setEndTime(endTime);
		
		return timeFrame;
	}

	public EventService getEventService() {
		return eventService;
	}

	public void setEventService(EventService eventService) {
		this.eventService = eventService;
	}

	

}
