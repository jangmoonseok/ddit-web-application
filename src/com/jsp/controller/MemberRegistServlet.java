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
       
	private MemberService memberService;// = new SearchMemberServiceImpl();
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url  = "/member/regist";
		
		request.setAttribute("viewName", url);
		InternalViewResolver.view(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//화면
		String url = "/member/regist_success";
		
		//입력
		try {
			request.setCharacterEncoding("utf-8");
			
			MemberRegistCommand command = 
					HttpRequestParameterAdapter.execute(request, MemberRegistCommand.class);
			MemberVO member = command.toMemberVO();
			
			//처리
			memberService.regist(member);

		} catch (Exception e) {
			e.printStackTrace();
			//exception처리...
			url="/member/regist_fail";
		}
		
		request.setAttribute("viewName", url);
		InternalViewResolver.view(request, response);
	}

}
