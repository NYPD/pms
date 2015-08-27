package services.ntr.pms.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {

	private long timeInMilis;

	public DateUtil(long timeInMilis) {
		this.setTimeInMilis(timeInMilis);
	}

	public long getTimeInMilis() {
		return timeInMilis;
	}

	public void setTimeInMilis(long timeInMilis) {
		this.timeInMilis = timeInMilis;
	}
	
	
	public long getStartOfDay(){
		
		Calendar calender = getTimeAsCalender(this.timeInMilis);
	
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		calender.set(Calendar.AM_PM, Calendar.AM);
		
		long startOfDayInMillis = calender.getTimeInMillis();

		return startOfDayInMillis;
	}
	
	public long getEndOfDay(){
		
		Calendar calender = getTimeAsCalender(this.timeInMilis);
	
		calender.set(Calendar.HOUR_OF_DAY, 24);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, -1);
		
		long endOfDayInMillis = calender.getTimeInMillis();

		return endOfDayInMillis;
	}
	
	public boolean isCurrentYear(){
		
		Calendar calender = getTimeAsCalender(this.timeInMilis);
		
		long currentTimeMillis = System.currentTimeMillis();
		
		Calendar currentClander = new GregorianCalendar();
		currentClander.setTimeInMillis(currentTimeMillis);
		
		int selectedYear = calender.get(Calendar.YEAR);
		int currentYear = currentClander.get(Calendar.YEAR);
		
		boolean isCurrentYear = selectedYear == currentYear;
		
		return isCurrentYear;
		
	}
	
	public Calendar getTimeAsCalender(long timeInMilis){
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInMilis);
		
		return calendar;
	}
	

}
