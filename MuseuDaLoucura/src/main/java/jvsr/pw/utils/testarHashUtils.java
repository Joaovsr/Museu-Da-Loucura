package jvsr.pw.utils;

import java.util.Calendar;

public class testarHashUtils {
	public static void main(String[] args) {
		System.out.println(HashUtils.getHashMd5(String.format("%d", Calendar.getInstance().getTimeInMillis())));
	}
}
