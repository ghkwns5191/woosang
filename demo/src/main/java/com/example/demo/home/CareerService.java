package com.example.demo.home;

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
public class CareerService {
	
	private static final Logger log = LoggerFactory.getLogger(CareerService.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ListService listService;
	
	private static final String NAMESPACE = "career.";

	public List<Map<String, Object>> getCareerList(Map<String, Object> resumeInfo) {
		// 경력정보 
		List<Map<String, Object>> careerList = sqlSession.selectList(NAMESPACE + "getCareerList", resumeInfo);
		careerList = Util.convertKeysToLowerCaseList(careerList);
		return careerList;
	}
	
	public void inputCareerList(Map<String, Object> inputParams, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("careerList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Career";

		listService.insertListMapResume(newList, resume_id, queryString, NAMESPACE);
	}
	
	public void updateCareerList(Map<String, Object> updateParams) {
		List<Map<String, Object>> careerList = (List<Map<String, Object>>) updateParams.get("careerList");
		List<Map<String, Object>> old_careerList = sqlSession.selectList(NAMESPACE + "getCareerList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Career";

		listService.updateListMapResume(careerList, old_careerList, queryTag, resume_id, NAMESPACE);
	}
}
