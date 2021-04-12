package com.test.springStudy.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.board.model.dto.BoardDTO;
import com.test.springStudy.board.model.dto.CommentDTO;

@Repository
public class BoardDAOImpl implements BoardDAO{
	String tableName01="board";
	String tableName02="board_comment";
	@Inject
	SqlSession session;
	@Override
	public int getMaxNum() {
		int result =  session.selectOne("board.getMaxNum");
		return result;
	}
	@Override
	public int getMaxRefNo() {
		int result =  session.selectOne("board.getMaxRefNo");
		return result;
	}
	@Override
	public int getMaxNoticeNo(String tbl) {
		int result =  session.selectOne("board.getMaxNoticeNo",tbl);
		 return result;
	}
	@Override
	public int setInsert(BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		  
		int result = session.insert("board.setInsert",map);
		return result;
	}
	@Override
	public int getTotalRecord(String tbl, String search_option, String search_data) {
		Map<String, Object> map = new HashMap<>();
		map.put("tbl", tbl);
		map.put("search_option", search_option);
		map.put("search_data",search_data);
	
		int result = session.selectOne("board.getTotalRecord",map);
		return result;
	}
	@Override
	public List<BoardDTO> getList(int startRecord, int lastRecord, String tbl, String search_option,
			String search_data) {
		Map<String, Object> map = new HashMap<>();
		map.put("startRecord",startRecord);
		map.put("lastRecord",lastRecord);
		map.put("tbl",tbl);
		map.put("search_option",search_option);
		map.put("search_data",search_data);

		List<BoardDTO> list = session.selectList("board.getList",map);
		return list;
	}
	@Override
	public BoardDTO getView(int no) {
		BoardDTO dto = session.selectOne("board.getView",no);
		return dto;
	}
	@Override
	public void setUpdatHit(int no) {
		session.update("board.setUpdatHit",no);
		
	}
	@Override
	public void setUpdateReLevel(BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto",dto);
		
		session.update("board.setUpdateReLevel",dto);
		
	}
	@Override
	public int setUpdate(BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		int result = session.update("board.setUpdate",map);
		return result;
	}
	@Override
	public int setDelete(BoardDTO dto) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto);
		
		
		int result = session.delete("board.setDelete",map);
		return result;
	}
	@Override
	public int setCommmentInsert(CommentDTO dto) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("dto", dto);
		 
		 int result = session.insert("board.setCommmentInsert",map);
		 return result;
	}
	@Override
	public List<CommentDTO> getCommentList(int board_no, int startRow, int lastRow) {
		Map<String,Object> map = new HashMap<>();
		map.put("board_no", board_no);
		map.put("startRow", startRow);
		map.put("lastRow", lastRow);

		List<CommentDTO> list = session.selectList("board.getCommentList",map);
		return list;
	}
	@Override
	public int commentTotalRecord(int board_no) {
		 int result = session.selectOne("board.commentTotalRecord",board_no);
		  return result;
	}
	@Override
	public CommentDTO getCommmentView(int comment_no) {
		CommentDTO commentDto = session.selectOne("board.getCommmentView",comment_no);
		return commentDto;
	}
	@Override
	public int setCommmentDelete(int comment_no) {
		int result = session.delete("board.setCommmentDelete",comment_no);
		return result;

	}
	@Override
	public int setCommmentUpdate(CommentDTO dto) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("dto", dto);
		
		 int result = session.update("board.setCommmentUpdate",map);
		 return result;
	}
	@Override
	public int tblCheck(String tbl) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("tbl", tbl);
		  
		 int result = session.selectOne("board.tblCheck",map);
		 return result;
	}
	
	
	
	
}
