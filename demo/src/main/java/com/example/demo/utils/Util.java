package com.example.demo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

public class Util {


    /*
     * 1) StringUtil
     * parameter : String
     * 매개변수가 null 일 경우 "" 반환.
     */

     /*
     * 2) objectToString
     * parameter : Object
     * 매개변수를 String 형태로 cast 하여 반환.
     */

     /*
     * 3) objectToInteger
     * parameter : Object
     * 매개변수를 Integer 형태로 cast 하여 반환.
     */

     /*
     * 4) objectToDouble
     * parameter : Object
     * 매개변수를 Double 형태로 cast 하여 반환.
     */

     /*
     * 5) mapToString
     * parameter : Map<String, Object>, String
     * 매개 변수의 key 값의 데이터를 String 으로 cast 하여 반환.
     */

    /*
     * 6) mapValidate
     * parameter : Map<String, Object>
     * 매개변수로 받은 Map 데이터의 null 체크, null 이면 new HashMap<>() 반환.
     */

     /*
     * 7) convertKeysToLowerCase
     * parameter : Map<String, Object>
     * 매개변수로 받은 Map 데이터의 key 를 모두 소문자로 변환.
     */

     /*
     * 8) convertKeysToLowerCase
     * parameter : List<Map<String, Object>>
     * 매개변수로 받은 List<Map> 데이터 내 모든 Map의 key 를 모두 소문자로 변환.
     */

    /*
     * 9) convertKeysToLowerCase
     * parameter : List<Map<String, Object>>
     * 매개변수로 받은 List<Map> 데이터 내 모든 Map의 key 를 모두 소문자로 변환.
     */

     /*
     * 10) setSessionAttribute
     * parameter : HttpSession, String, Object
     * 세션 Attribute 설정
     */

     /*
     * 11) getSessionString
     * parameter : HttpSession, String
     * 설정된 Session 값을 String 으로 cast 하여 반환.
     */

     /*
     * 12) getSessionMap
     * parameter : HttpSession, String
     * 설정된 Session 값을 Map 으로 cast 하여 반환.
     */

     /*
     * 13) getSessionListMap
     * parameter : HttpSession, String
     * 설정된 Session 값을 List<Map> 으로 cast 하여 반환.
     */

     /*
     * 14) getLoginInfo
     * parameter : HttpSession
     * 세션 내 Attribute 체크하여 로그인 여부 확인 (로그인되어 있다면 : true / 로그인이 X 인 경우 : false)
     */

     /*
     * 15) isNullOrEmpty
     * parameter : String
     * String 이 null 혹은 "" 이라면 true, 값이 있다면 false 반환.
     */

     /*
     * 16) checkCsrf
     * parameter : HttpSession, Map
     * 요청으로 받은 csrf 토큰 값과 서버 내 지정된 csrf 토큰 값 비교 (문제없으면 true, 다르면 false)
     */

     /*
     * 17) convertXssScript
     * parameter : Map
     * Map 으로 요청받은 data 내 악성 script 제거
     */

     /*
     * 18) convertXssScript
     * parameter : List<Map>
     * List<Map> 으로 요청받은 data 내 악성 script 제거
     */

     /*
     * 19) getLoginFlag
     * parameter : HttpSession
     * 로그인 여부 체크 (로그인 완료 : true, 로그인 X : false)
     */
	
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
