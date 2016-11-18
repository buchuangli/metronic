package com.open01.logs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Years;

public class DateUtils {
	/*
	 * int 类型时间转换为String(yyyy-MM-dd hh:mm)
	 */
	public static String timeToString(long t,String flag){
		String str  = "";
		String year = "";
		String month = "";
		String day = "";
		String hour = "";
		String minute = "";
		//年月日时分的转换
		if(flag.equals("1")){
			 str  = String.valueOf(t);
			 year = str.substring(0,4);
			 month = str.substring(4,6);
			 day = str.substring(6,8);
			 hour = str.substring(8,10);
			 minute = str.substring(10,12);
			 return year+"-"+month+"-"+day+" "+hour+":"+minute;
		}
		//年月日时的转换
		else if(flag.equals("2")){
			 str  = String.valueOf(t);
			 year = str.substring(0,4);
			 month = str.substring(4,6);
			 day = str.substring(6,8);
			 hour = str.substring(8,10);
			 return year+"-"+month+"-"+day+" "+hour;
		}
		//年月日的转换
		else if(flag.equals("3")){
			 str  = String.valueOf(t);
			 year = str.substring(0,4);
			 month = str.substring(4,6);
			 day = str.substring(6,8);
			 return year+"-"+month+"-"+day;
		}
		//年月的转换
		else if(flag.equals("4")){
			str  = String.valueOf(t);
			year = str.substring(0,4);
			month = str.substring(4,6);
			return year+"-"+month;
		}
		//年的转换
		else if(flag.equals("5")){
			str  = String.valueOf(t);
			year = str.substring(0,4);
			return year;
		}
		return null;
	}
	
