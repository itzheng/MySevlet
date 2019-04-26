package org.itzheng.utils.db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.itzheng.utils.StrUtils;

public class DbTimeUtils {
	/**
	 * 验证时间字符串格式输入是否正确
	 *
	 * @param timeStr
	 * @return
	 */
	public static boolean valiDateTime(String timeStr) {
		if (StrUtils.isEmpty(timeStr)) {
			return false;
		}
		if (timeStr.length() < 10) {
			return false;
		}

//        LogHelper.d(TAG, "4:" + timeStr.substring(4, 5) + " 7:" + (timeStr.substring(7, 8)));
		if (timeStr.substring(4, 5).equals("-") && (timeStr.substring(7, 8).equals("-"))) {
			return true;
		}
//        String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
//                + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		String format = "((18|19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])*$";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 将日期转换成yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateTime
	 * @return
	 */
	public static String toDateTime(String dateTime) {
//        2017-09-14 09:02.04.980 ===》 2017-09-14 09:02:04.980
		if (StrUtils.isEmpty(dateTime)) {
			return "";
		}
		String space = " ";
		String[] split = dateTime.split(space);
		if (split == null || split.length <= 1) {
			return dateTime;
		}
		String date = split[0];
		String tempTime = split[1];
		String time = "";
		String[] times = tempTime.split("\\:");
		if (times == null || times.length <= 1 || time.length() > 2) {
			time = tempTime;
		} else {
			// ==2()
			String s = times[1];// (02.04.980)
			String[] aa = s.split("\\.");// 把第一个.换成:
			if (aa == null || aa.length <= 2) {
				time = tempTime;
			} else {
				time = times[0] + ":" + aa[0] + ":" + aa[1] + "." + aa[2];
			}
		}
		return date + space + time;
	}
}
