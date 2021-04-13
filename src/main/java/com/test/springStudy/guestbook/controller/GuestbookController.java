package com.test.springStudy.guestbook.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.test.springStudy.guestbook.common.GuestbookUtil;
import com.test.springStudy.guestbook.model.dao.GuestbookDAO;
import com.test.springStudy.guestbook.model.dto.GuestbookDTO;
import com.test.springStudy.member.model.dto.MemberDTO;

@Controller
@RequestMapping("guestbook/")
public class GuestbookController {
	@Inject
	GuestbookDAO dao;
	
	GuestbookUtil util = new GuestbookUtil();
	
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
	@RequestMapping("index.do")
	public String index(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
			) throws UnknownHostException {
		Map<String,Object> map = topInfo(request);
		String ip = (String) map.get("ip");
		
		String arg01 = request.getParameter("arg01");
		arg01 = util.nullCheck(arg01);
		
		model.addAttribute("menu_gubun","guestbook_index");
		model.addAttribute("ip",ip);
		model.addAttribute("arg01",arg01);
		return "main/main";
	}
	@RequestMapping("list.do")
	public String guestbook_list(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
		Map<String, Object> map = topInfo(request);
		int pageNumber = (int) map.get("pageNumber");
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		int pageSize = 3;
		int blockSize = 10;
		int totalRecord = dao.getTotalRecord(search_option, search_data);
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];
		
		List<GuestbookDTO> list = dao.getList(startRecord, lastRecord, search_option, search_data);
		
		model.addAttribute("menu_gubun", "guestbook_list");
		model.addAttribute("list", list);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("blockSize", blockSize);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("jj", jj);
		model.addAttribute("startRecord", startRecord);
		model.addAttribute("lastRecord", lastRecord);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("lastPage", lastPage);
		
		return "guestbook/list";
	}

	
	@RequestMapping("chuga.do")
	public String guestbook_chuga(Model model) {
		model.addAttribute("menu_gubun", "guestbook_chuga");
		return "guestbook/write";
	}
	@RequestMapping("chugaProc.do")
	public void guestbook_chugaProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="passwd", defaultValue="") String passwd,
			@RequestParam(value="content", defaultValue="") String content
			) throws IOException {
		
		GuestbookDTO dto = new GuestbookDTO();
		dto.setName(name);
		dto.setEmail(email);
		dto.setPasswd(passwd);
		dto.setContent(content);
		
		int result = dao.setInsert(dto);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result>0) {
	    	  out.println("<script>$('#span_passwd').text('T');$('#span_search_option').text('');$('#span_search_data').text('');</script>");
	    }else {
	    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
	    }
	    out.flush();
	    out.close();
		model.addAttribute("menu_gubun", "guestbook_chugaProc");
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
	public String guestbook_sujung(HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		GuestbookDTO dto = dao.getOne(no);
		
		model.addAttribute("menu_gubun", "guestbook_sujung");
		model.addAttribute("dto", dto);
	
		return "guestbook/sujung";
	}
	
	@RequestMapping("sujungProc.do")
	public void guestbook_sujungProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="no", defaultValue="") int no,
			@RequestParam(value="name", defaultValue="") String name,
			@RequestParam(value="passwd", defaultValue="") String passwd,
			@RequestParam(value="email", defaultValue="") String email,
			@RequestParam(value="content", defaultValue="") String content
			) throws IOException {
		
		GuestbookDTO dto = new GuestbookDTO();
		dto.setNo(no);
		dto.setName(name);
		dto.setEmail(email);
		dto.setPasswd(passwd);
		dto.setContent(content);
		
		int result = dao.setSujung(dto);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result>0) {
	    	  out.println("<script>$('#span_passwd').text('T');</script>");
	    }else {
	    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
	    }
	    out.flush();
	    out.close();

		model.addAttribute("menu_gubun", "guestbook_sujungProc");
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
	
	
	
	
	@RequestMapping("sakjae.do")
	public String guestbook_sakjae(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		GuestbookDTO dto = dao.getOne(no);
		
		model.addAttribute("menu_gubun", "guestbook_sakjae");
		model.addAttribute("dto", dto);
	
		return "guestbook/sakjae";
	}
	
	@RequestMapping("sakjaeProc.do")
	public void guestbook_sakjaeProc(
			HttpServletResponse response,
			HttpServletRequest request,
			Model model,
			@RequestParam(value="passwd", defaultValue="") String passwd
			) throws IOException {
		System.out.println("삭제들어옴");
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		
		GuestbookDTO dto = new GuestbookDTO();
		dto.setNo(no);
		dto.setPasswd(passwd);
		
		int result = dao.setSakjae(dto);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result>0) {
  		  out.println("<script>$('#span_passwd').text('T');</script>");
		}else {
  		  out.println("<script>$('#span_passwd').text('F');</script>"); 
		}
		out.flush();
		out.close();
		//model.addAttribute("menu_gubun", "guestbook_sakjaeProc");
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
