package jvsr.pw.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
	public static String obterDia(Calendar calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(calendar.getTime()); 
	}
}
