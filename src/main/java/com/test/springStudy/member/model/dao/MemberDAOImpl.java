package com.test.springStudy.member.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.member.model.dto.MemberDTO;
@Repository
public class MemberDAOImpl implements MemberDAO {
	String table_1 ="member";
	@Inject
	SqlSession sqlSession;
	
	@Override
	public int getIdCheck(String id) {
		 int result = sqlSession.selectOne("member.getIdCheck",id);
		 return result;
	}

	@Override
	public String getIdCheckWin(String id) {
		 String result = sqlSession.selectOne("member.getIdCheckWin",id);
		 return result;
	}

	@Override
	public int setInsert(MemberDTO dto) {
		Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
			 
		 int result = sqlSession.insert("member.setInsert",map);
		 return result;
	}

	@Override
	public MemberDTO setlogin(MemberDTO dto) {
		Map<String,Object> map = new HashMap<>();
		 map.put("id", dto.getId());
		 map.put("passwd", dto.getPasswd());
		 map.put("table_1", table_1);
		 
		 MemberDTO dto1 = sqlSession.selectOne("member.setlogin",map);
		 return dto1;
	}

	@Override
	public int getTotalRecord(String search_option, String search_data) {
		Map<String,String> map = new HashMap<>();
		 map.put("search_option", search_option);
		 map.put("search_data", search_data); 
		 map.put("table_1", table_1);
		 
		 int result = sqlSession.selectOne("member.getTotalRecord",map);
		 return result;
	}

	@Override
	public List<MemberDTO> getList(int startRecord, int lastRecord, String search_option, String search_data) {
		 Map<String,String> map = new HashMap<>();
		 map.put("startRecord", startRecord+"");
		 map.put("lastRecord", lastRecord+"");
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("table_1", table_1);
		 
		 
		 List<MemberDTO> list = sqlSession.selectList("member.getList",map);
		 return list;
	}

	@Override
	public MemberDTO getOne(int no, String search_option, String search_data) {
		Map<String,Object> map = new HashMap<>();
		 map.put("no", no);
		 map.put("search_option", search_option);
		 map.put("search_data", search_data);
		 map.put("table_1", table_1);
		 
		 MemberDTO dto2 = sqlSession.selectOne("member.getOne",map);
		 return dto2;
	}

	@Override
	public int setSujung(MemberDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
			 
		 int result = sqlSession.update("member.setSujung",map);
		 return result;
	}

	@Override
	public int setSakjae(MemberDTO dto) {
		Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);

		 int result = sqlSession.delete("member.setSakjae",map);
		 return result;
	}

}
