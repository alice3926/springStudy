package com.test.springStudy.memo.model.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.memo.model.dto.MemoDTO;
@Repository
public class MemoDAOImpl implements MemoDAO {
	String table_1 = "memo";
	@Inject
	SqlSession sqlSession;
	
	@Override
	public int setInsert(MemoDTO dto) {
		Map<String,Object> map = new HashMap<>();
		map.put("dto", dto);
		 
		int result = sqlSession.insert("memo.setInsert",map);
		return result;
	}

	@Override
	public List<MemoDTO> getList(int startRow, int endRow) {
		Map<String,String> map = new HashMap<>();
		map.put("startRecord", startRow+"");
		map.put("lastRecord", endRow+"");
		map.put("table_1", table_1);
		 
		List<MemoDTO> list = sqlSession.selectList("memo.getList",map);
		return list;
	}

	@Override
	public int getTotalRecord() {
		Map<String,String> map = new HashMap<>();
		map.put("table_1", table_1);
		 
		int result = sqlSession.selectOne("memo.getTotalRecord",map);
		return result;
	}

	@Override
	public MemoDTO getOne(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		MemoDTO dto2 = sqlSession.selectOne("memo.getOne",map);
		return dto2;
	}

	@Override
	public int setSujung(MemoDTO dto2) {
		Map<String,Object> map = new HashMap<>();
		map.put("dto", dto2);
		
		int result = sqlSession.update("memo.setSujung",map);
		return result;
	}

	@Override
	public int setSakjae(MemoDTO dto2) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto2);
		
		int result = sqlSession.delete("memo.setSakjae",map);
		return result;
	}

}
