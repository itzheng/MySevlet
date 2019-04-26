//package org.itzheng.utils;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//
///**
// * 时间转换工具<br/>
// * 数据库统一使用UTC时间，<br/>
// * 客户端访问时，加入时区参数 获取到UTC时间后，本地先处理，将UTC时间转成客户端时区的时间
// * 
// * @author WL001
// *
// */
//public class TimeUtils {
//	public static String getCurrentTimeZone() {
//		if (true) {
//			String utcTime = "2019-01-23 00:00.00.000";
//			String time = formatStrUTCToDateStr(utcTime);
//			System.out.println("utcTime 转换前：" + utcTime);
//			System.out.println("utcTime 转换后 time ：" + time);
//
//			return "";
//		}
//		TimeZone currentTimeZome = TimeZone.getDefault();
//		System.out.println("currentTimeZome:" + currentTimeZome);
//		long currentTimeMillis = System.currentTimeMillis();
//		Calendar cal = Calendar.getInstance();
//		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
//		cal.setTimeZone(timeZone);
//		long utcTimeMillis = cal.getTimeInMillis();
//		System.out.println("currentTimeMillis:" + currentTimeMillis);
//		System.out.println("utcTimeMillis:" + utcTimeMillis);
//		System.out.println("zoneOffset:" + (currentTimeMillis - utcTimeMillis));
//		System.out.println("currentTimeZome:" + currentTimeZome);
//		return "";
//	}
//
//	public static String UTC2LocalTime() {
//		String[] aa = TimeZone.getDefault().getAvailableIDs();
//		for (String tz : aa) {
//			System.out.println("time zone:" + tz);
//		}
//		return null;
//
//	}
//
//	/**
//	 * utc 时间格式转换正常格式 2018-08-07T03:41:59Z
//	 * 
//	 * @param utcTime 时间
//	 * @return
//	 */
//	public static String formatStrUTCToDateStr(String utcTime) {
////		2019-01-23 00:00.00.000
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm.ss.SSS");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm.ss.SSS",Locale.CHINA);
//		TimeZone utcZone = TimeZone.getTimeZone("UTC");
//		sf.setTimeZone(utcZone);
//		Date date = null;
//		String dateTime = "";
//		try {
//			date = sf.parse(utcTime);
//			dateTime = sdf.format(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return dateTime;
//	}
//}
