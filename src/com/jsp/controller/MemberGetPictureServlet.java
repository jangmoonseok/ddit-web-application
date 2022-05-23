package com.jsp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
import com.jsp.service.SearchMemberServiceImpl;

@WebServlet("/member/getPicture")
public class MemberGetPictureServlet extends HttpServlet {
	
	private MemberService memberService = new SearchMemberServiceImpl();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;
		
		String id = request.getParameter("id");
		
		
		MemberVO member;
		try {
			member = memberService.getMember(id);
			String fileName = member.getPicture();
			String savedPath = GetUploadPath.getUploadPath("member.picture.upload");
			
			FileDownloadResolver.sendFile(fileName, savedPath, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(response.SC_INTERNAL_SERVER_ERROR);
		}
		

	}

}
