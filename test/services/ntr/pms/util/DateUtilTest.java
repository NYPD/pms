package services.ntr.pms.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class DateUtilTest {

	
	@Test
	public void testGetStartOfDay() {

		DateUtil dateUtil = new DateUtil(1405786881000L);
		
		long startOfDay = dateUtil.getStartOfDay();
		
		assertThat(startOfDay, is(1405742400000L));
	}
	
	@Test
	public void testGetEndOfDay() {

		DateUtil dateUtil = new DateUtil(1405791000000L);
		
		long startOfDay = dateUtil.getEndOfDay();
		
		assertThat(startOfDay, is(1405828799999L));
	}
	
	@Test
	public void testIsCurrentYear() {

		DateUtil dateUtil = new DateUtil(System.currentTimeMillis());
		
		boolean isCurrentYear = dateUtil.isCurrentYear();
		
		assertThat(isCurrentYear, is(true));
	}

}
