package com.test.springStudy.guestbook.model.dao;

import java.util.List;

import com.test.springStudy.guestbook.model.dto.GuestbookDTO;

public interface GuestbookDAO {
	public int setInsert(GuestbookDTO dto);
	public List<GuestbookDTO> getList(int startRow,int endRow,String search_option, String search_data);
	public int getTotalRecord(String search_option, String search_data);
	public GuestbookDTO getOne(int no);	
	public int setSujung(GuestbookDTO dto2);
	public int setSakjae(GuestbookDTO dto2);
}
