package com.test.springStudy.memo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.springStudy.common.Util;
import com.test.springStudy.member.model.dto.MemberDTO;
import com.test.springStudy.memo.model.dao.MemoDAO;
import com.test.springStudy.memo.model.dto.MemoDTO;

@Controller
public class MemoController {
	@Inject
	MemoDAO memoDao;
	
	Util util = new Util();
	
	//항상 공통
    public Map<String, Object> topInfo(
			HttpServletRequest request
			) throws UnknownHostException {
		String search_option = request.getParameter("search_option");
		String search_data = request.getParameter("search_data");
		
		Map<String, Object> map = util.basicInfo(request);
		map.put("search_option", search_option);
		map.put("search_data", search_data);
		return map;
	}
	
	
	
	@RequestMapping("memo/index.do")
	public String memo_index(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
			) throws IOException {		
		Map<String,Object> map = topInfo(request);
		String path = (String)map.get("path");
		int cookNo = request.getSession().getAttribute("cookNo") != null ? (int)request.getSession().getAttribute("cookNo"): 0;
		
		if (cookNo == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 사용가능.');");
			out.println("location.href='"+path+"';");
			out.println("</script>");
			return null;
		}else {
			model.addAttribute("menu_gubun", "memo_index");
			return "main/main";
		}
			
		
	}
	
	@RequestMapping("memo/list.do")
	public String memo_list(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		Map<String,Object> map = topInfo(request);
		int pageNumber = (int)map.get("pageNumber");
		
		int pageSize = 3;
		int blockSize = 10;
		int totalRecord = memoDao.getTotalRecord();
		int[] pageArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pageArray[0];
		int startRecord = pageArray[1];
		int lastRecord = pageArray[2];
		int totalPage = pageArray[3];
		int startPage = pageArray[4];
		int lastPage = pageArray[5];
		
		List<MemoDTO> list = memoDao.getList(startRecord, lastPage);
		
		model.addAttribute("menu_gubun","memo_list");
		model.addAttribute("list",list);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("blockSize",blockSize);
		model.addAttribute("totalRecord",totalRecord);
		model.addAttribute("jj",jj);
		model.addAttribute("startRecord",startRecord);
		model.addAttribute("lastRecord",lastRecord);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("lastPage",lastPage);
		
		return "memo/list";
	}
	@RequestMapping("memo/insertProc.do")
	public void memo_insertProc(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value="writerName",defaultValue="") String writerName,
			@RequestParam(value="content",defaultValue="") String content
			) throws IOException {
		HttpSession session = request.getSession();
		String cookId = (String)session.getAttribute("cookId");
		cookId = util.nullCheck(cookId);
		
		
		MemoDTO dto = new MemoDTO();
		dto.setWriterId(cookId);
		dto.setWriterName(writerName);
		dto.setMemo(content);
		System.out.println("memo:"+dto.getMemo());
		
		int result = memoDao.setInsert(dto);
		model.addAttribute("menu_gubun","memo_insertProc");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
    	if(result>0) {
    	  out.println("<script>$('#span_passwd').text('T');</script>");
    	}else {
    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
    	}
    	out.flush();
    	out.close();
		
	}
	@RequestMapping("memo/sujung.do")
	public String memo_sujung(
			HttpServletRequest request,
			Model model,
			@RequestParam(value="no", defaultValue="") int no
			) throws UnknownHostException {
		Map<String,Object> map = topInfo(request);
		int pageNumber = (int)map.get("pageNumber");
		
		int pageSize = 3;
		int blockSize = 10;
		int totalRecord = memoDao.getTotalRecord();
		int[] pageArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pageArray[0];
		int startRecord = pageArray[1];
		int lastRecord = pageArray[2];
		int totalPage = pageArray[3];
		int startPage = pageArray[4];
		int lastPage = pageArray[5];
		
		List<MemoDTO> list = memoDao.getList(startRecord, lastPage);
		
		MemoDTO onedto = memoDao.getOne(no);
		
		model.addAttribute("onedto", onedto);
		model.addAttribute("menu_gubun","memo_list");
		model.addAttribute("list",list);
		model.addAttribute("pageSize",pageSize);
		model.addAttribute("blockSize",blockSize);
		model.addAttribute("totalRecord",totalRecord);
		model.addAttribute("jj",jj);
		model.addAttribute("startRecord",startRecord);
		model.addAttribute("lastRecord",lastRecord);
		model.addAttribute("totalPage",totalPage);
		model.addAttribute("startPage",startPage);
		model.addAttribute("lastPage",lastPage);
		
		return "memo/list";
	}
	@RequestMapping("memo/sujungProc.do")
	public void memo_sujungProc(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value="no",defaultValue="") int no,
			@RequestParam(value="writerName",defaultValue="") String writerName,
			@RequestParam(value="content",defaultValue="") String content
			) throws IOException {
//		HttpSession session = request.getSession();
//		String cookId = (String)session.getAttribute("cookId");
//		cookId = util.nullCheck(cookId);
//		
		System.out.println("수정들어옴");
		MemoDTO sujungDto = memoDao.getOne(no);
		sujungDto.setWriterName(writerName);
		sujungDto.setMemo(content);
		
		int result = memoDao.setSujung(sujungDto);
		model.addAttribute("menu_gubun","memo_sujungProc");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
    	if(result>0) {
    	  out.println("<script>$('#span_passwd').text('T');</script>");
    	}else {
    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
    	}
    	out.flush();
    	out.close();
	}
	
	@RequestMapping("memo/sakjaeProc.do")
	public void memo_sakjaeProc(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value="no",defaultValue="") int no,
			@RequestParam(value="writerName",defaultValue="") String writerName,
			@RequestParam(value="content",defaultValue="") String content
			) throws IOException {
		HttpSession session = request.getSession();
		String cookId = (String)session.getAttribute("cookId");
		cookId = util.nullCheck(cookId);
		
		System.out.println("삭제들어옴");
		MemoDTO sakjaedto = memoDao.getOne(no);
		
		int result = memoDao.setSakjae(sakjaedto);
		model.addAttribute("menu_gubun","memo_sakjaeProc");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
    	if(result>0) {
    	  out.println("<script>$('#span_passwd').text('T');</script>");
    	}else {
    	  out.println("<script>$('#span_passwd').text('F');</script>"); 
    	}
    	out.flush();
    	out.close();
	}	
			
	
}
