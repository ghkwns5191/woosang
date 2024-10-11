package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class Util {
	
	public static String stringUtil(String str) {
		if (str.trim() == "" || str.trim() == null) {
			str = "";
		}
		
		return str.trim();
	}
	
	
	public static String mapToString(Map<String, Object> map, String param) {
		String result = "";
		
		result = (String) map.get(param);
		
		return result;
	}
	
	
	public static Map<String, Object> mapValidate(Map<String, Object> map) {
		
		if (map == null || map.size() == 0) {
			map = new HashMap<>();
		}
		
		return map;
		
	}
}
