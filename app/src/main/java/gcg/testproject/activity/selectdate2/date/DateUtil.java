package gcg.testproject.activity.selectdate2.date;

import java.util.Date;


public class DateUtil {

	/**
	 * 
	 * @param year ��0��ʼ
	 * @param month
	 * @param day
	 * @param last
	 * @return
	 */
	public static Date getDateLast(int year,int month,int day,int last){
		Date targetDate = new Date(year - 1900, month - 1, day);
		long addTime =(((long)last) * 24 * 3600 * 1000);
		long currentTime  = targetDate.getTime();
		long lastTime =currentTime + addTime;
		Date date = new Date(lastTime);
		return date;
	}
	
	/**
	 * 
	 * @param year ��0��ʼ
	 * @param month
	 * @param day
	 * @param before
	 * @return
	 */
	public static Date getDateBefore(int year,int month,int day,int before){
		Date targetDate = new Date(year - 1900, month - 1, day);
		long delTime =(((long)before) * 24 * 3600 * 1000);
		long beforeTime = targetDate.getTime() - delTime;
		return new Date(beforeTime);
	}
	
	public static long getDateInternal(Date date1,Date date2){
		long interalTime = Math.abs(date2.getTime() - date1.getTime());
		return interalTime/(1000*3600*24);
	}
	
	public static boolean isDate(int year,int month,int day){
		int maxDay = CalendarUtil.getMonthMaxDay(year,month);
		return day <= maxDay;
	}
	
	public static int getMaxDay(int year,int month){
		return CalendarUtil.getMonthMaxDay(year, month);
	}
}
