package services.ntr.pms.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.model.checkin.CallerAttendance;
import services.ntr.pms.model.history.AttendanceHistory;
import services.ntr.pms.model.history.CallerMapFrequency;
import services.ntr.pms.model.util.TimeFrame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class})
@Transactional
public class CallerAttendanceDAOTest {
	
	@Configuration
    static class Config {

		@Bean
		public DataSource getPmsDataSource() throws NamingException {	
			
			EmbeddedDatabase datasource = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("setup/create-pms-database.sql")
			.addScript("setup/dao/insert-caller-attendance-data.sql")
			.build();
			
			return datasource;
		}
    }

	@Autowired
	private CallerAttendanceDAO callerAttendanceDAO;
	
	@Before
	public void setUp() throws Exception
	{}
	
	@Test
	public void shouldGetAllCallerAttendanceHistory(){
		
		TimeFrame timeFrame = new TimeFrame();
		timeFrame.setStartTime(18000000);//Start of 1970
		timeFrame.setEndTime(32503698000000L);//End of 3000
		
		List<AttendanceHistory> callerAttendanceHistory = callerAttendanceDAO.getCallerAttendanceHistory(1234567890, timeFrame);
		
		int numberOfCallerAttendances = callerAttendanceHistory.size();
		
		int attendancePoints = callerAttendanceHistory.get(0).getPoints();

		assertThat(numberOfCallerAttendances, is(1));
		assertThat(attendancePoints, is(3));
		
	}
	
	@Test
	public void shouldGetCallerMapFrequencies(){
		
		List<CallerMapFrequency> callerMapFrequencies = callerAttendanceDAO.getCallerMapFrequencies(1234567890);
		
		int size = callerMapFrequencies.size();
		
		CallerMapFrequency callerMapFrequency = callerMapFrequencies.get(2);
		String mapName = callerMapFrequency.getMapName();
		
		assertThat(size, is(3));
		assertThat(mapName, is("Steppes"));
		
	}
	
	@Test
	public void shouldAddCallerAttenedance(){
		
		CallerAttendance callerAttendance = new CallerAttendance();
		
		callerAttendance.setAccountId(1001167561);
		callerAttendance.setClanId(1234567890);
		callerAttendance.setMapName("Sand River");
		
		callerAttendanceDAO.addCallerAttendance(callerAttendance);
		
		int callerAttendanceId = callerAttendance.getId();
		
		assertThat(callerAttendanceId, is(12));
		
	}
	
}
