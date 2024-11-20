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
public class AcademicService {
	
	private static final Logger log = LoggerFactory.getLogger(AcademicService.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ListService listService;
	
	private static final String NAMESPACE = "academic.";

	public List<Map<String, Object>> getAcademicList(Map<String, Object> resumeInfo) {
		// 학력 정보
		List<Map<String, Object>> academicList = sqlSession.selectList(NAMESPACE + "getAcademicList", resumeInfo);
		academicList = Util.convertKeysToLowerCaseList(academicList);
		return academicList;
	}
	
	public void inputAcademicList(Map<String, Object> inputParams, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("academicList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Academic";

		listService.insertListMapResume(newList, resume_id, queryString, NAMESPACE);
	}
	
	public void updateAcademicList(Map<String, Object> updateParams) {
		List<Map<String, Object>> academicList = (List<Map<String, Object>>) updateParams.get("academicList");
		List<Map<String, Object>> old_academicList = sqlSession.selectList(NAMESPACE + "getAcademicList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Academic";

		listService.updateListMapResume(academicList, old_academicList, queryTag, resume_id, NAMESPACE);
	}
}
