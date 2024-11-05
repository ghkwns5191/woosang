package com.example.demo.home;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.utils.Util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Transactional
@Service
public class HomeService {
	
	private static final Logger log = LoggerFactory.getLogger(HomeService.class);
	
	@Autowired
	SqlSession sql;
	
	private static final String NAMESPACE = "home.";
	
	
	/*
	 * 로그인 메서드 (반환 값은 true or false)
	 * 로그인 처리 후 true 는 성공, false 는 실패
	 */
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> loginInfo) {
		Map<String, Object> result = new HashMap<>();
		boolean loginFlag = false;
		String username = "";
		HttpSession session = request.getSession();
		SHA256 sha = new SHA256();
		
		/*
		 * 입력받은 username 및 password 에서
		 * username 에 해당하는 회원 데이터가 있는지 확인하여 해당 데이터의 username 과 password(암호화된) 데이터를 추출.
		 * 로그인 창에 입력받은 password 를 암호화 해서 DB 값과 일치여부를 확인 함. 
		 * 일치하면 성공, 일치하지 않으면 비밀번호 오류로 실패.
		 */
		Map<String, Object> infoById = Util.mapValidate(sql.selectOne(NAMESPACE + "getInfoById", loginInfo));
		log.info((String)infoById.get("USERNAME"));
		log.info(infoById.toString());
		String usr_id = (String) infoById.get("ID");
		if ((String)infoById.get("USERNAME") == null) {
			loginFlag = false;

		} else {
			username = Util.mapToString(infoById, "USERNAME");
			String savedPassword = Util.mapToString(infoById, "PASSWORD");
			String inputPassword = Util.mapToString(loginInfo, "password");
			
			try {
				inputPassword = sha.encrypt(inputPassword);
				
				log.info(savedPassword);
				log.info(inputPassword);
				
				loginFlag = sha.validatePassword(savedPassword, inputPassword);
				
				if (loginFlag) {
					request.getSession().invalidate();  // 기존 세션 무효화
					session = request.getSession(true);
					session.setAttribute("usr_id", usr_id);
				} else {
					
				}
			} catch(Exception e) {
				
			}
			
			
		}
		log.info(username);
		log.info(String.valueOf(loginFlag));
		log.info(session.toString());
		result.put("username", username);
		result.put("loginFlag", loginFlag);
		result.put("session", session);
		
		
		return result;
	}
	
	
	
	/*
	 * 회원가입 메서드 (반환 값은 true or false)
	 * 회원가입 처리 후 true 는 성공, false 는 실패
	 */
	public Map<String, Object> join(HttpServletRequest request, HttpServletResponse response, Map<String, Object> joinInfo) {
		
		Map<String, Object> result = new HashMap<>();
		boolean joinFlag = false;
		String password = Util.mapToString(joinInfo, "password");
		SHA256 sha = new SHA256();
		
		
		try {
			password = sha.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		joinInfo.put("password", password);
		
		List<Map<String, Object>> usernameData = sql.selectList(NAMESPACE + "checkUsername", joinInfo);
		
		if (usernameData.size() > 0) {
			joinFlag = false;
		} else if (usernameData.size() == 0) {
			
			
			String id = UUID.randomUUID().toString();
			joinInfo.put("id", id);
			sql.insert(NAMESPACE + "insertNewUser", joinInfo);
			joinFlag = true;
		}
		
		result.put("username", Util.mapToString(joinInfo, "username"));
		result.put("joinFlag", joinFlag);
		
		
		return result;
	}
}
