package com.open01.logs.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GTMDateUtil {
	/**
	* GTM转本地时间
	*
	* @param GTMDate
	* @return
	*/
	public String GTMToLocal(String GTMDate) {
	int tIndex = GTMDate.indexOf("T");
	String dateTemp = GTMDate.substring(0, tIndex);
	String timeTemp = GTMDate.substring(tIndex + 1, GTMDate.length() - 6);
	String convertString = dateTemp + " " + timeTemp;

	SimpleDateFormat format;
	format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	Date result_date;
	long result_time = 0;

	if (null == GTMDate) {
	return GTMDate;
	} else {
	try {
	format.setTimeZone(TimeZone.getTimeZone("GMT08:00"));
	result_date = format.parse(convertString);
	result_time = result_date.getTime();
	format.setTimeZone(TimeZone.getDefault());
	return format.format(result_time);
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	return GTMDate;
	}

	/***
	* 转成格林威治时间 感觉用不到
	*/
	public String LocalToGTM(String LocalDate) {
	SimpleDateFormat format;
	format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	Date result_date;
	long result_time = 0;
	if (null == LocalDate) {
	return LocalDate;
	} else {
	try {
	format.setTimeZone(TimeZone.getDefault());
	result_date = format.parse(LocalDate);
	result_time = result_date.getTime();
	format.setTimeZone(TimeZone.getTimeZone("GMT00:00"));
	return format.format(result_time);
	} catch (Exception e) {
	e.printStackTrace();
	}
	}
	return LocalDate;
	}
}
