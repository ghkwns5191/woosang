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
public class ResumeService {
	
	private static final Logger log = LoggerFactory.getLogger(ResumeService.class);
	
	@Autowired
	SqlSession sqlSession;
	
	private static final String NAMESPACE = "resume.";


	// 신규 이력서 작성 시 사용자 기본정보를 조회
	public Map<String, Object> getUsrDataforNewResume(HttpServletRequest request
														, HttpServletResponse response) {
		Map<String, Object> loginInfo = new HashMap<>();
		HttpSession session = request.getSession();
		String usr_id = (String) session.getAttribute("usr_id");
		loginInfo.put("usr_id", usr_id);

		return Util.convertKeysToLowerCase(Util.mapValidate(sqlSession.selectOne(NAMESPACE + "getUsrDataforNewResume", loginInfo)));
	}
	

	// 화면에 이력서 리스트를 뿌림.
	public List<Map<String, Object>> getResumeListByUser(HttpServletRequest request
														, HttpServletResponse response) {
		Map<String, Object> loginInfo = new HashMap<>();
		HttpSession session = request.getSession();
		String usr_id = (String) session.getAttribute("usr_id");
		loginInfo.put("usr_id", usr_id);
		// 사용자 이름을 이용해 uuid 를 찾아서 해당 값을 기준으로 이력서 정보를 load. (쿼리문으로 바로 처리 가능. parameter 는 username)
		return sqlSession.selectList(NAMESPACE + "getResumeListByUser", loginInfo);
	}

	public Map<String, Object> getBasicInfo(Map<String, Object> resumeInfo) {
		// 기본정보
		Map<String, Object> basicInfo = sqlSession.selectOne(NAMESPACE + "getBasicInfo", resumeInfo);
		basicInfo = Util.convertKeysToLowerCase(basicInfo);
		return basicInfo;
	}

	public List<Map<String, Object>> getCareerList(Map<String, Object> resumeInfo) {
		// 경력정보 
		List<Map<String, Object>> careerList = sqlSession.selectList(NAMESPACE + "getCareerList", resumeInfo);
		careerList = Util.convertKeysToLowerCaseList(careerList);
		return careerList;
	}

	public List<Map<String, Object>> getAcademicList(Map<String, Object> resumeInfo) {
		// 학력 정보
		List<Map<String, Object>> academicList = sqlSession.selectList(NAMESPACE + "getAcademicList", resumeInfo);
		academicList = Util.convertKeysToLowerCaseList(academicList);
		return academicList;
	}

	public List<Map<String, Object>> getCertificateList(Map<String, Object> resumeInfo) {
		// 자격증 정보
		List<Map<String, Object>> certificateList = sqlSession.selectList(NAMESPACE + "getCertificateList", resumeInfo);
		certificateList = Util.convertKeysToLowerCaseList(certificateList);
		return certificateList;
	}

	public List<Map<String, Object>> getPortfolioList(Map<String, Object> resumeInfo) {
		// 포트폴리오 정보
		List<Map<String, Object>> portfolioList = sqlSession.selectList(NAMESPACE + "getPortfolioList", resumeInfo);
		portfolioList = Util.convertKeysToLowerCaseList(portfolioList);
		return portfolioList;
	}

	public List<Map<String, Object>> getSkillsList(Map<String, Object> resumeInfo) {
		// 보유기술 정보
		List<Map<String, Object>> skillsList = sqlSession.selectList(NAMESPACE + "getSkillsList", resumeInfo);
		skillsList = Util.convertKeysToLowerCaseList(skillsList);
		return skillsList;
	}


	// 화면에 이력서 정보를 뿌림.
	public Map<String, Object> getResumeInfo(Map<String, Object> resumeInfo) {
		Map<String, Object> result = new HashMap<>();

		// 기본정보
		Map<String, Object> basicInfo = sqlSession.selectOne(NAMESPACE + "getBasicInfo", resumeInfo);

		// 경력정보 
		List<Map<String, Object>> careerList = sqlSession.selectList(NAMESPACE + "getCareerList", resumeInfo);

		// 학력 정보
		List<Map<String, Object>> academicList = sqlSession.selectList(NAMESPACE + "getAcademicList", resumeInfo);
		
		basicInfo = Util.convertKeysToLowerCase(basicInfo);
		careerList = Util.convertKeysToLowerCaseList(careerList);
		academicList = Util.convertKeysToLowerCaseList(academicList);

		result.put("basicInfo", basicInfo);
		result.put("careerList", careerList);
		result.put("academicList", academicList);
		result.put("careerLength", careerList.size());
		result.put("academicLength", academicList.size());

		return result;
	}


	// 이력서 기본정보 신규 등록
	public void inputBasicInfo(Map<String, Object> inputParams, HttpServletRequest request) {
		Map<String, Object> basicInfo = (Map<String, Object>) inputParams.get("basicInfo");
		basicInfo = Util.convertKeysToLowerCase(basicInfo);
		HttpSession session = request.getSession();
		// 이력서 기본정보(header)
		String id = UUID.randomUUID().toString();
		String usr_id = (String) request.getSession().getAttribute("usr_id");
		basicInfo.put("id", id);
		basicInfo.put("usr_id", usr_id);
		sqlSession.insert(NAMESPACE + "insertBasicInfo", basicInfo);
		session.setAttribute("resume_id_new", id);
	}

