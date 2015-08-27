package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.checkin.Attendance;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.information.Player;
import services.ntr.pms.model.util.TimeFrame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@ActiveUnitProfile
@Transactional
public class PlayerAttendanceDAOTest {

	@Autowired
	private PlayerAttendanceDAO playerAttendanceDAO;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetLastAttendanceForPlayer() {
		Player player = new Player();
		player.setAccountId(1001167560);
		player.setNickname("NYPD");
		player.setAccessToken("62ea598a8bdbe29502b02d1cee8844e5924e4079");
		
		 Attendance lastAttendanceForPlayer = playerAttendanceDAO.getLastAttendanceForPlayer(player);
		 long lastCheckInTime = lastAttendanceForPlayer.getCheckInTime();
		 
		 assertThat(lastCheckInTime, is(1402704900000L));
	}
	
	@Test
	public void shouldGetAttendanceForPlayer()
	{
		Player player = new Player();
		player.setAccountId(1001167560);
		player.setNickname("NYPD");
		player.setAccessToken("62ea598a8bdbe29502b02d1cee8844e5924e4079");
		
		List<Attendance> attendances = playerAttendanceDAO.getAttendanceForPlayer(player);
		
		int numberOfAttendance = attendances.size();
		assertThat(numberOfAttendance, is(8));
	}
	
	@Test
	public void shouldGetAllAttendances()
	{
		List<Attendance> attendances = playerAttendanceDAO.getAttendances();
		int numberOfAttendance = attendances.size();
		assertThat(numberOfAttendance, is(11));
	}
	@Test
	public void shouldGetClanAttendanceHistoryByMonths()
	{
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);
		
		List<AttendanceHistory> clanAttendanceHistory = playerAttendanceDAO.getClanAttendanceHistory(1000007315, timeFrame);
		
		AttendanceHistory attendanceHistory = clanAttendanceHistory.get(0);
		int numberOfAttendanceHistory = clanAttendanceHistory.size();
		int attendancePoints = attendanceHistory.getPoints();
		
		assertThat(numberOfAttendanceHistory, is(2));
		assertThat(attendancePoints, is(7));
	}
	
	@Test
	public void shouldAddAttendance(){
		
		Attendance attendance = new Attendance();
		
		attendance.setAccountId(1001167560);
		attendance.setClanId(1000007315);
		
		playerAttendanceDAO.addAttendance(attendance);
		
		int id = attendance.getId();
		
		assertThat(id, is(not(0)));
	}
	
	@Test
	public void shouldAddAttendanceWithCheckInTime(){
		
		Attendance attendance = new Attendance();
		
		attendance.setCheckInTime(1388560360000L);
		attendance.setAccountId(1001167560);
		attendance.setClanId(1000007315);
		
		playerAttendanceDAO.addAttendanceWithCheckIn(attendance);
		
		int id = attendance.getId();
		
		assertThat(id, is(not(0)));
	}
	
}
