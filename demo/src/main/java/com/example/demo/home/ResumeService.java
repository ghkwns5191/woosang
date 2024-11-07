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

		return Util.convertKeysToLowerCase(Util.mapValidate(sqlSession.selectOne(NAMESPACE + "getUsrDataforNewResume", loginINfo)));
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
		for (int i = 0; i < careerList.size(); i++) {
			Map<String, Object> data = careerList.get(i);
			data = Util.convertKeysToLowerCase(data);
			careerList.set(i, data);
		}
		
		for (int i = 0; i < academicList.size(); i++) {
			Map<String, Object> data = academicList.get(i);
			data = Util.convertKeysToLowerCase(data);
			academicList.set(i, data);
		}
		
		log.info(basicInfo.toString());
		log.info(careerList.get(0).toString());
		log.info(academicList.get(0).toString());

		result.put("basicInfo", basicInfo);
		result.put("careerList", careerList);
		result.put("academicList", academicList);

		return result;
	}

	// 이력서 정보 신규 등록.
	public void inputResume(Map<String, Object> inputParams, HttpServletRequest request) {
		Map<String, Object> basicInfo = (Map<String, Object>) inputParams.get("basicInfo");
		List<Map<String, Object>> careerList = (List<Map<String, Object>>) inputParams.get("careerList");
		List<Map<String, Object>> academicList = (List<Map<String, Object>>) inputParams.get("academicList");



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





	// 이력서 정보 수정 -- 기존 리크루팅 페이지 로직 참고하여 수정 필요..
	/* 
	* list 데이터에 한하여 기존 데이터 size 와 입력 데이터 size 가 다를 수 있는 경우에 대비해야 함.
	*/
	public void updateResume(Map<String, Object> updateParams) {
		Map<String, Object> basicInfo = (Map<String, Object>) updateParams.get("basicInfo");
		List<Map<String, Object>> careerList = (List<Map<String, Object>>) updateParams.get("careerList");
		List<Map<String, Object>> academicList = (List<Map<String, Object>>) updateParams.get("academicList");

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
			sqlSession.update(NAMESPACE + "updateCareerInfo", data);
			}

		} else if (newCareerSize > oldCareerSize) {

			for (int i = 0; i < oldCareerSize; i++) {
				// update
				Map<String, Object> data = careerList.get(i);
				sqlSession.update(NAMESPACE + "updateCareerInfo", data);
			}

			for (int i = oldCareerSize; i < newCareerSize; i++) {
				// insert
				Map<String, Object> data = careerList.get(i);
				sqlSession.insert(NAMESPACE + "insertCareerInfo", data);
			}


		} else if (newCareerSize < oldCareerSize) {

			for (int i = 0; i < newCareerSize; i++) {
				// update
				Map<String, Object> data = careerList.get(i);
				sqlSession.update(NAMESPACE + "updateCareerInfo", data);
			}

			for (int i = newCareerSize; i < oldCareerSize; i++) {
				// delete
				Map<String, Object> data = old_careerList.get(i);
				sqlSession.delete(NAMESPACE + "deleteCareerInfo", data);
			}

		}
		

		// 학력정보
		if (newAcademicSize == oldAcademicSize) {

			for (Map<String, Object> data : academicList) {
				sqlSession.update(NAMESPACE + "updateAcademicInfo", data);
			}

		} else if (newAcademicSize > oldAcademicSize) {

			for (int i = 0; i < oldCareerSize; i++) {
				// update
				Map<String, Object> data = academicList.get(i);
				sqlSession.update(NAMESPACE + "updateAcademicInfo", data);
			}

			for (int i = oldAcademicSize; i < newAcademicSize; i++) {
				// insert
				Map<String, Object> data = academicList.get(i);
				sqlSession.insert(NAMESPACE + "insertAcademicInfo", data);
			}

		} else if (newAcademicSize < oldAcademicSize) {

			for (int i = 0; i < newCareerSize; i++) {
				// update
				Map<String, Object> data = academicList.get(i);
				sqlSession.update(NAMESPACE + "updateAcademicInfo", data);
			}

			for (int i = newAcademicSize; i < oldAcademicSize; i++) {
				// delete
				Map<String, Object> data = old_academicList.get(i);
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
}
