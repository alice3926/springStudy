package com.test.springStudy.survey.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.springStudy.guestbook.model.dao.GuestbookDAO;
import com.test.springStudy.member.model.dto.MemberDTO;
import com.test.springStudy.memo.model.dao.SurveyAnswerDAO;
import com.test.springStudy.survey.model.dao.SurveyDAO;
import com.test.springStudy.survey.model.dto.SurveyAnswerDTO;
import com.test.springStudy.survey.model.dto.SurveyDTO;
import com.test.springStudy.survey.util.SurveyUtil;

@Controller
@RequestMapping("survey/")
public class SurveyController {
	@Inject
	SurveyDAO dao;
	@Inject
	SurveyAnswerDAO saDao;
	
	SurveyUtil util = new SurveyUtil();
	public Map<String, Object> topInfo(
			HttpServletRequest request
			) throws UnknownHostException {
		String search_option = request.getParameter("search_option");
		String search_data = request.getParameter("search_data");
		String search_date_s = request.getParameter("search_date_s");
		String search_date_e = request.getParameter("search_date_e");
		String search_date_check = request.getParameter("search_date_check");
		String[] searchArray = util.searchCheck(search_option, search_data, search_date_s, search_date_e,search_date_check);
		search_option = searchArray[0];
		search_data = searchArray[1];
		search_date_s = searchArray[2];
		search_date_e = searchArray[3];
		search_date_check = searchArray[4];
		
		Map<String, Object> map = util.basicInfo(request);
		map.put("search_option", search_option);
		map.put("search_data", search_data);
		String list_gubun = util.list_gubunCheck(search_date_check);
		map.put("list_gubun",list_gubun);
		
		return map;
	}
		
	@RequestMapping("index.do")
	public String index(
		HttpServletRequest request,
		HttpServletResponse response,
		Model model
		) throws IOException {
		Map<String,Object> map = topInfo(request);
		String path = (String)map.get("path");
		int cookNo = request.getSession().getAttribute("cookNo") != null ? (int)request.getSession().getAttribute("cookNo"): 0;
		if(cookNo>0) {
			String moveword = request.getParameter("moveword");
			
			if (moveword == null || moveword.equals("")) {
				request.setAttribute("menu_gubun", "survey_index");
			} else {
				request.setAttribute("moveword", moveword);
				request.setAttribute("menu_gubun", "questionBank_index");
			}
			return "main/main";
		}else {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그인 후 사용가능.');");
			out.println("location.href='"+path+"';");
			out.println("</script>");
			return null;
		}
	}
	@RequestMapping("chuga.do")
	public String survey_chuga(Model model) {
		model.addAttribute("menu_gubun", "survey_chuga");
		return "survey/chuga";
	}
	
