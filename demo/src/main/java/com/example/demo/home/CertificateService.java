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
public class CertificateService {
	
	private static final Logger log = LoggerFactory.getLogger(CertificateService.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	ListService listService;
	
	private static final String NAMESPACE = "certificate.";

	public List<Map<String, Object>> getCertificateList(Map<String, Object> resumeInfo) {
		// 자격증 정보
		List<Map<String, Object>> certificateList = sqlSession.selectList(NAMESPACE + "getCertificateList", resumeInfo);
		certificateList = Util.convertKeysToLowerCaseList(certificateList);
		return certificateList;
	}
	
	public void inputCertificateList(Map<String, Object> inputParams, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("certificateList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Certificate";

		listService.insertListMapResume(newList, resume_id, queryString, NAMESPACE);
	}
	
	public void updateCertificateList(Map<String, Object> updateParams) {
		List<Map<String, Object>> certificateList = (List<Map<String, Object>>) updateParams.get("certificateList");
		List<Map<String, Object>> old_certificateList = sqlSession.selectList(NAMESPACE + "getCertificateList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Certificate";

		listService.updateListMapResume(certificateList, old_certificateList, queryTag, resume_id, NAMESPACE);
	}
}
