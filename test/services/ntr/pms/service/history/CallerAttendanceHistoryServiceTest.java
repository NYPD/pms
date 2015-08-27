package services.ntr.pms.service.history;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.util.TimeFrame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class CallerAttendanceHistoryServiceTest {
	
	@Autowired
	private CallerAttendanceHistoryService callerAttendanceHistoryService;
	
	@Before
	public void setUp() throws Exception
	{}

	@Test
	public void shouldGetCallerAttendanceHistoryByWeek()
	{
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(1402200000000L);//Start of june 8 2014
		timeFrame.setEndTime(1402804799000L);//End of june 14 2014
		
		List<AttendanceHistory> callerAttendanceHistory = callerAttendanceHistoryService.getAttendanceHistory(1000007315, timeFrame);
		
		AttendanceHistory attendanceHistory = callerAttendanceHistory.get(0);
		int numberOfAttendanceHistory = callerAttendanceHistory.size();
		int attendancePoints = attendanceHistory.getPoints();
		
		assertThat(numberOfAttendanceHistory, is(3));
		assertThat(attendancePoints, is(1));
	}
	
}
