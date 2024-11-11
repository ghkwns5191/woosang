package com.example.demo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

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
	
	public static Map<String, Object> convertKeysToLowerCase(Map<String, Object> originalMap) {

        Map<String, Object> lowerCaseMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
                lowerCaseMap.put(entry.getKey().toLowerCase(), entry.getValue());
            }
        
        return lowerCaseMap; // 실제 View 이름으로 대체
    }

 

    public static List<Map<String, Object>> convertKeysToLowerCase(List<Map<String, Object>> originalList) {

        if (originalList.size() > 0) {
            for (int i = 0; i < originalList.size(); i++) {
            	Map<String, Object> data = originalList.get(i);
                data = convertKeysToLowerCase(data);
                originalList.set(i, data);
            }
            return originalList;
        } else {

            return new ArrayList<>();

        }
    }

 

    public static void setSessionAttribute(HttpSession session, String key, Object obj) {

        session.setAttribute(key, obj);

    }

 

    public static String getSessionString(HttpSession session, String key) {

        return (String) session.getAttribute(key);

    }

 

    public static Map<String, Object> getSessionMap(HttpSession session, String key) {

        return (Map<String, Object>) session.getAttribute(key);

    }

 

    public static List<Map<String, Object>> getSessionListMap(HttpSession session, String key) {

        return (List<Map<String, Object>>) session.getAttribute(key);

    }

 

    public static boolean getLoginInfo(HttpSession session) {

        if (session.getAttribute("usr_id") != null || (String)session.getAttribute("usr_id") != "") {

            return true;

        } else {

            return false;

        }

    }


}
