package net.uchoice.travelgift.vote.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

	public static Date todayZeroOclock() {
		String pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(df.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
