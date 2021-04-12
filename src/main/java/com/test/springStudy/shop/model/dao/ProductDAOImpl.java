package com.test.springStudy.shop.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.shop.model.dto.ProductDTO;
@Repository
public class ProductDAOImpl implements ProductDAO {
	String tableName01="product";
	@Inject
	SqlSession session;
	
	
	@Override
	public int setInsert(ProductDTO dto) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("dto", dto);
			 
		 int result = session.insert("product.setInsert",map);
		 return result; 
	}

	@Override
	public int getTotalRecord(String search_option, String search_data) {
		Map<String,String> map = new HashMap<>();
		map.put("search_option", search_option);
		map.put("search_data", search_data);
			
		int result = session.selectOne("product.getTotalRecord",map);
		return result;	
	}

	@Override
	public List<ProductDTO> getList(int startRecord, int lastRecord, String search_option, String search_data) {
		Map<String,Object> map = new HashMap<>();
		map.put("startRecord", startRecord);
		map.put("lastRecord", lastRecord);
		map.put("search_option", search_option);
		map.put("search_data", search_data);
		 
		List<ProductDTO> list = session.selectList("product.getList",map);
		return list;
	}

	@Override
	public ProductDTO getView(int no) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		
		ProductDTO dto2 = session.selectOne("product.getView",map);
		return dto2;
	}

	@Override
	public int setUpdate(ProductDTO dto) {
		Map<String,Object> map = new HashMap<>();
		map.put("dto", dto);
		
		int result = session.update("product.setUpdate",map);
		return result;
	}

	@Override
	public int setDelete(ProductDTO dto2) {
		Map<String, Object> map = new HashMap<>();
		map.put("dto", dto2);
		
		int result = session.delete("product.setDelete",map);
		return result;
	}

}
