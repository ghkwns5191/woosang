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
public class ListService {
	
	private static final Logger log = LoggerFactory.getLogger(ListService.class);
	
	@Autowired
	SqlSession sqlSession;


	public void updateListMapResume(
	        List<Map<String, Object>> newList
	        , List<Map<String, Object>> oldList
	        , String queryTag
	        , String resume_id
	        , String NAMESPACE
	    ) {
			int newSize = newList.size();
			int oldSize = oldList.size();

	        if (newSize > 0 && oldSize > 0) {
				// 경력정보
				if (newSize == oldSize) {

					for (int i = 0; i < newSize; i++) {
						Map<String, Object> data = newList.get(i);
						data.put("resume_id", resume_id);
						data.put("id", Util.objectToString(oldList.get(i).get("id")));
						sqlSession.update(NAMESPACE + "update"+queryTag+"Info", data);
					}

				} else if (newSize > oldSize) {

					for (int i = 0; i < newSize; i++) {
						Map<String, Object> data = newList.get(i);
						
						data.put("resume_id", resume_id);
						if (i < oldSize) {
							// update
							data.put("id", Util.objectToString(oldList.get(i).get("id")));
							sqlSession.update(NAMESPACE + "update"+queryTag+"Info", data);
						} else if (i >= oldSize) {
							// insert
							data.put("id", UUID.randomUUID().toString());
							sqlSession.insert(NAMESPACE + "insert"+queryTag+"Info", data);
						}
					}

				} else if (newSize < oldSize) {

					for (int i = 0; i < oldSize; i++) {
						
						Map<String, Object> data = newList.get(i);
						data.put("resume_id", resume_id);
						if (i < newSize) {
							// update
							data.put("id", Util.objectToString(oldList.get(i).get("id")));
							sqlSession.update(NAMESPACE + "update"+queryTag+"Info", data);
						} else if (i >= newSize) {
							data = oldList.get(i);
							// delete
							sqlSession.delete(NAMESPACE + "delete"+queryTag+"Info", data);
						}
					}
				}
			} else if (newSize == 0 && oldSize > 0) {
				for (int i = 0; i < oldSize; i++) {
					Map<String, Object> data = oldList.get(i);
					sqlSession.delete(NAMESPACE + "delete"+queryTag+"List", data);
				}
			} else if (newSize > 0 && oldSize == 0) {
	            this.insertListMapResume(newList, resume_id, queryTag, NAMESPACE);
			} 
	    }



	    public void insertListMapResume(
	        List<Map<String, Object>> newList
	        , String resume_id
	        , String queryString
	        , String NAMESPACE
	    ) {
	        for (Map<String, Object> data : newList) {
				String id = UUID.randomUUID().toString();
				data.put("resume_id", resume_id);
				data.put("id", id);
				sqlSession.insert(NAMESPACE + "insert" + queryString + "Info", data);
			}
	    }

}
