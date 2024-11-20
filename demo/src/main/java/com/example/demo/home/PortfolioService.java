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
public class PortfolioService {
	
	private static final Logger log = LoggerFactory.getLogger(PortfolioService.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ListService listService;
	
	private static final String NAMESPACE = "portfolio.";

	public List<Map<String, Object>> getPortfolioList(Map<String, Object> resumeInfo) {
		// 포트폴리오 정보
		List<Map<String, Object>> portfolioList = sqlSession.selectList(NAMESPACE + "getPortfolioList", resumeInfo);
		portfolioList = Util.convertKeysToLowerCaseList(portfolioList);
		return portfolioList;
	}
	
	public void inputPortfolioList(Map<String, Object> inputParams, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("portfolioList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Portfolio";

		listService.insertListMapResume(newList, resume_id, queryString, NAMESPACE);
	}
	
	public void updatePortfolioList(Map<String, Object> updateParams) {
		List<Map<String, Object>> portfolioList = (List<Map<String, Object>>) updateParams.get("portfolioList");
		List<Map<String, Object>> old_portfolioList = sqlSession.selectList(NAMESPACE + "getPortfolioList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Portfolio";

		listService.updateListMapResume(portfolioList, old_portfolioList, queryTag, resume_id, NAMESPACE);
	}
}
