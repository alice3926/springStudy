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

import com.test.springStudy.member.model.dto.CartDTO;
import com.test.springStudy.shop.common.UtilProduct;
import com.test.springStudy.shop.model.dao.CartDAO;

@Controller
public class MallController {
	@Inject
	CartDAO dao;
	
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
	@RequestMapping("list.do")
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
		int totalRecord = dao.getTotalRecord();
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];
		
		List<CartDTO> list = dao.getList(startRecord, lastRecord, search_option, search_data);
		
		model.addAttribute("menu_gubun", "member_list");
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
		
		return "member/list";
	}
	@RequestMapping("view.do")
	public String member_view(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		CartDTO dto = dao.getOne(no, search_option, search_data);
		
		model.addAttribute("menu_gubun", "member_view");
		model.addAttribute("dto", dto);
		return "member/view";
	}
	
	@RequestMapping("chuga.do")
	public String member_chuga(Model model) {
		model.addAttribute("menu_gubun", "member_chuga");
		return "member/chuga";
	}
	@RequestMapping("chugaProc.do")
	public void member_chugaProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="id", defaultValue="") String id,
			@RequestParam(value="passwd", defaultValue="") String passwd,
			@RequestParam(value="passwdChk", defaultValue="") String passwdChk,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="gender", defaultValue="") String gender,
			@RequestParam(value="bornYear", defaultValue="0") int bornYear,
			@RequestParam(value="sample6_postcode", defaultValue="") String sample6_postcode,
			@RequestParam(value="sample6_address", defaultValue="") String sample6_address,
			@RequestParam(value="sample6_detailAddress", defaultValue="") String sample6_detailAddress,
			@RequestParam(value="sample6_extraAddress", defaultValue="") String sample6_extraAddress
			) {
		
		CartDTO dto = new CartDTO();
		dto.setId(id);
		dto.setPasswd(passwd);
		dto.setName(name);
		dto.setGender(gender);
		dto.setBornYear(bornYear);
		
		dto.setPostcode(sample6_postcode);
		dto.setAddress(sample6_address);
		dto.setDetailAddress(sample6_detailAddress);
		dto.setExtraAddress(sample6_extraAddress);
		
		System.out.println(dto.getId());
		
		
		int result = dao.setInsert(dto);
		System.out.println("result - :" + result);
		model.addAttribute("menu_gubun", "member_chugaProc");
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
	
	
	@RequestMapping("sujung.do")
	public String member_sujung(HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		CartDTO dto = dao.getOne(no, search_option, search_data);
		
		model.addAttribute("menu_gubun", "member_sujung");
		model.addAttribute("dto", dto);
	
		return "member/sujung";
	}
	
	@RequestMapping("sujungProc.do")
	public void member_sujungProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="id", defaultValue="") String id,
			@RequestParam(value="passwd", defaultValue="") String passwd,
			@RequestParam(value="passwdChk", defaultValue="") String passwdChk,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="gender", defaultValue="") String gender,
			@RequestParam(value="bornYear", defaultValue="0") int bornYear,
			@RequestParam(value="sample6_postcode", defaultValue="") String sample6_postcode,
			@RequestParam(value="sample6_address", defaultValue="") String sample6_address,
			@RequestParam(value="sample6_detailAddress", defaultValue="") String sample6_detailAddress,
			@RequestParam(value="sample6_extraAddress", defaultValue="") String sample6_extraAddress
			) {
		
		CartDTO dto = new CartDTO();
		dto.setId(id);
		dto.setPasswd(passwd);
		dto.setName(name);
		dto.setGender(gender);
		dto.setBornYear(bornYear);
		
		dto.setPostcode(sample6_postcode);
		dto.setAddress(sample6_address);
		dto.setDetailAddress(sample6_detailAddress);
		dto.setExtraAddress(sample6_extraAddress);
		
		System.out.println(dto.getId());
		
		
		int result = dao.setSujung(dto);
		System.out.println("result - :" + result);
		model.addAttribute("menu_gubun", "member_sujungProc");
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
	
	
	
	
	@RequestMapping("sakje.do")
	public String member_sakje(HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		CartDTO dto = dao.getOne(no, search_option, search_data);
		
		model.addAttribute("menu_gubun", "member_sakje");
		model.addAttribute("dto", dto);
	
		return "member/sakjae";
	}
	@RequestMapping("sakjeProc.do")
	public void member_sakjeProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="id", defaultValue="") String id,
			@RequestParam(value="passwd", defaultValue="") String passwd,
			@RequestParam(value="passwdChk", defaultValue="") String passwdChk,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="gender", defaultValue="") String gender,
			@RequestParam(value="bornYear", defaultValue="0") int bornYear,
			@RequestParam(value="sample6_postcode", defaultValue="") String sample6_postcode,
			@RequestParam(value="sample6_address", defaultValue="") String sample6_address,
			@RequestParam(value="sample6_detailAddress", defaultValue="") String sample6_detailAddress,
			@RequestParam(value="sample6_extraAddress", defaultValue="") String sample6_extraAddress
			) {
		
		CartDTO dto = new CartDTO();
		dto.setId(id);
		dto.setPasswd(passwd);
		dto.setName(name);
		dto.setGender(gender);
		dto.setBornYear(bornYear);
		
		dto.setPostcode(sample6_postcode);
		dto.setAddress(sample6_address);
		dto.setDetailAddress(sample6_detailAddress);
		dto.setExtraAddress(sample6_extraAddress);
		
		System.out.println(dto.getId());
		
		
		int result = dao.setSakjae(dto);
		System.out.println("result - :" + result);
		model.addAttribute("menu_gubun", "member_sakjeProc");
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
