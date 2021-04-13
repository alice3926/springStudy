package com.test.springStudy.memo.model.dao;

import com.test.springStudy.survey.model.dto.SurveyAnswerDTO;

public interface SurveyAnswerDAO {
	public int setInsert(SurveyAnswerDTO dto);
	public int getSurvey_counter(int no); 
}
