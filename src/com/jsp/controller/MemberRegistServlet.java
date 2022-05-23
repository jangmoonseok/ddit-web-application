package com.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.command.MemberRegistCommand;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.service.SearchMemberServiceImpl;


@WebServlet("/member/regist")
public class MemberRegistServlet extends HttpServlet {
       
	private MemberService memberService = new SearchMemberServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url  = "/WEB-INF/views/member/regist.jsp";
		
		request.getRequestDispatcher(url).forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//화면
		String url = request.getContextPath() + "/member/list";
		
		try {
			//입력
			MemberRegistCommand command = 
					HttpRequestParameterAdapter.execute(request, MemberRegistCommand.class);
			MemberVO member = command.toMemberVO();
			
			//처리
			memberService.regist(member);
			
			//출력
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('등록 되었습니다.')");
			out.println("window.close();");
			out.println("window.opener.location.reload()");
			out.println("</script>");
//			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
			//exception처리...
		}
	}

}
