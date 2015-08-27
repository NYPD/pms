package services.ntr.pms.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class TimeUtil {

	public static List<Integer> getYearsSincePmsCreation(){
		
		List<Integer> list = new ArrayList<Integer>();
		
		Calendar currentCalendar = new GregorianCalendar();
		
		int currentYear = currentCalendar.get(Calendar.YEAR);
		
		int yearDifferenceSince2014 = currentYear - 2014;
		
		for(int i = 0 ; i < yearDifferenceSince2014 + 1 ; i++){
			list.add(currentYear - i);
		}
		
		return list;
		
	}
	
	/**
	 * 0-11
	 * 
	 * @return
	 */
	public static int getCurrentMonthOfThisYear(){
		
		long currentTimeMillis = System.currentTimeMillis();
		
		Calendar currentClander = new GregorianCalendar();
		
		currentClander.setTimeInMillis(currentTimeMillis);;
		
		int month = currentClander.get(Calendar.MONTH);
		
		return month;
		
	}
	
}