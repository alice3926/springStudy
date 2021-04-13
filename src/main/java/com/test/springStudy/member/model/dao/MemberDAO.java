package com.test.springStudy.member.model.dao;

import java.util.List;

import com.test.springStudy.member.model.dto.MemberDTO;

public interface MemberDAO {
	public int getIdCheck(String id);
	public String getIdCheckWin(String id);
	public int setInsert(MemberDTO dto);	
	public MemberDTO setlogin(MemberDTO dto);
	public int getTotalRecord(String search_option, String search_data);
	public List<MemberDTO> getList(int startRecord,int lastRecord, String search_option, String search_data);
	public MemberDTO getOne(int no, String search_option, String search_data);
	public int setSujung(MemberDTO dto);
	public int setSakjae(MemberDTO dto);
	
}


