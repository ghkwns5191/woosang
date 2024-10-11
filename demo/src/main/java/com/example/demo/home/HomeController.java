package com.example.demo.home;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.utils.Util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired
	private HomeService homeService;

	@RequestMapping("/")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		Map<String, Object> datas = new HashMap<>();
		datas = this.homeService.getDialogs();
		
		return "ui/home";
	}
	
	
	@RequestMapping("/chatting")
	public String chatting(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		Map<String, Object> datas = new HashMap<>();
//		datas = this.homeService.getDialogs();
		
		return "ui/login";
	}
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
//		this.homeService.login(request, response);
//		
//		Map<String, Object> datas = new HashMap<>();
//		datas = this.homeService.getDialogs();
		
		return "ui/login";
	}
	
	
	@RequestMapping("/room")
	public String room(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		log.info(sessionVal);
		log.info((String) session.getAttribute("username"));
		Map<String, Object> datas = new HashMap<>();
//		datas = this.homeService.getDialogs();
		
		return "ui/index";
	}
	
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> loginInfo) {
		boolean result = false;
		log.info(loginInfo.toString());
		
		/*
		 *  로그인 메서드 시용하여 처리 후 결과 받아서 map 에 담음.
		 *  처리 후에는 
		 *  1. 로그인 문제없는지 여부
		 *  2. 로그인 후 갱신한 세션 값
		 *  3. 로그인에 성공한 username --> ID 값으로 변경할까 고민 중.
		 *  정보를 반환.
		 */
		Map<String, Object> loginResult = this.homeService.login(request, response, loginInfo);
		log.info(loginResult.toString());
		
		
		if ((boolean) loginResult.get("loginFlag")) {
			result = true;
			
			
			HttpSession newSession = (HttpSession) loginResult.get("session");
			newSession.setAttribute("username", Util.mapToString(loginResult, "username"));
			log.info(newSession.toString());
			
			// 로그 인 성공 후에는 세션값을 갱신하고 쿠기 값으로 username 을 함께 전송해둠.
			Cookie cookie = new Cookie("username", Util.mapToString(loginResult, "username"));
			response.addCookie(cookie);  // 쿠키를 클라이언트에 추가
			log.info(cookie.toString());
		} else {
			result = false;
		}
		String res = "";
		
		if (result) {
			res = "success";
		} else {
			res = "fail";
		}
		
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/join", method=RequestMethod.POST)
	public ResponseEntity<String> join(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> joinInfo) {
		boolean result = false;
		
		Map<String, Object> joinResult = this.homeService.join(request, response, joinInfo);
		if ((boolean) joinResult.get("joinFlag")) {
			result = true;
		} else {
			result = false;
		}
		String res = "";
		if (result) {
			res = "success";
		} else {
			res = "fail";
		}
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
