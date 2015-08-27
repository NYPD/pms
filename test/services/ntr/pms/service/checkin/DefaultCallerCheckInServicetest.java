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
import services.ntr.pms.model.checkin.CallerAttendance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ApplicationConfiguration.class, EmbeddedDataSourceConfiguration.class})
@Transactional
@ActiveUnitProfile
public class DefaultCallerCheckInServicetest {
	
	@Autowired
	CallerCheckInService callerCheckInService;

	@Test
	public void shouldAddCallerAttenedance(){
		
		CallerAttendance callerAttendance = new CallerAttendance();
		
		callerAttendance.setAccountId(1001167561);
		callerAttendance.setClanId(1234567890);
		callerAttendance.setMapName("Sand River");
		
		callerCheckInService.checkIn(callerAttendance);
		
		int callerAttendanceId = callerAttendance.getId();
		
		assertThat(callerAttendanceId, is(12));
		
	}
}
