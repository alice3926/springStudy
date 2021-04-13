package com.test.springStudy.shop.controller;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.springStudy.shop.common.UtilProduct;
import com.test.springStudy.shop.model.dao.CartDAO;
import com.test.springStudy.shop.model.dao.ProductDAO;
import com.test.springStudy.shop.model.dto.CartDTO;
import com.test.springStudy.shop.model.dto.ProductDTO;

@Controller
public class MallController {
	@Inject
	ProductDAO dao;	
	@Inject
	CartDAO cartDao;
	
	UtilProduct util = new UtilProduct();
	
	 public Map<String, Object> topInfo(
				HttpServletRequest request
				) throws UnknownHostException {
			String search_option = request.getParameter("search_option");
			String search_data = request.getParameter("search_data");
			String[] searchArray = util.searchCheck(search_option, search_data);
			search_option = searchArray[0];
			search_data = searchArray[1];
			
			Map<String, Object> map = util.basicInfo(request);
			map.put("search_option", search_option);
			map.put("search_data", search_data);
			return map;
		}
	
	
	@RequestMapping("mall/index.do")
	public String index(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
			) throws UnknownHostException {
		Map<String,Object> map = topInfo(request);
		String ip = (String) map.get("ip");
		
		String arg01 = request.getParameter("arg01");
		arg01 = util.nullCheck(arg01);
		
		model.addAttribute("menu_gubun","mall_index");
		model.addAttribute("ip",ip);
		model.addAttribute("arg01",arg01);
		return "main/main";
	}
	@RequestMapping("mall/mall_list.do")
	public String mall_list(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int pageNumber = (int) map.get("pageNumber");
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		int pageSize = 10;
		int blockSize = 10;
		int totalRecord = dao.getTotalRecord(search_option,search_data);
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];
		
		List<ProductDTO> list = dao.getList(startRecord, lastRecord, search_option, search_data);
		
		model.addAttribute("menu_gubun", "mall_list");
		model.addAttribute("list", list);
//		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("jj", jj);
		model.addAttribute("startRecord", startRecord);
		model.addAttribute("lastRecord", lastRecord);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("lastPage", lastPage);
		
		return "shop/mall/mall_list";
	}
	@RequestMapping("mall/mall_view.do")
	public String mall_view(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		ProductDTO dto = dao.getView(no);
		
		model.addAttribute("menu_gubun", "member_view");
		model.addAttribute("dto", dto);
		return "shop/mall/mall_view";
	}
	
	@RequestMapping("mall/cart_chuga.do")
	public void member_chuga(
			HttpServletResponse response,
			HttpServletRequest request,
			Model model,
			@RequestParam(value="buy_counter", defaultValue="") int buy_counter
			) throws UnknownHostException {
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		int cookNo = (int)map.get("cookNo");
		
		
		CartDTO cartDto = new CartDTO();
		cartDto.setProductNo(no);
		cartDto.setAmount(buy_counter);
		cartDto.setMemberNo(cookNo);
				
		int result = cartDao.setInsert(cartDto);
		model.addAttribute("menu_gubun", "mall_cart_chuga");
/*
		try {
  			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('정상적으로 등록되었습니다.');");
			out.println("suntaek_proc('list', '1', '');");
			out.println("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/		
	}
	@RequestMapping("mall/cart_list.do")
	public String cart_list(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int pageNumber = (int) map.get("pageNumber");
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		int pageSize = 10;
		int blockSize = 10;
		int totalRecord = cartDao.getTotalRecord();
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];
		
		List<CartDTO> list = cartDao.getList(startRecord, lastRecord);
		
		model.addAttribute("menu_gubun", "cart_list");
		model.addAttribute("list", list);
//		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("jj", jj);
		model.addAttribute("startRecord", startRecord);
		model.addAttribute("lastRecord", lastRecord);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("lastPage", lastPage);
		
		return "shop/mall/cart_list";
	}
	
	@RequestMapping("mall/cart_sujung.do")
	public void member_sujung(
			HttpServletRequest request,
			Model model,
			@RequestParam(value="buy_counter", defaultValue="") int buy_counter
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		
		cartDao.setSujung(no, buy_counter);
	}
	

	@RequestMapping("mall/cart_clear.do")
	public void member_sakjeProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="chk_no", defaultValue="") String chk_no
			) {
			String[] array = chk_no.split(",");
		  	for(int i=0; i<array.length; i++) {
		  	  System.out.println(array[i]);
		  	}
		  	cartDao.setDeleteBatch(array);
/*
		try {
  			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('정상적으로 등록되었습니다.');");
			out.println("suntaek_proc('list', '1', '');");
			out.println("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/		
	}
	
	
}
