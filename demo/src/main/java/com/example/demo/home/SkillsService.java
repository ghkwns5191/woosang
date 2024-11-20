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
public class SkillsService {
	
	private static final Logger log = LoggerFactory.getLogger(SkillsService.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ListService listService;
	
	private static final String NAMESPACE = "skills.";

	public List<Map<String, Object>> getSkillsList(Map<String, Object> resumeInfo) {
		// 보유기술 정보
		List<Map<String, Object>> skillsList = sqlSession.selectList(NAMESPACE + "getSkillsList", resumeInfo);
		skillsList = Util.convertKeysToLowerCaseList(skillsList);
		return skillsList;
	}
	
	public void inputSkillsList(Map<String, Object> inputParams, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("skillsList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Skills";

		listService.insertListMapResume(newList, resume_id, queryString, NAMESPACE);
	}
	
	public void updateSkillsList(Map<String, Object> updateParams) {
		List<Map<String, Object>> skillsList = (List<Map<String, Object>>) updateParams.get("skillsList");
		List<Map<String, Object>> old_skillsList = sqlSession.selectList(NAMESPACE + "getSkillsList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Skills";

		listService.updateListMapResume(skillsList, old_skillsList, queryTag, resume_id, NAMESPACE);
	}
}
