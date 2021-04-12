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
import org.springframework.web.bind.annotation.RequestParam;

import com.test.springStudy.common.Util;
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
		
		String cookId = 
				request.getSession()
				.getAttribute("cookId") != null
				?(String)request.getSession()
						.getAttribute("cookId"): null;
		
		String ip = (String)map.get("ip");
		
		String arg01 = request.getParameter("arg01");
		arg01 = util.nullCheck(arg01);
		
		String menu_gubun = "memo_index";
		if(cookId == null) menu_gubun = "member_index";
		
		model.addAttribute("menu_gubun",menu_gubun);
		model.addAttribute("ip",ip);
		model.addAttribute("arg01",arg01);
			
		return "main/main";
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
	@RequestMapping("sujungProc.do")
	public void memo_sujungProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="cookId", defaultValue="") String cookId,
			@RequestParam(value="cookNo", defaultValue="") int cookNo,
			@RequestParam(value="oneno", defaultValue="") int oneno,
			@RequestParam(value="writerName", defaultValue="") String writerName,
			@RequestParam(value="memo", defaultValue="") String memo
			
			) throws IOException {
		MemoDTO sujungdto = memoDao.getOne(oneno);
		sujungdto.setWriterName(writerName);
		sujungdto.setMemo(memo);
		
		if (cookId != sujungdto.getWriterId()) {
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('본인의 메모만 수정할 수 있습니다.')");
			out.println("location.href='memo/index';");
			out.println("</script>");
		}
		if (cookId == sujungdto.getWriterId()|| cookNo == 51) {
			
			int result = memoDao.setSujung(sujungdto);
			System.out.println("result:"+result);
			model.addAttribute("menu_gubun", "member_sujungProc");
			
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
	
	@RequestMapping("sakjaeProc.do")
	public void memo_sakjaeProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="cookId", defaultValue="") String cookId,
			@RequestParam(value="cookNo", defaultValue="") int cookNo,
			@RequestParam(value="oneno", defaultValue="") int oneno,
			@RequestParam(value="writerName", defaultValue="") String writerName,
			@RequestParam(value="memo", defaultValue="") String memo
			
			) throws IOException {
		MemoDTO sakjaedto = memoDao.getOne(oneno);
				
		if (cookId != sakjaedto.getWriterId()) {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('본인의 메모만 삭제할 수 있습니다.')");
			out.println("location.href='memo/index';");
			out.println("</script>");
		}
		if (cookId == sakjaedto.getWriterId()|| cookNo == 51) {
			
			int result = memoDao.setSakjae(sakjaedto);
			System.out.println("result:"+result);
			model.addAttribute("menu_gubun", "member_sujungProc");
			
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
	
}
