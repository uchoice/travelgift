package net.uchoice.travelgift.wechart.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final String DEFAULTDATEPATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEPATTERN_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATEPATTERN_YYYY_MM_DD_HH = "yyyy-MM-dd HH";

	private static final String SEPARATOR = "~";

	public static Date parse(String string, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(string);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToStringFormat(Date date, String dateFormat) {
		if (date == null)
			return "";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	public static String dateFormat(Date date) {
		return dateToStringFormat(date, DEFAULTDATEPATTERN);
	}

	public static String dateToDayFormat(Date date) {
		return dateToStringFormat(date, DATEPATTERN_YYYY_MM_DD);
	}

	public static String getTimeSlot(Date beginDate, Date endDate) {

		return dateFormat(beginDate) + SEPARATOR + dateFormat(endDate);
	}

	/**
	 * 时分秒格式化为日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static Date dateFormatDate(Date date) {

		if (date == null)
			return null;
		String dateStr = dateToStringFormat(date, DATEPATTERN_YYYY_MM_DD);
		return parse(dateStr, DATEPATTERN_YYYY_MM_DD);
	}

	/**
	 * 时间戳转换成具体时间
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timeStamp2Date(long timestamp) {

		SimpleDateFormat format = new SimpleDateFormat(DEFAULTDATEPATTERN);
		return format.format(new Date(timestamp * 1000L));
	}

	/**
	 * calendar 设为0点
	 *
	 * @param calendar
	 */
	public static void setZero(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * calendar 设为23:59:59
	 *
	 * @param calendar
	 */
	public static void set235959(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * 计算传入当前时间与间隔分钟的时间
	 * 
	 * @param minutes
	 * @return
	 */
	public static Date minusDate(int minutes, Date processDate) {

		if (processDate == null) {
			processDate = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(processDate);

		int minute = cal.get(Calendar.MINUTE);// 分
		cal.set(Calendar.MINUTE, minute - minutes);

		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 校验销售计划时涉及到的开始结束时间 校验规则
	 * <p>
	 * 1、开始日期 > 当前日期 2、开始时间 < 当前时间 3、开始时间与结束时间不能跨天
	 * </p>
	 * 
	 * @param beginDate
	 *            yyyy-MM-dd HH:mm:ss
	 * @param endDate
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static boolean checkSalePlanDate(Date beginDate, Date endDate) {

		if (beginDate == null || endDate == null)
			return false;
		if (beginDate.after(endDate))
			return false;

		Date now = dateFormatDate(new Date());
		Date bd = dateFormatDate(beginDate);
		if (now.after(bd))
			return false;

		Date ed = dateFormatDate(endDate);
		if (!bd.equals(ed))
			return false;
		return true;
	}

	public static Date getNextDateTime() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
		return calendar.getTime();
	}

	/**
	 * 获取当天起始time
	 * 
	 * @return
	 */
	public static Date getYesterdayTimeEnd() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);// 让日期-1
		set235959(calendar);
		return calendar.getTime();
	}

	/**
	 * 获取当天起始time
	 * 
	 * @return
	 */
	public static Date getCurrentDateTimeBegin() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		setZero(calendar);
		return calendar.getTime();
	}

	// 获取次日起始time
	public static Date getNextDateTimeBegin() {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);// 让日期加1
		setZero(calendar);
		return calendar.getTime();
	}

	/**
	 * 与当前时间的天数间隔
	 * 
	 * @param beginDate
	 * @return
	 */
	public static long getIntervalDay(Date beginDate) {

		Date now = dateFormatDate(new Date());
		if (beginDate == null)
			return -1;
		Date bd = dateFormatDate(beginDate);

		return (bd.getTime() - now.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 判断两个 Date 对象是否在同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 检查当前时间是否在指定时间段内
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static boolean checkExpired(Date beginDate, Date endDate) {

		if (beginDate == null || endDate == null)
			return false;
		Date now = new Date();
		return now.after(beginDate) && now.before(endDate);
	}

	public static String dateJoinDate(Date beginTime, Date endTime) {
		return dateFormat(beginTime) + SEPARATOR + DateUtils.dateFormat(endTime);
	}

	public static void main(String[] args) throws ParseException {

		Date begin = DateUtils.parse("2016-12-14 00:00:00", DEFAULTDATEPATTERN);
		Date end = DateUtils.parse("2016-12-14", DATEPATTERN_YYYY_MM_DD);

		System.out.println(checkSalePlanDate(begin, end));
		System.out.println(dateFormat(getYesterdayTimeEnd()));
		System.out.println(dateFormat(getNextDateTimeBegin()));

		System.out.println(begin.after(getYesterdayTimeEnd()));

		System.out.println(getIntervalDay(begin));

	}
}
