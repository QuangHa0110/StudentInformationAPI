package com.manageuniversity.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ConvertString {
	
	public static Date convetString(String s) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<Integer> convertListInteger(String[] list) {
		List<Integer> list2 = new ArrayList<>();
		for (String s : list) {
			list2.add(Integer.parseInt(s));
		}
		return list2;

	}
	
	public static List<Date> convertListDate(String[] list){
		List<Date> list2 = new ArrayList<>();
		for(String s: list) {
			list2.add(convetString(s));
		}
		return list2;
	}
	
	public static List<String> convertListString(String[] list){
		List<String> list2 = new ArrayList<>();
		for(String s: list) {
			list2.add(s);
		}
		return list2;
	}

}
