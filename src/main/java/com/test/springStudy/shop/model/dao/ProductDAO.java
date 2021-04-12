package com.test.springStudy.shop.model.dao;

import java.util.List;

import com.test.springStudy.shop.model.dto.ProductDTO;

public interface ProductDAO {
	public int setInsert(ProductDTO dto);
	  
	public int getTotalRecord(String search_option, String search_data);
	public List<ProductDTO> getList(int startRecord,int lastRecord, String search_option, String search_data);
	public ProductDTO getView(int no);
	public int setUpdate(ProductDTO dto);
	public int setDelete(ProductDTO dto2);
}
