package com.tan.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	/**
	 * 将日期对象格式化为指定格式日期字符串
	 * @param date 待格式化的日期对象
	 * @param format 格式化的日期形式，以字符串的方式表达，如:"yyyy-MM-dd"
	 * @return 返回一个字符串类型对象 result
	 */
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	/**
	 * 将日期字符串转换为日期对象
	 * @param str 待格式化的日期字符串
	 * @param format 格式化的日期形式，以字符串的方式表达，如:"yyyy-MM-dd"
	 * @return 返回一个日期类型对象 date
	 * @throws Exception
	 */
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		Date date=null;
		date=sdf.parse(str);
		return date;
	}
	
	
	
	/**
	 * 第一个时间<第二个时间
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static int differentDays(Calendar cal1,Calendar cal2){
		int day1=cal1.get(Calendar.DAY_OF_YEAR);
		int day2=cal2.get(Calendar.DAY_OF_YEAR);
		
		int year1=cal1.get(Calendar.YEAR);
		int year2=cal2.get(Calendar.YEAR);
		if(year1!=year2){
			int timeDistance=0;
			for(int i=year1;i<year2;i++){
				if(i%4==0&&i%100!=0||i%400==0){
					timeDistance+=366;//闰年
				}else{
					timeDistance+=365;//不是闰年
				}
			}
			return timeDistance+(day2-day1);
		}else{
			System.out.println("判断day2-day1:"+(day2-day1));
			return day2-day1;
		}
	}
}
