package com.test.springStudy.shop.model.dao;

import java.util.List;

import com.test.springStudy.shop.model.dto.CartDTO;

public interface CartDAO {
	 public int setInsert(CartDTO dto);
	  public List<CartDTO> getList(int startRecord,int lastRecord);
	  public int getTotalRecord(String search_option,String search_data);
	  public void setDeleteBatch(String[] array );
	  public List<CartDTO> getListCartProductGroup();
	  public int setSujung(int no, int jumunsu);
}
