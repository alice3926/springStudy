package com.test.springStudy.survey.model.dao;

import java.util.List;

import com.test.springStudy.survey.model.dto.SurveyAnswerDTO;
import com.test.springStudy.survey.model.dto.SurveyDTO;


public interface SurveyDAO {
	public int setInsert(SurveyDTO dto);
	public List<SurveyDTO> getList(String list_gubun,int startRecord,int lastRecord, String search_option ,String search_data, String search_date_s,String search_date_e,String search_date_check);
	public int getTotalRecord(String list_gubun, String search_option ,String search_data ,String search_date_s, String search_date_e, String search_date_check); 
	public SurveyDTO getOne(int no); 
	public int setInsertAnswer(SurveyAnswerDTO dto);
	public int setSujung(SurveyDTO dto); 
	public int setSakjae(int no); 
}