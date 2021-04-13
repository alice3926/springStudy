package com.test.springStudy.survey.util;

import com.test.springStudy.common.Util;

public class SurveyUtil extends Util{
	public String[] searchCheck(String search_option,String search_data,String search_date_s,String search_date_e,String search_date_check) {
		String[] result = new String[5];
		
		if(search_option==null || search_option.trim().equals("")) {
			search_option="";
		}
		
		search_option = search_option.trim();
		if(search_option.equals("")){	

		}else if(search_option.equals("question")) {
			
		}else {
			search_option ="";
		}
		
		if(search_data==null || search_data.trim().equals("")) {
			search_data="";
		}
		search_option = search_option.trim();
		if(search_option.equals("")||search_data.equals("")){	
			search_option="";
			search_data="";
		}
		if(search_date_check==null|| search_date_check.trim().equals("")) {
			search_date_check="";
		}
		if(search_date_check.equals("o")) {
			if(search_date_s==null || search_date_s.trim().equals("")) {
				search_date_s="";
			}
			search_date_s=search_date_s.trim();
			if(search_date_e==null || search_date_e.trim().equals("")) {
				search_date_e="";
			}
			search_date_e=search_date_e.trim();
			if(search_date_s.equals("") || search_date_e.equals("")) {
				search_date_check="";
			}
		}else {
			search_date_check="";
			search_date_s="";
			search_date_e="";
		}

		//의문점 : check, date_s,date_e중에 하나라도 공백이면 다공백처리...가 더 편리한듯.
		
		
		result[0]=search_option;
		result[1]=search_data;
		result[2]=search_date_s;
		result[3]=search_date_e;
		result[4]=search_date_check;
		return result;
		
	}
	public String list_gubunCheck(String list_gubun) {
		if(list_gubun==null||list_gubun.trim().equals("")) {
			list_gubun="all";
		}
		list_gubun=list_gubun.trim();
		if(list_gubun.equals("all")) {
			
		}else if(list_gubun.equals("ing")) {
			
		}else if(list_gubun.equals("end")) {
			
		}else if(list_gubun.equals("future")) {
			
		}else {
			list_gubun="all";
		}
		return list_gubun;
	}
}
