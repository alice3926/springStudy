package com.test.springStudy.survey.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.survey.model.dto.SurveyAnswerDTO;
import com.test.springStudy.survey.model.dto.SurveyDTO;

@Repository
public class SurveyDAOImpl implements SurveyDAO{
	String tableName01="survey";
	String tableName02="survey_answer";
	@Inject
	SqlSession session;
	@Override
	public int setInsert(SurveyDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		int result  = session.insert("survey.setInsert", map);
		return result;
	}
	@Override
	public List<SurveyDTO> getList(String list_gubun, int startRecord, int lastRecord, String search_option,String search_data,String search_date_s,String search_date_e,String search_date_check){
		 Map<String,String> map = new HashMap<>();
		 map.put("startRecord", startRecord+"");
		 map.put("lastRecord", lastRecord+"");
		 map.put("list_gubun", list_gubun);
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("search_date_s", search_date_s);
		 map.put("search_date_e", search_date_e);
		 map.put("search_date_e", search_date_check);
		 
		 List<SurveyDTO> list = session.selectList("survey.getList",map);
		 return list;
	}
	@Override
	public int getTotalRecord(String list_gubun, String search_option, String search_data, String search_date_s,
			String search_date_e, String search_date_check) {
		 Map<String,String> map = new HashMap<>();
		 map.put("list_gubun", list_gubun);
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("search_date_s", search_date_s);
		 map.put("search_date_e", search_date_e);
		 map.put("search_date_e", search_date_check);
		 

		 int result = session.selectOne("survey.getTotalRecord",map);
		 return result; 
	}
	@Override
	public SurveyDTO getOne(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		SurveyDTO dto2 = session.selectOne("survey.getOne", map);
		return dto2;
	}
	@Override
	public int setInsertAnswer(SurveyAnswerDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		int result  = session.insert("survey.setInsertAnswer", map);
		return result;
	}
	@Override
	public int setSujung(SurveyDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		int result  = session.update("survey.setSujung", map);
		return result;
	}
	@Override
	public int setSakjae(int no) {
		int result = session.delete("survey.setSakjae",no);
		return result;
	}
	
	
}
