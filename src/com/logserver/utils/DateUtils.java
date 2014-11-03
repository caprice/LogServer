package com.logserver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	/**
	 * 
	 * <p>Title: getDate</p>
	 * <p>Description: 获取格式化的日期：yyyy_dd_mm</p>
	 * @return
	 * @author guangshuai.wang
	 */
	public static String getDate(long date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		String tomStamp = format.format(Long
				.valueOf(date));
		return tomStamp;
	}
	/**
	 * 
	 * <p>Title: getTodayZero</p>
	 * <p>Description: 获取零点的毫秒时间</p>
	 * @param day 0表示今天的零点，1表示明天，2表示后天，等
	 * @return
	 * @author guangshuai.wang
	 */
	public static  long getTodayZero(int days){
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_YEAR );
		calendar.set(Calendar.DAY_OF_YEAR, day + days);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
        return  (calendar.getTimeInMillis());
	}
	public static void main(String[] args) {
	
	}
}
