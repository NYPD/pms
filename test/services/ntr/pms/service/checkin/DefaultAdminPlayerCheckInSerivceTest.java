package services.ntr.pms.service.checkin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ntr.pms.annotation.ActiveUnitProfile;
import services.ntr.pms.configuration.ApplicationConfiguration;
import services.ntr.pms.configuration.EmbeddedDataSourceConfiguration;
import services.ntr.pms.exception.PlayerHasAlreadyBeenCheckedInException;
import services.ntr.pms.model.checkin.Attendance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultAdminPlayerCheckInSerivceTest {
	
	@Autowired
	AdminPlayerCheckInSerivce adminPlayerCheckInSerivce;

	@Test
	public void shouldCheckInPlayerManually(){
		
		Attendance attendance = new Attendance();
		
		attendance.setAccountId(1001167560);
		attendance.setCheckInTime( 1402796320000L);//9:38 PM EST 6/14/2014
		attendance.setClanId(1000007315);
		
		adminPlayerCheckInSerivce.manualCheckIn(attendance);
		
		int attendanceId = attendance.getAttendanceId();
		
		assertThat(attendanceId, is(12));	
		
	}
	
	@Test(expected = PlayerHasAlreadyBeenCheckedInException.class)
	public void shouldNotLetAdminCheckInPlayerManually(){
		
		Attendance attendance = new Attendance();
		
		attendance.setAccountId(1001167560);
		attendance.setCheckInTime(1402709920000L);//9:38 PM EST 6/13/2014
		attendance.setClanId(1000007315);
		
		adminPlayerCheckInSerivce.manualCheckIn(attendance);
		
	}

}
