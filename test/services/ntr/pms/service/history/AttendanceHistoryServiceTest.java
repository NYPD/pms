package services.ntr.pms.service.history;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
import services.ntr.pms.configuration.DefaultAPIRequestDAOConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.util.TimeFrame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, DefaultAPIRequestDAOConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class AttendanceHistoryServiceTest
{
	
	@Autowired
	private PlayerAttendanceHistoryService playerAttendanceService;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetClanAttendanceHistoryByMonths()
	{
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402194600000L);
		timeFrame.setEndTime(1402704900000L);
		
		List<AttendanceHistory> clanAttendanceHistory = playerAttendanceService.getAttendanceHistory(1000007315L, timeFrame);
		
		AttendanceHistory attendanceHistory = clanAttendanceHistory.get(0);
		int numberOfAttendanceHistory = clanAttendanceHistory.size();
		int attendancePoints = attendanceHistory.getPoints();
		
		assertThat(numberOfAttendanceHistory, is(2));
		assertThat(attendancePoints, is(7));
	}
	
	

}
