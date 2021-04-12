package com.test.springStudy.memo.model.dao;

import java.util.List;

import com.test.springStudy.memo.model.dto.MemoDTO;

public interface MemoDAO {
	public int setInsert(MemoDTO dto);
	public List<MemoDTO> getList(int startRow,int endRow);
	public int getTotalRecord();
	public MemoDTO getOne(int no);
	public int setSujung(MemoDTO dto2);
	public int setSakjae(MemoDTO dto2);
}
