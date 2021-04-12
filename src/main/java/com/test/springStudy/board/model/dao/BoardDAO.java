package com.test.springStudy.board.model.dao;

import java.util.List;

import com.test.springStudy.board.model.dto.BoardDTO;
import com.test.springStudy.board.model.dto.CommentDTO;

public interface BoardDAO {
	public int getMaxNum();
	 public int getMaxRefNo();
	 public int getMaxNoticeNo(String tbl);
	 public int setInsert(BoardDTO dto);
	 public int getTotalRecord(String tbl, String search_option, String search_data);
	 public List<BoardDTO> getList(int startRecord,int lastRecord, String tbl, String search_option, String search_data);

	public BoardDTO getView(int no);
	public void setUpdatHit(int no);
	public void setUpdateReLevel(BoardDTO dto);
	public int setUpdate(BoardDTO dto);
	public int setDelete(BoardDTO dto);
		
//Comment관련
	public int setCommmentInsert(CommentDTO dto);
	public List<CommentDTO> getCommentList(int board_no,int startRow,int lastRow);
	public int commentTotalRecord(int board_no); ;
	public CommentDTO getCommmentView(int comment_no); 
	public int setCommmentDelete(int comment_no);
	public int setCommmentUpdate(CommentDTO dto); 
	//tblChk관련
	public int tblCheck(String tbl);
}
