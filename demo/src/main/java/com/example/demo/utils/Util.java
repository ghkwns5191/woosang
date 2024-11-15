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


    public static String objectToString(Object obj) {
        String result = "";
        if (obj != null) {
            result = (String) obj;
            result = result.trim();
        }
        return result;
    }

    public static int objectToInteger(Object obj) {
        int result = 0;
        if (obj != null) {
            result = Integer.parseInt(objectToString(obj));
        }
        return result;
    }

    public static double objectToDouble(Object obj) {
        double result = 0;
        if (obj != null) {
            result = Double.parseInt(objectToString(obj));
        }
        return result;
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

        if (session.getAttribute(key) != null) {
            return (String) session.getAttribute(key);
        } else {
            return null;
        }
        

    }

 

    public static Map<String, Object> getSessionMap(HttpSession session, String key) {

        if (session.getAttribute(key) != null) {
            return (Map<String, Object>) session.getAttribute(key);
        } else {
            return null;
        }
        

    }

 

    public static List<Map<String, Object>> getSessionListMap(HttpSession session, String key) {
        
        if (session.getAttribute(key) != null) {
            return (List<Map<String, Object>>) session.getAttribute(key);
        } else {
            return null;
        }

    }

 

    public static boolean getLoginInfo(HttpSession session) {

        if (session.getAttribute("usr_id") != null || (String)session.getAttribute("usr_id") != "") {

            return true;

        } else {

            return false;

        }

    }



    public static boolean isNullOrEmpty(String str) {
        boolean result = false;
        if (str.equals("") || str == null) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }


    // csrf 검증
    public static boolean checkCsrf(HttpSession session, Map<String, Object> param) {
        boolean result = false;
        param = convertKeysToLowerCase(param);
        String csrf_token = mapToString(param, "csrf_token");
        String csrf_token_server = getSessionString(session, "csrf_token");

        if (csrf_token.equals(csrf_token_server)) {
            result = true;
        }
        else {
            result = false;
        }
        return result;
    }

    // xss 스크립트 치환
    public static Map<String, Object> convertXssScript(Map<String, Object> map) {

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue().getClass().getName().equals("java.lang.String")) {
                entry.getValue().replaceAll("<", "&lt;");
                entry.getValue().replaceAll(">", "&gt;");
                entry.getValue().replaceAll(",", "&quot;");
                entry.getValue().replaceAll('"', "&#x27;");
                entry.getValue().replaceAll("(", "&#40;");
                entry.getValue().replaceAll(")", "&#41;");
            }
            map.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        return map;

    }

    public static List<Map<String, Object>> convertXssScript(List<Map<String, Object>> list) {
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> data = list.get(i);

            data = convertXssScript(data);
            list.set(i, data);
        }
        return list;
    }


    public static boolean getLoginFlag(HttpSession session) {
        boolean result = false;
        if (isNullOrEmpty(getSessionString(session, "usr_id"))) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
