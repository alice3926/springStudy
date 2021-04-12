package com.test.springStudy.guestbook.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.guestbook.model.dto.GuestbookDTO;
@Repository
public class GuestbookDAOImpl implements GuestbookDAO{
	String tableName01="guestbook";
	@Inject
	SqlSession session;
	
	@Override
	public int setInsert(GuestbookDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		
		int result = session.insert("guestbook.setInsert",map);
		return result;
	}

	@Override
	public List<GuestbookDTO> getList(int startRow, int endRow, String search_option, String search_data) {
		Map<String, Object> map = new HashMap<>();
		map.put("startRecord", startRow);
		map.put("lastRecord", endRow);
		map.put("search_option",search_option);
		map.put("search_data", search_data);
		
		List<GuestbookDTO> list = session.selectList("guestbook.getList", map);
		return list;
	}

	@Override
	public int getTotalRecord(String search_option, String search_data) {
		Map<String, Object> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search_data", search_data);
		
		int result = session.selectOne("guestbook.getTotalRecord",map);
		return result;
	}

	@Override
	public GuestbookDTO getOne(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		GuestbookDTO dto2 = session.selectOne("guestbook.getOne",map);
		return dto2;
	}

	@Override
	public int setSujung(GuestbookDTO dto2) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto2);
		
		int result = session.update("guestbook.setSujung",map);
		return result;
	}

	@Override
	public int setSakjae(GuestbookDTO dto2) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto",dto2);
		
		int result = session.update("guestbook.setSakjae",map);
		return result;
	}

}
