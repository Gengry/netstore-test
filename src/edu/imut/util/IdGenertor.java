package edu.imut.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IdGenertor {

	public static String genOrdersNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyymmdd");
		String s = df.format(date);
		return s+System.nanoTime();
	}

}