	public void inputCareerList(Map<String, Object> inputParams, HttpServletRequest request) {
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("careerList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Career";

		Util.insertListMapResume(newList, resume_id, queryString);
	}

	public void inputAcademicList(Map<String, Object> inputParams, HttpServletRequest request) {
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("academicList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Academic";

		Util.insertListMapResume(newList, resume_id, queryString);
	}

	public void inputCertificateList(Map<String, Object> inputParams, HttpServletRequest request) {
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("certificateList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Certificate";

		Util.insertListMapResume(newList, resume_id, queryString);
	}

	public void inputPortfolioList(Map<String, Object> inputParams, HttpServletRequest request) {
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("portfolioList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Portfolio";

		Util.insertListMapResume(newList, resume_id, queryString);
	}

	public void inputSkillsList(Map<String, Object> inputParams, HttpServletRequest request) {
		List<Map<String, Object>> newList = (List<Map<String, Object>>) inputParams.get("skillsList");
		String resume_id = Util.objectToString(session.getAttribute("resume_id_new"));
		String queryString = "Skills";

		Util.insertListMapResume(newList, resume_id, queryString);
	}

	// 이력서 정보 신규 등록.
	public void inputResume(Map<String, Object> inputParams, HttpServletRequest request) {
		Map<String, Object> basicInfo = (Map<String, Object>) inputParams.get("basicInfo");
		List<Map<String, Object>> careerList = (List<Map<String, Object>>) inputParams.get("careerList");
		List<Map<String, Object>> academicList = (List<Map<String, Object>>) inputParams.get("academicList");

		basicInfo = Util.convertKeysToLowerCase(basicInfo);
		careerList = Util.convertKeysToLowerCaseList(careerList);
		academicList = Util.convertKeysToLowerCaseList(academicList);

		// 이력서 기본정보(header)
		String id = UUID.randomUUID().toString();
		String usr_id = (String) request.getSession().getAttribute("usr_id");
		basicInfo.put("id", id);
		basicInfo.put("usr_id", usr_id);
		sqlSession.insert(NAMESPACE + "insertBasicInfo", basicInfo);

		// 경력사항 저장
		for (Map<String, Object> data : careerList) {
			String careerId = UUID.randomUUID().toString();
			data.put("resume_id", id);
			data.put("id", careerId);

			sqlSession.insert(NAMESPACE + "insertCareerInfo", data);
		}

		// 학력사항 저장
		for (Map<String, Object> data : academicList) {
			String academicId = UUID.randomUUID().toString();
			data.put("resume_id", id);
			data.put("id", academicId);

			sqlSession.insert(NAMESPACE + "insertAcademicInfo", data);
		}

	}


	public void updateBasicInfo(Map<String, Object> updateParams) {
		Map<String, Object> basicInfo = (Map<String, Object>) updateParams.get("basicInfo");
		// 기본정보
		sqlSession.update(NAMESPACE + "updateBasicInfo", basicInfo);
	}

	public void updateCareerList(Map<String, Object> updateParams) {
		List<Map<String, Object>> careerList = (List<Map<String, Object>>) updateParams.get("careerList");
		List<Map<String, Object>> old_careerList = sqlSession.selectList(NAMESPACE + "getCareerList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Career";

		Util.updateListMapResume(careerList, old_careerList, queryTag, resume_id);
	}


	public void updateAcademicList(Map<String, Object> updateParams) {
		List<Map<String, Object>> academicList = (List<Map<String, Object>>) updateParams.get("academicList");
		List<Map<String, Object>> old_academicList = sqlSession.selectList(NAMESPACE + "getAcademicList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Academic";

		Util.updateListMapResume(academicList, old_academicList, queryTag, resume_id);
	}


	public void updateCertificateList(Map<String, Object> updateParams) {
		List<Map<String, Object>> certificateList = (List<Map<String, Object>>) updateParams.get("certificateList");
		List<Map<String, Object>> old_certificateList = sqlSession.selectList(NAMESPACE + "getCertificateList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Certificate";

		Util.updateListMapResume(certificateList, old_certificateList, queryTag, resume_id);
	}


	public void updatePortfolioList(Map<String, Object> updateParams) {
		List<Map<String, Object>> portfolioList = (List<Map<String, Object>>) updateParams.get("portfolioList");
		List<Map<String, Object>> old_portfolioList = sqlSession.selectList(NAMESPACE + "getPortfolioList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Portfolio";

		Util.updateListMapResume(portfolioList, old_portfolioList, queryTag, resume_id);
	}


	public void updateSkillsList(Map<String, Object> updateParams) {
		List<Map<String, Object>> skillsList = (List<Map<String, Object>>) updateParams.get("skillsList");
		List<Map<String, Object>> old_skillsList = sqlSession.selectList(NAMESPACE + "getSkillsList", updateParams);
		String resume_id = (String) updateParams.get("id");
		String queryTag = "Skills";

		Util.updateListMapResume(skillsList, old_skillsList, queryTag, resume_id);
	}



	// 이력서 정보 수정 -- 기존 리크루팅 페이지 로직 참고하여 수정 필요..
	/* 
	* list 데이터에 한하여 기존 데이터 size 와 입력 데이터 size 가 다를 수 있는 경우에 대비해야 함.
	*/
	public void updateResume(Map<String, Object> updateParams) {
		Map<String, Object> basicInfo = (Map<String, Object>) updateParams.get("basicInfo");
		List<Map<String, Object>> careerList = (List<Map<String, Object>>) updateParams.get("careerList");
		List<Map<String, Object>> academicList = (List<Map<String, Object>>) updateParams.get("academicList");
		String resume_id = (String) updateParams.get("id");

		List<Map<String, Object>> old_careerList = sqlSession.selectList(NAMESPACE + "getCareerList", basicInfo);
		List<Map<String, Object>> old_academicList = sqlSession.selectList(NAMESPACE + "getAcademicList", basicInfo);



		int newCareerSize = careerList.size();
		int newAcademicSize = academicList.size();

		int oldCareerSize = old_careerList.size();
		int oldAcademicSize = old_academicList.size();

		// 기본정보
		sqlSession.update(NAMESPACE + "updateBasicInfo", basicInfo);

		// 경력정보
		if (newCareerSize == oldCareerSize) {

			for (Map<String, Object> data : careerList) {
				data.put("resume_id", resume_id);
				sqlSession.update(NAMESPACE + "updateCareerInfo", data);
			}

		} else if (newCareerSize > oldCareerSize) {

			for (int i = 0; i < oldCareerSize; i++) {
				// update
				Map<String, Object> data = careerList.get(i);
				data.put("resume_id", resume_id);
				sqlSession.update(NAMESPACE + "updateCareerInfo", data);
			}

			for (int i = oldCareerSize; i < newCareerSize; i++) {
				// insert
				Map<String, Object> data = careerList.get(i);
				data.put("id", UUID.randomUUID().toString());
				data.put("resume_id", resume_id);
				sqlSession.insert(NAMESPACE + "insertCareerInfo", data);
			}


		} else if (newCareerSize < oldCareerSize) {

			for (int i = 0; i < newCareerSize; i++) {
				// update
				Map<String, Object> data = careerList.get(i);
				data.put("resume_id", resume_id);
				sqlSession.update(NAMESPACE + "updateCareerInfo", data);
			}

			for (int i = newCareerSize; i < oldCareerSize; i++) {
				// delete
				Map<String, Object> data = old_careerList.get(i);
				data.put("resume_id", resume_id);
				sqlSession.delete(NAMESPACE + "deleteCareerInfo", data);
			}

		}
		
		// 학력정보
		if (newAcademicSize == oldAcademicSize) {

			for (Map<String, Object> data : academicList) {
				data.put("resume_id", resume_id);
				sqlSession.update(NAMESPACE + "updateAcademicInfo", data);
			}

		} else if (newAcademicSize > oldAcademicSize) {

			for (int i = 0; i < oldCareerSize; i++) {
				// update
				Map<String, Object> data = academicList.get(i);
				data.put("resume_id", resume_id);
				sqlSession.update(NAMESPACE + "updateAcademicInfo", data);
			}

			for (int i = oldAcademicSize; i < newAcademicSize; i++) {
				// insert
				Map<String, Object> data = academicList.get(i);
				data.put("id", UUID.randomUUID().toString());
				data.put("resume_id", resume_id);
				sqlSession.insert(NAMESPACE + "insertAcademicInfo", data);
			}

		} else if (newAcademicSize < oldAcademicSize) {

			for (int i = 0; i < newCareerSize; i++) {
				// update
				Map<String, Object> data = academicList.get(i);
				data.put("resume_id", resume_id);
				sqlSession.update(NAMESPACE + "updateAcademicInfo", data);
			}

			for (int i = newAcademicSize; i < oldAcademicSize; i++) {
				// delete
				Map<String, Object> data = old_academicList.get(i);
				data.put("resume_id", resume_id);
				sqlSession.delete(NAMESPACE + "deleteAcademicInfo", data);
			}
			
		}

	}




	// 이력서 정보 삭제
	public void deleteResume(Map<String, Object> deleteParams) {
		Map<String, Object> basicInfo = (Map<String, Object>) deleteParams.get("basicInfo");

		sqlSession.delete(NAMESPACE + "deleteBasicInfo", basicInfo);
		sqlSession.delete(NAMESPACE + "deleteCareerList", basicInfo);
		sqlSession.delete(NAMESPACE + "deleteAcademicList", basicInfo); 
	}


	public boolean validateResumeId(Map<String, Object> params, HttpSession session) {
		boolean result = false;

		String usr_id_db = Util.mapToString(Util.convertKeysToLowerCase(sqlSession.selectOne(NAMESPACE + "validateResumeId", params)), "usr_id");
		String usr_id_session = Util.getSessionString(session, "usr_id");

		if (usr_id_db.equals(usr_id_session)) result = true;
		else result = false;

		return result;
	} 
}
