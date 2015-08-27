package services.ntr.pms.service.checkin;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.DefaultCheckInServiceTestConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.exception.AlreadyCheckedInException;
import services.ntr.pms.exception.CheckInNotWithinBattleTimeFrame;
import services.ntr.pms.exception.PlayerIsBannedException;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.information.Event;
import services.ntr.pms.model.information.MembersInfo;
import services.ntr.pms.model.information.MembersInfoClan;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.information.Private;
import services.ntr.pms.model.util.TimeFrame;
import services.ntr.pms.service.checkin.DefaultPlayerCheckInService;
import services.ntr.pms.service.information.EventService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultCheckInServiceTestConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultPlayerCheckInServiceTest
{

	@Autowired
	private DefaultPlayerCheckInService checkInService;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	
	public void shouldCheckPlayerIn(){
		Private personal = new Private();
		
		Player player = new Player();
		player.setAccountId(1001167560);
		player.setPrivate(personal);
		player.setNickname("NYPD");
		player.setAccessToken("62ea598a8bdbe29502b02d1cee8844e5924e4079");
		
		MembersInfo membersInfo = new MembersInfo();
		MembersInfoClan clan = new MembersInfoClan();
		clan.setClanId(1000007315);
		
		membersInfo.setClan(clan);
		
		checkInService.checkIn(player, membersInfo);
		
		Attendance lastCheckIn = checkInService.getLastCheckIn(player);
		int attendanceId = lastCheckIn.getId();
		
		assertThat(attendanceId, is(12));	
	}
	
	@Test
	
	public void shouldgetCheckInEventTimeFrame(){

		long clanId = 1000007315L;
		TimeFrame checkInEventTimeFrame = checkInService.getCheckInEventTimeFrame(clanId);
		
		long startTime = checkInEventTimeFrame.getStartTime();
		long endTime = checkInEventTimeFrame.getEndTime();
		
		long currentTimeMillis = System.currentTimeMillis();
		
		long ballParkEndTime = currentTimeMillis + (24 * 60 * 60 * 1000);
		
		long endTimeDiffrence = endTime - ballParkEndTime;
		
		boolean isCorrectishEndTime = endTimeDiffrence > 0 || endTimeDiffrence < 1000;
		
		long ballParkStartTime = currentTimeMillis - (30 * 60 * 1000);
		
		long startTimeDiffrence = startTime - ballParkStartTime;
		
		boolean isCorrectishStartTime = startTimeDiffrence > 0 || startTimeDiffrence < 1000;
		
		//assertThat(startTime, is(12));
		assertThat(isCorrectishEndTime, is(true));	
		assertThat(isCorrectishStartTime, is(true));	
	}
	
	@Test
	
	public void shouldCheckPlayerInByEvent(){
		
		Private personal = new Private();
		
		Player player = new Player();
		player.setAccountId(1001167560);
		player.setPrivate(personal);
		player.setNickname("NYPD");
		player.setAccessToken("ec7d403bceae094334a7986f9a49d245f4d37395");
		
		MembersInfo membersInfo = new MembersInfo();
		MembersInfoClan clan = new MembersInfoClan();
		clan.setClanId(1000007315);
		
		membersInfo.setClan(clan);
		
		checkInService.checkIn(player, membersInfo);
		
		Attendance lastCheckIn = checkInService.getLastCheckIn(player);
		int attendanceId = lastCheckIn.getId();
		
		assertThat(attendanceId, is(12));	
	}
	
	@Test(expected = AlreadyCheckedInException.class)  
	
	public void shouldNotLetPlayerCheckInAndThrowExceptionBecauseAlreadyCheckedIn()
	{
		Private personal = new Private();
		
		Player player = new Player();
		player.setAccountId(1001167560);
		player.setPrivate(personal);
		player.setNickname("NYPD");
		player.setAccessToken("62ea598a8bdbe29502b02d1cee8844e5924e4079");
		
		MembersInfo membersInfo = new MembersInfo();
		MembersInfoClan clan = new MembersInfoClan();
		clan.setClanId(1000007315);
		
		membersInfo.setClan(clan);
		
		checkInService.checkIn(player, membersInfo);
		checkInService.checkIn(player, membersInfo);
	}
	
	@Test
	
	public void shouldLetPlayerCheckInAndNowThrowExceptionBecauseFirstTimeCheckIn()
	{
		Private personal = new Private();
		
		Player player = new Player();
		player.setAccountId(1111111111);
		player.setPrivate(personal);
		player.setNickname("LAPD");
		player.setAccessToken("62ea598a8bdbe29502b02d1cee8844e5924e4079");
		
		MembersInfo membersInfo = new MembersInfo();
		MembersInfoClan clan = new MembersInfoClan();
		clan.setClanId(1000007315);
		
		membersInfo.setClan(clan);
		
		checkInService.checkIn(player, membersInfo);
	}
	
	
	@Test(expected = PlayerIsBannedException.class)  
	
	public void shouldNotLetPlayerCheckInAndThrowExceptionBecausePlayerIsBanned()
	{
		long banTime = System.currentTimeMillis() + 18000000;
		
		Private personal = new Private();
		personal.setBanTime(banTime);
		
		Player player = new Player();
		player.setAccountId(1111111111);
		player.setPrivate(personal);
		player.setAccessToken("62ea598a8bdbe29502b02d1cee8844e5924e4079");
		
		MembersInfo membersInfo = new MembersInfo();
		MembersInfoClan clan = new MembersInfoClan();
		clan.setClanId(1000007315);
		
		membersInfo.setClan(clan);
		
		checkInService.checkIn(player, membersInfo);
	}
	
	@Test(expected = CheckInNotWithinBattleTimeFrame.class)
	
	public void shouldNotLetPlayerCheckInBecauseHeIsNotWithinTheAllowedTimeframe(){
		
		setEventServiceThatReturnsEmptyEventList();
		
		Private personal = new Private();
		
		Player player = new Player();
		player.setAccountId(1001167560);
		player.setPrivate(personal);
		player.setNickname("NYPD");
		player.setAccessToken("5dc8a468b9ed8319767456733c80cc388a19ba33");
		
		MembersInfo membersInfo = new MembersInfo();
		MembersInfoClan clan = new MembersInfoClan();
		clan.setClanId(1000007315);
		
		membersInfo.setClan(clan);
		
		checkInService.checkIn(player, membersInfo);
	}

	private void setEventServiceThatReturnsEmptyEventList()
	{
		long clanId = 1000007315;
		EventService eventServiceMock = mock(EventService.class);
		List<Event> events = new ArrayList<Event>();
		
		when(eventServiceMock.getEventsByTimeFrame(eq(clanId), any(TimeFrame.class ))).thenReturn(events);
		checkInService.setEventService(eventServiceMock);
	}
	
	@Test
	
	public void shouldGetLastPlayerCheckedIn()
	{
		Player player = new Player();
		player.setAccountId(1001167560);
		
		Attendance lastCheckIn = checkInService.getLastCheckIn(player);
		long checkInTime = lastCheckIn.getCheckInTime();
		
		assertThat(checkInTime, is(1402704900000L));
	}
}
