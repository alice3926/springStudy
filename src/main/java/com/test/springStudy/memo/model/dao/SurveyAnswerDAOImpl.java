package com.test.springStudy.memo.model.dao;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.survey.model.dto.SurveyAnswerDTO;

@Repository
public class SurveyAnswerDAOImpl implements SurveyAnswerDAO{
	String tableName01="survey";
	String tableName02="survey_answer";
	@Inject
	SqlSession session;
	@Override
	public int setInsert(SurveyAnswerDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		int result  = session.insert("survey.setInsert", map);
		return result;
	}
	@Override
	public int getSurvey_counter(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		int result   = session.insert("survey.getSurvey_counter",map);
		return result;
	}
}
