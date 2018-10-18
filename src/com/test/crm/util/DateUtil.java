package com.test.crm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	public static void main(String[] args) {
		long start = new Date().getTime();
		System.out.println(start);
		long end = new Date().getTime();
		if((end-start)/1000 == 4){
			System.out.println((end-start)/1000);
		}
	}
	//该方法和DateUtil2一样
	@SuppressWarnings("unchecked")
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal(){
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		};
	};
	private DateUtil(){};
	
	/**
	 * 当前时间转换为字符串
	 * @return 2018-07-22 11:55:19
	 */
	//非线程安全
	public static String getDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.now().format(formatter);
		
	}
	
	/**
	 * 
	 * @param dateTime 日期字符串
	 * @return Date对象  Sun Jul 22 10:20:20 CST 2018
	 * @throws ParseException
	 */
	public static Date toDate(String dateTime){
		Date date = null;
		try {
			date = threadLocal.get().parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 线程安全
	 * 高并发下使用
	 * @return
	 */
	public static String getDates(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return threadLocal.get().format(new Date());
		
	}
}