	/*
	 * 获取前一天,前一周,前一月,前三月,前六月,前一年和前一年数据
	 */
	public static HashMap<String,Long> getDay(String time,String flag){
		HashMap<String,Long> dt = new HashMap<String,Long>();
		Date end = new Date();   //当前时间
		Date start = new Date();
		SimpleDateFormat sdf = null;
		//年月日时分的转换
		if(flag.equals("1")){
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}
		//年月日时的转换
		else if(flag.equals("2")){
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}
		//年月日的转换
		else if(flag.equals("3")){
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}
		//年月的转换
		else if(flag.equals("4")){
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}
		//年的转换
		else if(flag.equals("5")){
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(end);//把当前时间赋给日历
	//	calendar.add(Calendar.DAY_OF_YEAR, -1);
		end = calendar.getTime();
		if(time.equals("day")){
			calendar.add(Calendar.DAY_OF_YEAR, -1);  //设置为前一天
		}else if(time.equals("week")){
			calendar.add(Calendar.DAY_OF_YEAR, -7);  //设置为前一周
		}else if(time.equals("oneMonth")){
			calendar.add(Calendar.MONTH, -1);  //设置为前一月
		}else if(time.equals("threeMonth")){
			calendar.add(Calendar.MONTH, -3);  //设置为前三月
		}else if(time.equals("sixMonth")){
			calendar.add(Calendar.MONTH, -6);  //设置为前六月
		}else if(time.equals("year")){
			calendar.add(Calendar.MONTH, -12);  //设置为前一年
		}else if(time.equals("twoYear")){
			calendar.add(Calendar.MONTH, -24);  //设置为前一年
		}
		start = calendar.getTime();   //得到起始时间
		String startDate = sdf.format(start); //格式化起始时间
		String endDate = sdf.format(end); //格式化截止时间
		dt.put("firstT", Long.valueOf(startDate));
		dt.put("nextT",  Long.valueOf(endDate));
		return dt;
	}
	/*
	 * 根据subTime=截止时间-开始时间
	 * 推算
	 * 开始时间-subTime
	 */
	public static long getLastDate(long startTime,long endTime){
		String st ="";
		String et ="";
		SimpleDateFormat sdf0 = null;
		SimpleDateFormat sdf = null; //设置时间格式
		int len = String.valueOf(startTime).length();
		if(len==12){
			st = timeToString(startTime, "1");
			et = timeToString(endTime, "1");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}else if(len==10){
			st = timeToString(startTime, "2");
			et = timeToString(endTime, "2");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd hh");
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}else if(len==8){
			st = timeToString(startTime, "3");
			et = timeToString(endTime, "3");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd");
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}else if(len==6){
			st = timeToString(startTime, "4");
			et = timeToString(endTime, "4");
			sdf0  = new SimpleDateFormat("yyyy-M");
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}else{
			st = timeToString(startTime, "5");
			et = timeToString(endTime, "5");
			sdf0  = new SimpleDateFormat("yyyy");
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf0.parse(st);
			date2 = sdf0.parse(et);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Interval interval = new Interval(date1.getTime(), date2.getTime());
		Period period = interval.toPeriod();
		int year = period.getYears();
		int month = period.getMonths();
		int day = period.getDays()+period.getWeeks()*7;
		int hour = period.getHours();
		int minute = period.getMinutes();
		
		DateTime dt1= new DateTime(date1);
		dt1 = dt1.plusYears(-year).plusMonths(-month).plusDays(-day).plusHours(-hour).plusMinutes(-minute);
		String ld = sdf.format(dt1.toDate());
		return Long.valueOf(ld);
	}
//	public static long getLastDate(long startTime,long endTime) throws Exception{
//		String st ="";
//		String et ="";
//		SimpleDateFormat sdf0 = null;
//		SimpleDateFormat sdf = null; //设置时间格式
//		int len = String.valueOf(startTime).length();
//		if(len==12){
//			st = timeToString(startTime, "1");
//			et = timeToString(endTime, "1");
//			sdf0  = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
//		}else if(len==10){
//			st = timeToString(startTime, "2");
//			et = timeToString(endTime, "2");
//			sdf0  = new SimpleDateFormat("yyyy-MM-dd hh");
//			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
//		}else if(len==8){
//			st = timeToString(startTime, "3");
//			et = timeToString(endTime, "3");
//			sdf0  = new SimpleDateFormat("yyyy-MM-dd");
//			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
//		}else if(len==6){
//			st = timeToString(startTime, "4");
//			et = timeToString(endTime, "4");
//			sdf0  = new SimpleDateFormat("yyyy-M");
//			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
//		}else{
//			st = timeToString(startTime, "5");
//			et = timeToString(endTime, "5");
//			sdf0  = new SimpleDateFormat("yyyy");
//			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
//		}
//		//sdf0  = new SimpleDateFormat("yyyy-MM-dd");
//		Date d1 = sdf0.parse(st);
//		Date d2 = sdf0.parse(et);
//		
//		long between=(d2.getTime()-d1.getTime())/1000;
//		
//		//long month=between/(24*3600);
//		long day=between/(24*3600);
//		long hour=between%(24*3600)/3600;
//		long minute=between%3600/60;
//		
//		Calendar calendar = Calendar.getInstance(); //得到日历
//		calendar.setTime(d1);//把当前时间赋给日历
//		calendar.add(Calendar.MINUTE, (int) -minute);  //设置
//		calendar.add(Calendar.HOUR, (int) -hour);  //设置
//		if(day%365 == 0){
//			calendar.add(Calendar.DAY_OF_YEAR, (int) -day);  //设置为前一天
//		}else{
//			calendar.add(Calendar.DAY_OF_YEAR, (int) (-day+(day%365)));  //设置为前一天
//		}
//		d1=calendar.getTime();
//		String ld = sdf.format(d1);
//		return Long.valueOf(ld);
//	}
	
	
	
	/*
	 * 获取前一天,前一周,前一月,前三月,前六月,前一年和前两年数据
	 */
	public static HashMap<String,Long> getDay(String time,String flag,String str){
		HashMap<String,Long> dt = new HashMap<String,Long>();
		str=str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
		Date date =null;
		try {
			 date = sdfs.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date end = date;   //当前时间
		Date start = date;
		SimpleDateFormat sdf = null;
		//年月日时分的转换
		if(flag.equals("1")){
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}
		//年月日时的转换
		else if(flag.equals("2")){
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}
		//年月日的转换
		else if(flag.equals("3")){
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}
		//年月的转换
		else if(flag.equals("4")){
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}
		//年的转换
		else if(flag.equals("5")){
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(end);//把当前时间赋给日历
		//calendar.add(Calendar.DAY_OF_YEAR, -1);
		end = calendar.getTime();
		if(time.equals("day")){
			calendar.add(Calendar.DAY_OF_YEAR, -1);  //设置为前一天
		}else if(time.equals("week")){
			calendar.add(Calendar.DAY_OF_YEAR, -7);  //设置为前一周
		}else if(time.equals("oneMonth")){
			calendar.add(Calendar.MONTH, -1);  //设置为前一月
		}else if(time.equals("threeMonth")){
			calendar.add(Calendar.MONTH, -3);  //设置为前三月
		}else if(time.equals("sixMonth")){
			calendar.add(Calendar.MONTH, -6);  //设置为前六月
		}else if(time.equals("year")){
			calendar.add(Calendar.YEAR, -1);  //设置为前一年
		}else if(time.equals("twoYear")){
			calendar.add(Calendar.YEAR, -2);  //设置为前一年
		}
		start = calendar.getTime();   //得到起始时间
		String startDate = sdf.format(start); //格式化起始时间
		String endDate = sdf.format(end); //格式化截止时间
		dt.put("firstT", Long.valueOf(startDate));
		dt.put("nextT",  Long.valueOf(endDate));
		return dt;
	}
	
	/*
	 * 根据截止时间-开始时间
	 * 推算
	 * 开始时间-和截止时间内所有的时间
	 */
	public static List getBetweenDate(long startTime,long endTime){
		String st ="";
		String et ="";
		SimpleDateFormat sdf0 = null;
		SimpleDateFormat sdf = null; //设置时间格式
		int len = String.valueOf(startTime).length();
		if(len==12){
			st = timeToString(startTime, "1");
			et = timeToString(endTime, "1");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}else if(len==10){
			st = timeToString(startTime, "2");
			et = timeToString(endTime, "2");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH");
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}else if(len==8){
			st = timeToString(startTime, "3");
			et = timeToString(endTime, "3");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd");
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}else if(len==6){
			st = timeToString(startTime, "4");
			et = timeToString(endTime, "4");
			sdf0  = new SimpleDateFormat("yyyy-MM");
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}else{
			st = timeToString(startTime, "5");
			et = timeToString(endTime, "5");
			sdf0  = new SimpleDateFormat("yyyy");
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf0.parse(st);
			date2 = sdf0.parse(et);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		Interval interval = new Interval(date1.getTime(), date2.getTime());
		Period period = interval.toPeriod();
		int year = period.getYears();
 		int month = period.getMonths();
		int day = period.getDays()+period.getWeeks()*7;
		int hour = period.getHours();
		int minute = period.getMinutes();
		System.out.print("相差:"+year+"年"+"\t"+month+"月\t"+day+"日\t"+hour+"小时\t"+minute+"分钟\t");
		
		DateTime dt1= new DateTime(date1);
		DateTime dt2= new DateTime(date2);
		
		Long t1 = Long.valueOf(sdf.format(dt1.toDate()));
		Long t2 = Long.valueOf(sdf.format(dt2.toDate()));
		
		List list = new ArrayList();
		
		if(year >1 ||(year == 1 &&(month !=0 || hour !=0 || day !=0 || minute !=0))) {
			//年
			
			String t3 = String.valueOf(t1).substring(0,4);
			String t4 = String.valueOf(t2).substring(0,4);
			t1=Long.valueOf(t3);
			t2=Long.valueOf(t4);
			while(t1<t2){
				//System.out.println(t1);
				list.add(t1);
				dt1= dt1.plusYears(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyy"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}
		else if(year == 1 &&(month ==0 || hour ==0 || day ==0 || minute ==0)){
			//月
			String t3 = String.valueOf(t1).substring(0,6);
			String t4 = String.valueOf(t2).substring(0,6);
			t1=Long.valueOf(t3);
			t2=Long.valueOf(t4);
			while(t1<t2){
				list.add(t1);
				dt1= dt1.plusMonths(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMM"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}
		else if(year == 0){
			if(month >1 || (month == 1 &&(hour !=0 || day !=0 || minute !=0))){
				//月
				String t3 = String.valueOf(t1).substring(0,6);
				String t4 = String.valueOf(t2).substring(0,6);
				t1=Long.valueOf(t3);
				t2=Long.valueOf(t4);
				while(t1<t2){
					list.add(t1);
					dt1= dt1.plusMonths(1);
					SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMM"); //设置时间格式
					t1= Long.valueOf(sdf2.format(dt1.toDate()));
				}
			}
			else if(month ==1 &&(hour ==0 || day ==0 || minute ==0)){
				//天
				String t3 = String.valueOf(t1).substring(0,8);
				String t4 = String.valueOf(t2).substring(0,8);
				t1=Long.valueOf(t3);
				t2=Long.valueOf(t4);
				while(t1<t2){
					list.add(t1);
					dt1= dt1.plusDays(1);
					SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
					t1= Long.valueOf(sdf2.format(dt1.toDate()));
				}
			}
			else if(month == 0){
				if(day >1 ||(day ==1 &&(hour !=0 || minute !=0))){
					//天
					String t3 = String.valueOf(t1).substring(0,8);
					String t4 = String.valueOf(t2).substring(0,8);
					t1=Long.valueOf(t3);
					t2=Long.valueOf(t4);
					while(t1<t2){
						list.add(t1);
						dt1= dt1.plusDays(1);
						SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
						t1= Long.valueOf(sdf2.format(dt1.toDate()));
					}
				}
				else if(day == 1 &&(hour ==0 || minute ==0)){
					//小时
					String t3 = String.valueOf(t1).substring(0,10);
					String t4 = String.valueOf(t2).substring(0,10);
					t1=Long.valueOf(t3);
					t2=Long.valueOf(t4);
					while(t1<t2){
						list.add(t1);
						dt1= dt1.plusHours(1);
						SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
						t1= Long.valueOf(sdf2.format(dt1.toDate()));
					}
				}
				else if(day == 0){
					if(hour >1 ||(hour ==1 &&minute !=0)){
						//小时
						String t3 = String.valueOf(t1).substring(0,10);
						String t4 = String.valueOf(t2).substring(0,10);
						t1=Long.valueOf(t3);
						t2=Long.valueOf(t4);
						while(t1<t2){
							list.add(t1);
							dt1= dt1.plusHours(1);
							SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
							t1= Long.valueOf(sdf2.format(dt1.toDate()));
						}
					}
					else{
						//分钟
						String t3 = String.valueOf(t1).substring(0,12);
						String t4 = String.valueOf(t2).substring(0,12);
						t1=Long.valueOf(t3);
						t2=Long.valueOf(t4);
						while(t1<t2){
							list.add(t1);
							dt1= dt1.plusMinutes(1);
							SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
							t1= Long.valueOf(sdf2.format(dt1.toDate()));
						}
					}
				}
			}
			
		}
		return list;
	}
	/*
	 * 根据截止时间-开始时间
	 * 推算
	 * 开始时间-和截止时间内所有的时间
	 */
	public static List getBetweenDate(long startTime,long endTime,String timeType){
		String st ="";
		String et ="";
		SimpleDateFormat sdf0 = null;
		SimpleDateFormat sdf = null; //设置时间格式
		if(timeType.equals("year")){
			st = timeToString(startTime, "5");
			et = timeToString(endTime, "5");
			sdf0  = new SimpleDateFormat("yyyy");
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}else if(timeType.equals("month")){
			st = timeToString(startTime, "4");
			et = timeToString(endTime, "4");
			sdf0  = new SimpleDateFormat("yyyy-MM");
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}else if(timeType.equals("daily")){
			st = timeToString(startTime, "3");
			et = timeToString(endTime, "3");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd");
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}else if(timeType.equals("hour")){
			st = timeToString(startTime, "2");
			et = timeToString(endTime, "2");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH");
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}else if(timeType.equals("minute")){
			st = timeToString(startTime, "1");
			et = timeToString(endTime, "1");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf0.parse(st);
			date2 = sdf0.parse(et);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		DateTime dt1= new DateTime(date1);
		DateTime dt2= new DateTime(date2);
		
		Long t1 = Long.valueOf(sdf.format(dt1.toDate()));
		Long t2 = Long.valueOf(sdf.format(dt2.toDate()));
	
		
		List list = new ArrayList();
		
		if(timeType.equals("year")){
			String t3 = String.valueOf(t1).substring(0,4);
			String t4 = String.valueOf(t2).substring(0,4);
			while(t1<t2){
				//System.out.println(t1);
				list.add(t1);
				dt1= dt1.plusYears(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyy"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}else if(timeType.equals("month")){
			while(t1<t2){
				//System.out.println(t1);
				list.add(t1);
				dt1= dt1.plusMonths(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMM"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}else if(timeType.equals("daily")){
			while(t1<t2){
				//System.out.println(t1);
				list.add(t1);
				dt1= dt1.plusDays(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}else if(timeType.equals("hour")){
			while(t1<t2){
				//System.out.println(t1);
				list.add(t1);
				dt1= dt1.plusHours(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}else if(timeType.equals("minute")){
			while(t1<t2){
				//System.out.println(t1);
				list.add(t1);
				dt1= dt1.plusMinutes(1);
				SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
				t1= Long.valueOf(sdf2.format(dt1.toDate()));
			}
		}
		return list;
	}
	/*
	 * 获取下一天,下一周,下一月,下三月,下六月,下一年和下两年数据
	 */
	public static HashMap<String,Long> getNextTime(String time,String flag,String str){
		HashMap<String,Long> dt = new HashMap<String,Long>();
		str=str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8);
		SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
		Date date =null;
		try {
			 date = sdfs.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date end = date;   
		Date start = date;//当前时间
		SimpleDateFormat sdf = null;
		//年月日时分的转换
		if(flag.equals("1")){
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}
		//年月日时的转换
		else if(flag.equals("2")){
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}
		//年月日的转换
		else if(flag.equals("3")){
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}
		//年月的转换
		else if(flag.equals("4")){
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}
		//年的转换
		else if(flag.equals("5")){
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(end);//把当前时间赋给日历
		end = calendar.getTime();
		if(time.equals("day")){
			calendar.add(Calendar.DAY_OF_YEAR, 1);  //设置为后一天
		}else if(time.equals("week")){
			calendar.add(Calendar.DAY_OF_YEAR, 7);  //设置为后一周
		}else if(time.equals("oneMonth")){
			calendar.add(Calendar.MONTH, 1);  //设置下一月
		}else if(time.equals("threeMonth")){
			calendar.add(Calendar.MONTH, 3);  //设置为下三月
		}else if(time.equals("sixMonth")){
			calendar.add(Calendar.MONTH, 6);  //设置为下六月
		}else if(time.equals("year")){
			calendar.add(Calendar.YEAR, 1);  //设置为下一年
		}else if(time.equals("twoYear")){
			calendar.add(Calendar.YEAR, 2);  //设置为下两年
		}
		end = calendar.getTime();   //得到截至时间
		String startDate = sdf.format(start); //格式化起始时间
		String endDate = sdf.format(end); //格式化截止时间
		dt.put("firstT", Long.valueOf(startDate));
		dt.put("nextT",  Long.valueOf(endDate));
		return dt;
	}
	/*
	 * 根据截止时间-开始时间
	 * 推算
	 * 相差几分钟，或小时,或天，或月,或年
	 */
	public static int substractNum(long startTime,long endTime){
		String st ="";
		String et ="";
		SimpleDateFormat sdf0 = null;
		SimpleDateFormat sdf = null; //设置时间格式
		int len = String.valueOf(startTime).length();
		if(len==12){
			st = timeToString(startTime, "1");
			et = timeToString(endTime, "1");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}else if(len==10){
			st = timeToString(startTime, "2");
			et = timeToString(endTime, "2");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH");
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}else if(len==8){
			st = timeToString(startTime, "3");
			et = timeToString(endTime, "3");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd");
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}else if(len==6){
			st = timeToString(startTime, "4");
			et = timeToString(endTime, "4");
			sdf0  = new SimpleDateFormat("yyyy-MM");
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}else{
			st = timeToString(startTime, "5");
			et = timeToString(endTime, "5");
			sdf0  = new SimpleDateFormat("yyyy");
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf0.parse(st);
			date2 = sdf0.parse(et);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DateTime dt1 = new DateTime(date1);
  		DateTime dt2 = new DateTime(date2);
  		int year= Years.yearsBetween(dt1, dt2).getYears();
  		int month= Months.monthsBetween(dt1, dt2).getMonths();
  		int day= Days.daysBetween(dt1, dt2).getDays();
  		int hour = Hours.hoursBetween(dt1, dt2).getHours();
  		int minute = Minutes.minutesBetween(dt1, dt2).getMinutes() ;
		
		if(len==12){
			return minute;
		}else if(len==10){
			return hour;
		}else if(len==8){
			return day;
		}else if(len==6){
			return month;
		}else{
			return year;
		}
	}
	public static List getInitDate(String flag){
		
		SimpleDateFormat sdf = null;
		
		if(flag.equals("day")){
			sdf = new SimpleDateFormat("yyyyMMdd");
		}else if(flag.equals("hour")){
			sdf = new SimpleDateFormat("yyyyMMddHH");
		}else if(flag.equals("minute")){
			sdf = new SimpleDateFormat("yyyyMMddHHmm");
		}else if(flag.equals("month")){
			sdf = new SimpleDateFormat("yyyyMM");
		}else if(flag.equals("year")){
			sdf = new SimpleDateFormat("yyyy");
		}
		
		DateTime dt = new DateTime();  
		  
		//昨天  
		DateTime yesterday = dt.minusDays(1);  
		long endTime=Long.valueOf(sdf.format(dt.toDate()));
		long startTime=Long.valueOf(sdf.format(yesterday.toDate()));
		
		List list = new ArrayList();
		list.add(startTime);
		list.add(endTime);
		return list;
	}
	
	/*
	 * 根据截止时间-开始时间
	 * 推算
	 * 选择哪张表
	 */
	public static String getDateType(long startTime,long endTime){
		String st ="";
		String et ="";
		SimpleDateFormat sdf0 = null;
		SimpleDateFormat sdf = null; //设置时间格式
		int len = String.valueOf(startTime).length();
		if(len==12){
			st = timeToString(startTime, "1");
			et = timeToString(endTime, "1");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			sdf=new SimpleDateFormat("yyyyMMddHHmm"); //设置时间格式
		}else if(len==10){
			st = timeToString(startTime, "2");
			et = timeToString(endTime, "2");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd HH");
			sdf=new SimpleDateFormat("yyyyMMddHH"); //设置时间格式
		}else if(len==8){
			st = timeToString(startTime, "3");
			et = timeToString(endTime, "3");
			sdf0  = new SimpleDateFormat("yyyy-MM-dd");
			sdf=new SimpleDateFormat("yyyyMMdd"); //设置时间格式
		}else if(len==6){
			st = timeToString(startTime, "4");
			et = timeToString(endTime, "4");
			sdf0  = new SimpleDateFormat("yyyy-MM");
			sdf=new SimpleDateFormat("yyyyMM"); //设置时间格式
		}else{
			st = timeToString(startTime, "5");
			et = timeToString(endTime, "5");
			sdf0  = new SimpleDateFormat("yyyy");
			sdf=new SimpleDateFormat("yyyy"); //设置时间格式
		}
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf0.parse(st);
			date2 = sdf0.parse(et);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		Interval interval = new Interval(date1.getTime(), date2.getTime());
		Period period = interval.toPeriod();
		int year = period.getYears();
 		int month = period.getMonths();
		int day = period.getDays()+period.getWeeks()*7;
		int hour = period.getHours();
		int minute = period.getMinutes();
		System.out.print("年月日相差:"+year+"年"+"\t"+month+"月\t"+day+"日\t"+hour+"小时\t"+minute+"分钟\t");
		
		DateTime dt1= new DateTime(date1);
		DateTime dt2= new DateTime(date2);
		
		Long t1 = Long.valueOf(sdf.format(dt1.toDate()));
		Long t2 = Long.valueOf(sdf.format(dt2.toDate()));
		
		List list = new ArrayList();
		
		if(year >1 ||(year == 1 &&(month !=0 || hour !=0 || day !=0 || minute !=0))) {
			//年
			return "year";
		}
		else if(year == 1 &&(month ==0 || hour ==0 || day ==0 || minute ==0)){
			return "month";
		}
		else if(year == 0){
			if(month >1 || (month == 1 &&(hour !=0 || day !=0 || minute !=0))){
				//月
				return "month";
			}
			else if(month ==1 &&(hour ==0 || day ==0 || minute ==0)){
				//天
				return "daily";
			}
			else if(month == 0){
				if(day >1 ||(day ==1 &&(hour !=0 || minute !=0))){
					//天
					return "daily";
				}
				else if(day == 1 &&(hour ==0 || minute ==0)){
					//小时
					return "hour";
				}
				else if(day == 0){
					if(hour >1 ||(hour ==1 &&minute !=0)){
						//小时
						return "hour";
					}
					else{
						//分钟
						return "minute";
					}
				}
			}
			
		}
		return "other";
	}
	/*public static void main(String[] args) {
		List timeList = getBetweenDate(20160930, 20161031,"daily");
		System.out.println(timeList);
		
	}*/
}
