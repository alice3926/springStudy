package com.test.springStudy.shop.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.test.springStudy.shop.model.dto.CartDTO;
@Repository
public class CartDAOImpl implements CartDAO{
	String table_1 = "cart";
	@Inject
	SqlSession sqlSession;
	
	@Override
	public int setInsert(CartDTO dto) {
		Map<String,Object> map = new HashMap<>();
		map.put("dto", dto);
		 
		int result = sqlSession.insert("mall.setInsert",map);
		return result;
	}

	@Override
	public List<CartDTO> getList(int startRecord, int lastRecord) {
		 Map<String,Object> map = new HashMap<>();
		 map.put("startRecord", startRecord);
		 map.put("lastRecord", lastRecord);
		 
		 List<CartDTO> list = sqlSession.selectList("mall.getList",map);
		 return list;
	}

	@Override
	public int getTotalRecord() {
		int result = sqlSession.selectOne("mall.getTotalRecord");
		return result;
	}

	@Override
	public void setDeleteBatch(String[] array) {
		Map<String, Object> map = new HashMap<>();
	  	map.put("array", array);
	  	
	  	sqlSession.delete("mall.setDeleteBatch",map);
		
	}

	@Override
	public List<CartDTO> getListCartProductGroup() {
		List<CartDTO> list = sqlSession.selectList("mall.getListCartProductGroup");//네임스페이스...
		return list;
	}

	@Override
	public int setSujung(int no, int jumunsu) {
		 Map<String, Object> map = new HashMap<>();
		 map.put("no", no);
		 map.put("jumunsu", jumunsu);
		  
		 int result = sqlSession.update("mall.setSujung",map);
		 return result;
	}

}