	@RequestMapping("chugaProc.do")
	public void survey_chugaProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="question", defaultValue="") String question,
			@RequestParam(value="ans1", defaultValue="") String ans1,
			@RequestParam(value="ans2", defaultValue="") String ans2,
			@RequestParam(value="ans3", defaultValue="") String ans3,
			@RequestParam(value="ans4", defaultValue="") String ans4,
			@RequestParam(value="status", defaultValue="0") String status,
			
			@RequestParam(value="syear", defaultValue="") String syear,
			@RequestParam(value="smonth", defaultValue="") String smonth,
			@RequestParam(value="sday", defaultValue="") String sday,
			@RequestParam(value="syear", defaultValue="") String lyear,
			@RequestParam(value="smonth", defaultValue="") String lmonth,
			@RequestParam(value="sday", defaultValue="") String lday
			) throws IOException {
		
		SurveyDTO dto = new SurveyDTO();
		String start_date_ = syear + "-" + smonth + "-" + sday + " 00:00:00.0";
		String last_date_ = lyear + "-" + lmonth + "-" + lday + " 23:59:59.9"; // 소수점 6자리까지만 됨.

		java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(start_date_);
		java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(last_date_);

		System.out.println(question + "/" + ans1 + "/" + ans2 + "/" + ans3 + "/" + ans4 + "/" + status + "/\n"
				+ start_date_ + "/" + last_date_);
		
		dto.setQuestion(question);
		dto.setAns1(ans1);
		dto.setAns2(ans2);
		dto.setAns3(ans3);
		dto.setAns4(ans4);
		dto.setStatus(status);
		dto.setStart_date(start_date);
		dto.setLast_date(last_date);
			
		int result = dao.setInsert(dto);
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
	@RequestMapping("list.do")
	public String survey_list(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int pageNumber = (int) map.get("pageNumber");
		int no = (int) map.get("no");
		String list_gubun = (String) map.get("list_gubun");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		String search_date_s = (String) map.get("search_date_s");
		String search_date_e = (String) map.get("search_date_e");
		String search_date_check = (String) map.get("search_date_check");
		
		
		int pageSize = 20;
		int blockSize = 10;
		if(search_date_s==null) {
			
		}else if (search_date_s.length() > 0 && search_date_e.length() > 0) {
			search_date_s = search_date_s + " 00:00:00.0";
			search_date_e = search_date_e + " 23:59:59.999999";
			// java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(search_date_s);
			// java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(search_date_e);
		}else {}
		int totalRecord = dao.getTotalRecord(list_gubun,search_option, search_data, search_date_s, search_date_e,
				search_date_check);
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];

		List<SurveyDTO> list = dao.getList(list_gubun, startRecord, lastRecord, search_option, search_data,
				search_date_s, search_date_e, search_date_check);
		
		model.addAttribute("menu_gubun", "survey_list");
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
		
		return "survey/list";
	}
	@RequestMapping("list2.do")
	public String survey_list2(
			HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int pageNumber = (int) map.get("pageNumber");
		int no = (int) map.get("no");
		String list_gubun = (String) map.get("list_gubun");
		String search_option = (String) map.get("search_option");
		String search_data = (String) map.get("search_data");
		String search_date_s = (String) map.get("search_date_s");
		String search_date_e = (String) map.get("search_date_e");
		String search_date_check = (String) map.get("search_date_check");
		
		
		int pageSize = 20;
		int blockSize = 10;
		if (search_date_s.length() > 0 && search_date_e.length() > 0) {
			search_date_s = search_date_s + " 00:00:00.0";
			search_date_e = search_date_e + " 23:59:59.999999";
			// java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(search_date_s);
			// java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(search_date_e);
		}
		int totalRecord = dao.getTotalRecord(list_gubun,search_option, search_data, search_date_s, search_date_e,
				search_date_check);
		int[] pagerArray = util.pager(pageSize, blockSize, totalRecord, pageNumber);
		int jj = pagerArray[0];
		int startRecord = pagerArray[1];
		int lastRecord = pagerArray[2];
		int totalPage = pagerArray[3];
		int startPage = pagerArray[4];
		int lastPage = pagerArray[5];

		List<SurveyDTO> list = dao.getList(list_gubun, startRecord, lastRecord, search_option, search_data,
				search_date_s, search_date_e, search_date_check);
		
		model.addAttribute("menu_gubun", "survey_list");
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
		
		return "survey/list2";
	}
	@RequestMapping("view.do")
	public String survey_view(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
			) throws IOException {
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		SurveyDTO dto = dao.getOne(no);
		model.addAttribute("menu_gubun", "survey_view");
		model.addAttribute("dto", dto);
		return "survey/view2";
	}
	@RequestMapping("viewProc.do")
	public String survey_viewProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="no", defaultValue="") int no,
			@RequestParam(value="answer", defaultValue="") int answer
			) throws IOException {
		
		SurveyAnswerDTO sadto = new SurveyAnswerDTO();
		sadto.setNo(no);
		sadto.setAnswer(answer);
			
		int result = dao.setInsertAnswer(sadto);
		System.out.println("result"+result);
		return "survey/index";
	}
	@RequestMapping("viewProc2.do")
	public String survey_viewProc2(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="no", defaultValue="") int no,
			@RequestParam(value="answer", defaultValue="") int answer
			) throws IOException {
		
		SurveyAnswerDTO sadto = new SurveyAnswerDTO();
		sadto.setNo(no);
		sadto.setAnswer(answer);
			
		int result = dao.setInsertAnswer(sadto);
		System.out.println("result"+result);
		return "survey/index";
	}
	@RequestMapping("saveProc.do")
	public void survey_saveProc(
			HttpServletResponse response,
			Model model,
			@RequestParam(value="answer_total", defaultValue="") String answer_total
			) throws IOException {
		String[] answer_totalArr = answer_total.split("[|]");
		System.out.println("answer_totalArr.length:" + answer_totalArr.length);

		for (int i = 0; i < answer_totalArr.length; i++) {
			String[] imsiArr = answer_totalArr[i].split(":");
			int tempNo = Integer.parseInt(imsiArr[0]);
			int tempAnswer = Integer.parseInt(imsiArr[1]);

			System.out.println("tempNo:" + tempNo + "|tempAnswer" + tempAnswer);

			SurveyAnswerDTO answerDto = new SurveyAnswerDTO();
			answerDto.setAnswer(tempAnswer);
			answerDto.setNo(tempNo);
			dao.setInsertAnswer(answerDto);
		}
	}
	@RequestMapping("sujung.do")
	public String member_sujung(HttpServletRequest request,
			Model model
			) throws UnknownHostException {
		
//		Map<String, Object> map = util.basicInfo(request);
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		System.out.println("no"+no);
		SurveyDTO dto = dao.getOne(no);

		Timestamp start_date = dto.getStart_date();
		Timestamp last_date = dto.getLast_date();
		
		String a = start_date.toString();
		String syear_ = a.substring(0, 4);
		String smonth_ = a.substring(5, 7);
		String sday_ = a.substring(8, 10);
		int syear = Integer.parseInt(syear_);
		System.out.println("syear"+syear);
		int smonth;
		if (smonth_.subSequence(0, 1).equals("0")) {
			smonth = Integer.parseInt(smonth_.substring(1,2));				
		}else {
			smonth = Integer.parseInt(smonth_);
		}
		int sday;
		if (sday_.subSequence(0, 1).equals("0")) {
			sday = Integer.parseInt(sday_.substring(1,2));				
		}else {
			sday = Integer.parseInt(sday_);
		}

		a = last_date.toString();
		String lyear_ = a.substring(0, 4);
		String lmonth_ = a.substring(5, 7);
		String lday_ = a.substring(8, 10);
		
		int lyear = Integer.parseInt(lyear_);
		int lmonth;
		if (lmonth_.subSequence(0, 1).equals("0")) {
			lmonth = Integer.parseInt(lmonth_.substring(1,2));				
		}else {
			lmonth = Integer.parseInt(lmonth_);
		}
		int lday;
		if (lday_.subSequence(0, 1).equals("0")) {
			lday = Integer.parseInt(lday_.substring(1,2));				
		}else {
			lday = Integer.parseInt(lday_);
		}
		
		
		model.addAttribute("syear", syear);
		model.addAttribute("smonth", smonth);
		model.addAttribute("sday", sday);
		model.addAttribute("lyear", lyear);
		model.addAttribute("lmonth", lmonth);
		model.addAttribute("lday", lday);
		model.addAttribute("menu_gubun", "survey_sujung");
		model.addAttribute("dto", dto);
	
		return "survey/sujung";
	}
	@RequestMapping("sujungProc.do")
	public void survey_sujungProc(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@RequestParam(value="question", defaultValue="") String question,
			@RequestParam(value="ans1", defaultValue="") String ans1,
			@RequestParam(value="ans2", defaultValue="") String ans2,
			@RequestParam(value="ans3", defaultValue="") String ans3,
			@RequestParam(value="ans4", defaultValue="") String ans4,
			@RequestParam(value="status", defaultValue="") String status,
			@RequestParam(value="syear", defaultValue="") String syear,
			@RequestParam(value="smonth", defaultValue="") String smonth,
			@RequestParam(value="sday", defaultValue="") String sday,
			@RequestParam(value="syear", defaultValue="") String lyear,
			@RequestParam(value="smonth", defaultValue="") String lmonth,
			@RequestParam(value="sday", defaultValue="") String lday			
			) throws IOException {
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		
		String start_date_ = syear + "-" + smonth + "-" + sday + " 00:00:00.0";
		String last_date_ = lyear + "-" + lmonth + "-" + lday + " 23:59:59.9"; // 소수점 6자리까지만 됨.

		java.sql.Timestamp start_date = java.sql.Timestamp.valueOf(start_date_);
		java.sql.Timestamp last_date = java.sql.Timestamp.valueOf(last_date_);

		System.out.println(question + "/" + ans1 + "/" + ans2 + "/" + ans3 + "/" + ans4 + "/" + status + "/\n"
				+ start_date_ + "/" + last_date_);
		SurveyDTO dto = new SurveyDTO();
		dto.setNo(no);
		dto.setQuestion(question);
		dto.setAns1(ans1);
		dto.setAns2(ans2);
		dto.setAns3(ans3);
		dto.setAns4(ans4);
		dto.setStatus(status);
		dto.setStart_date(start_date);
		dto.setLast_date(last_date);

		int result = dao.setSujung(dto);
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (result > 0) {
			out.println("<script>$('#span_passwd').text('T');</script>");
		} else {
			out.println("<script>$('#span_passwd').text('F');</script>");
		}
		out.flush();
		out.close();

	}
	@RequestMapping("sakjaeProc.do")
	public void survey_sakjaeProc(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model
			) throws IOException {
		Map<String, Object> map = topInfo(request);
		int no = (int) map.get("no");
		
		int result = dao.setSakjae(no);
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
	
	
	

}
