package com.test.springStudy.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.springStudy.member.model.dao.MemberDAO;
import com.test.springStudy.member.model.dto.MemberDTO;
import com.test.springStudy.member.util.MemberUtil;

@Controller
@RequestMapping("member/")
public class MemberController {
	@Inject
	MemberDAO memberDao; //수정할 부분
	
	MemberUtil util = new MemberUtil(); //수정할 부분
	//항상 공통
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
	public String member_index(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value="word", defaultValue="") String word
			) throws UnknownHostException {
		Map<String,Object> map = topInfo(request);
		String ip = (String) map.get("ip");
		model.addAttribute("word",word);
		model.addAttribute("menu_gubun","member_index");
		return "main/main";//WEB-INF/views생략되어 있음.
	}
	
	@RequestMapping("list.do")
	public String member_list(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int pageNumber = (int) map.get("pageNumber");
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		int pageSize = 3;
		int blockSize = 10;
		int totalRecord = memberDao.getTotalRecord(search_option, search_data);
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];
		
		List<MemberDTO> list = memberDao.getList(startRecord, lastRecord, search_option, search_data);
		
		model.addAttribute("menu_gubun", "member_list");
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
		
		return "member/list";
	}
	@RequestMapping("view.do")
	public String member_view(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
			) throws IOException {
		
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String path = (String)map.get("path");
		
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		int cookNo = request.getSession().getAttribute("cookNo") != null ? (int)request.getSession().getAttribute("cookNo"): null;
		
		if(cookNo == 51) {
			MemberDTO dto = memberDao.getOne(no, search_option, search_data);
			model.addAttribute("menu_gubun", "member_view");
			model.addAttribute("dto", dto);
			return "member/view";
		}else if (cookNo != no) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('자신의 상세정보만 접근가능.');");
			out.println("suntaek_proc('list', '1', '');");
			out.println("</script>");
			return null;
		}else{
			MemberDTO dto = memberDao.getOne(no, search_option, search_data);
			model.addAttribute("menu_gubun", "member_view");
			model.addAttribute("dto", dto);
			return "member/view";
		}
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
			@RequestParam(value="postcode", defaultValue="") String postcode,
			@RequestParam(value="address", defaultValue="") String address,
			@RequestParam(value="detailAddress", defaultValue="") String detailAddress,
			@RequestParam(value="extraAddress", defaultValue="") String extraAddress
			) throws IOException {
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPasswd(passwd);
		dto.setName(name);
		dto.setGender(gender);
		dto.setBornYear(bornYear);
		
		dto.setPostcode(postcode);
		dto.setAddress(address);
		dto.setDetailAddress(detailAddress);
		dto.setExtraAddress(extraAddress);
		
			
		int result = memberDao.setInsert(dto);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result > 0) {
			out.println("<script>$('#span_passwd').text('T');</script>");
		} else {
			out.println("<script>$('#span_passwd').text('F');</script>");
		}
		out.flush();
		out.close();
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
		
		MemberDTO dto = memberDao.getOne(no, search_option, search_data);
		
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
			@RequestParam(value="postcode", defaultValue="") String postcode,
			@RequestParam(value="address", defaultValue="") String address,
			@RequestParam(value="detailAddress", defaultValue="") String detailAddress,
			@RequestParam(value="extraAddress", defaultValue="") String extraAddress
			) throws IOException {
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPasswd(passwd);
		dto.setName(name);
		dto.setGender(gender);
		dto.setBornYear(bornYear);
		
		dto.setPostcode(postcode);
		dto.setAddress(address);
		dto.setDetailAddress(detailAddress);
		dto.setExtraAddress(extraAddress);
		
		int result = memberDao.setSujung(dto);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result > 0) {
			out.println("<script>$('#span_passwd').text('T');</script>");
		} else {
			out.println("<script>$('#span_passwd').text('F');</script>");
		}
		out.flush();
		out.close();

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
	public String member_sakjae(HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		
		MemberDTO dto = memberDao.getOne(no, search_option, search_data);
		
		model.addAttribute("menu_gubun", "member_sakjae");
		model.addAttribute("dto", dto);
	
		return "member/sakjae";
	}
	@RequestMapping("sakjaeProc.do")
	public void member_sakjaeProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="id", defaultValue="") String id,
			@RequestParam(value="passwd", defaultValue="") String passwd
			) throws IOException {
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPasswd(passwd);
		
		int result = memberDao.setSakjae(dto);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result > 0) {
			out.println("<script>$('#span_passwd').text('T');</script>");
		} else {
			out.println("<script>$('#span_passwd').text('F');</script>");
		}
		out.flush();
		out.close();
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
	@RequestMapping("/id_check.do")
	public void member_id_check(
			HttpServletResponse response,
			@RequestParam(value="id", defaultValue="") String id
			) throws IOException {
		int result = memberDao.getIdCheck(id);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		// System.out.println(result+"MC");
		out.println(result);
		out.flush();
		out.close();
	}
	@RequestMapping("/id_check_win.do")
	public String member_id_check_win(Model model){
		model.addAttribute("menu_gubun", "member_id_check_win");
		return "member/id_check";
	}
	
	
	@RequestMapping("id_check_win_Proc.do")
	public void member_id_check_win_Proc(
			HttpServletResponse response,
			@RequestParam(value="id", defaultValue="") String id
			
			) throws IOException {
		String result = memberDao.getIdCheckWin(id);
		
		if (result == null || result.equals("")) {
			result = id;
		} else {
			result = "";
		}
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		// System.out.println(result+"MC");
		out.println(result);
		out.flush();
		out.close();
	}
	@RequestMapping("/login.do")
	public String member_login(Model model){
		model.addAttribute("menu_gubun", "member_login");
		return "member/login";
	}
	@RequestMapping("/loginProc.do")
	public String loginProc(
			HttpServletRequest request,
			@RequestParam(value="id", defaultValue="") String id,
			@RequestParam(value="passwd", defaultValue="") String passwd
			) throws UnknownHostException {
		Map<String, Object> map = topInfo(request);
		String path = (String)map.get("path");
		MemberDTO dto = new MemberDTO();
		
		dto.setId(id);
		dto.setPasswd(passwd);
		
		MemberDTO resultDto = memberDao.setlogin(dto);
		
		String temp = "";
		
		if (resultDto == null) {
			temp = "index.do?word=login";
		} else if (resultDto.getNo() == 0) { // 실패
			temp = "index.do?word=login";
		} else { // 성공
			// 세션 등록
			HttpSession session = request.getSession();
			
			session.setAttribute("cookNo", resultDto.getNo());
			session.setAttribute("cookId", resultDto.getId());
			session.setAttribute("cookName", resultDto.getName());
			temp = "index.do";
		}

		return "redirect:" + temp;
	}
	@RequestMapping("/logout.do")
	public void member_logout(
			HttpServletRequest request,
			HttpServletResponse response
			) throws IOException {
		// 세션 해제
		HttpSession session = request.getSession();
		session.invalidate();
		
		Map<String, Object> map = topInfo(request);
		String path = (String)map.get("path");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다.');");
		out.println("location.href='" + path + "';");
		out.println("</script>");
	}
	
	
}
