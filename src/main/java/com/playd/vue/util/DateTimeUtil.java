package com.playd.vue.util;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateTimeUtil {
	
	//오늘 날짜
	public static String getToday(String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.now().format(formatter);
	}
	
	//오늘 날짜 +- 일 계산
	public static String getTodayAfterDate(int i, String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.now().plusDays(i).format(formatter);
	}
	
	//오늘 날짜 +- 월 계산
	public static String getTodayAfterMonth(int i, String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.now().plusMonths(i).format(formatter);
	}
	
	//오늘 날짜 +- 년 계산
	public static String getTodayAfterYear(int i, String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.now().plusYears(i).format(formatter);
	}
	
	//특정날짜 날짜 +- 일 계산
	public static String getTodayAfterDate(int year,int month, int dayOfMonth, int hour, int minute, int seconds, int i, String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.of(year, month, dayOfMonth, hour, minute, seconds).plusDays(i).format(formatter);
	}
	
	//특정날짜 +- 월 계산
	public static String getTodayAfterMonth(int year,int month, int dayOfMonth, int hour, int minute, int seconds, int i, String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.of(year, month, dayOfMonth, hour, minute, seconds).plusMonths(i).format(formatter);
	}
	
	//특정날짜 +- 년 계산
	public static String getTodayAfterYear(int year,int month, int dayOfMonth, int hour, int minute, int seconds, int i, String pattern){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.of(year, month, dayOfMonth, hour, minute, seconds).plusYears(i).format(formatter);
	}
	
	//String 날짜 포맷 변경
	public static String getConvertDateFormat(String ymd,String orgPattern,String pattern){
		
		LocalDate date = LocalDate.parse(ymd, DateTimeFormatter.ofPattern(orgPattern));
		
					
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern,Locale.KOREAN);
		
		return date.format(formatter);
	}	

}
