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
＠RequestMapping(value = {"", "/"})
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	
	@Autowired
	private HomeService homeService;

	/*
	 * 구조도
	 * 1. 로그인 / 회원가입
	 * 2. 개인 이력 정보 입력 및 조회
	 * 3. 채용담당자 로그인 시 조회 
	 * -- 일단 여기까지 구현하고 미팅 후 추가 개발
	 * -- ui 는 일단 bootstrap 으로 기본 구현 후 상세 디자인은 추후 적용할 예정.
	 * (우선 개발 모듈 확인이 우선)
	 * 현재는 타임리프 사용 중이기 떄문에 return 타입은 String 으로 받는 것이 유리해보임. (추후 vue.js 등으로 변경 검토 필요함. / 양방향 바인딩이 편하기 때문.)
	 */

	/* TO-DO List : 사용자 테이블 및 이력서 테이블 & 상세 테이블 확인 및 설계 필요 */

	/*
	* 회원가입
	* 필요한 정보들을 입력
	* pw 암호화 등 적용하여 (이미 서비스 로직으로 만들어 놓음)
	* insert 후 로그인 화면으로 이동 처리
	*/

	// 회원가입 화면으로 이동.
	@ReqeustMapping(value="/join", method=RequestMethod.GET)
	public String join(HttpServletRequest request
						, HttpServletResponse response) {

		return "회원가입 페이지";
	}
	
	// 실제 회원가입 로직 호출.
	@ReqeustMapping(value="/join", method=RequestMethod.POST)
	public ResponseEntity join(HttpServletRequest request
						, HttpServletResponse response
						, @RequestBody Map<String, Object> joinInfo) {
		

		Map<String, Object> result = this.homeService.join(request, response, joinInfo);
		return "회원가입 페이지";
	}

	/*
	* 로그인
	* id / pw 입력 받음.
	* pw를 복호화 및 입력값과 일치여부 확인 (이미 서비스 로직으로 만들어 놓음)
	* 성공 시 홈 화면으로 이동.
	*/

	// 로그인 화면으로 이동.
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request
						, HttpServletResponse response) {

		
		return "로그인 화면";
	}

	// 로그인 화면에서 로그인 진행.
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object> login(HttpServletRequest request
						, HttpServletResponse response
						, @RequestBody Map<String, Object> loginInfo) {

		Map<String, Object> result = this.homeService.login(request, response, loginInfo);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/*
	* 이력서 조회
	* 해당 인원의 해당 이력정보를 가져옴.
	* (일반 Map 데이터와 List<Map> 데이터를 모두 뽑아내어 model 데이터로 전송 (Response))
	* pw를 복호화 및 입력값과 일치여부 확인 (이미 서비스 로직으로 만들어 놓음)
	* 성공 시 홈 화면으로 이동.
	*/

	// 이력서 조회 화면으로 이동.
	@RequestMapping(value="/resumelist", method=RequestMethod.GET)
	public String getResumeListByUser(HttpServletRequest request
									, HttpServletResponse response
									, @RequestBody Map<String, Object> loginInfo){
		List<Map<String, Object>> resumeList = this.homeService.getResumeListByUser(loginInfo);								
		model.addAttribute("resumeList", resumeList);
		
		return "이력서 목록 조회 화면";
	}

	// 이력서 상세 조회 화면으로 이동.
	@RequestMapping(value="/resumeInfo", method=RequestMethod.GET)
	public String getResumeInfo(HttpServletRequest request, HttpServletResponse response
								, @RequestBody Map<String, Object> resumeInfo) {
		
		resumeInfo = this.homeService.getResumeInfo(resumeInfo);
		model.addAttribute("resumeInfo", resumeInfo);


		return "이력서 상세 조회 화면";
	}



	@RequestMapping(value="/inputResume", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> inputResume(@RequestBody Map<String, Object> inputParams) {
		this.homeService.inputResume(inputParams);

		return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
	}
	

	@RequestMapping(value="/updateResume", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> updateResume(@RequestBody Map<String, Object> updateParams) {
		this.homeService.updateResume(updateParams);

		return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
	}


	@RequestMapping(value="/deleteResume", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> deleteResume(@RequestBody Map<String, Object> deleteParams) {
		this.homeService.deleteResume(deleteParams);

		return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
	}
	
}
