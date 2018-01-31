package gcg.testproject.activity.selectdate2.date;

import java.io.Serializable;
import java.util.Date;

public class VDate implements Serializable{

	private static final long serialVersionUID = 1L;
	private int _year;
	private int _month;
	private int _day;
	private boolean _isInitialize = false;
	
	public static VDate create(int year,int month,int day){
		if(!DateUtil.isDate(year, month, day)){
			return null;
		}
		VDate date = new VDate();
		boolean flag = date.setDate(year, month, day);
		if(!flag){
			return null;
		}
		return date;
	}
	
	private VDate(){
		
	}
	
	public VDate(Date date){
		super();
		setDate(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
	}
	/**单位/秒 */
	VDate(long time){
		super();
		Date date = new Date(time * 1000);
		setDate(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
	}
	
	/** 单位 /秒*/
	long getTime(){
		return new Date(_year - 1900, _month -1,_day).getTime()/1000;
	}
	/**
	 * 
	 * @param year
	 * @param mouth
	 * @param day
	 * @return if the params is not right for a date,return false
	 */
	private boolean setDate(int year,int mouth,int day){
		if(!checkDate(year, mouth, day)){
			return false;
		}
		_year = year;
		_month = mouth;
		_day = day;
		_isInitialize = true;
		return true;
	}
	
	boolean isInitialize(){
		return _isInitialize;
	}
	
	public int getDay(){
		return _day;
	}
	
	public int getMonth(){
		return _month;
	}
	
	public int getYear(){
		return _year;
	}
	
	public Date getDate(){
		return new Date(_year - 1900, _month - 1, _day);
	}
	
	private boolean checkDate(int year,int month,int day){
		return DateUtil.isDate(year, month, day);
	}
	
	/**
	 * 
	 * @param date
	 * @return 1 when large,-1,when small,0 when equal
	 */
	public int compare(VDate date){
		long time1 = getTime();
		long time2 = date.getTime();
		if(time1 < time2){
			return -1;
		}
		if(time1 == time2){
			return 0;
		}
		return 1;
	}
	
	/** 间隔天数
	 * eg:1号到2号的间隔是1天*/
	public static int getDuration(VDate vDate1,VDate vDate2){
		Date date1 = vDate1.getDate();
		Date date2 = vDate2.getDate();
		return (int)(DateUtil.getDateInternal(date1, date2));
	}
	
}
