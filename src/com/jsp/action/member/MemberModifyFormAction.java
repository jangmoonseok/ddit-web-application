package com.jsp.action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.MakeFileName;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;

public class MemberModifyFormAction implements Action {

	private MemberService searchMemberService;
	
	public void setSearchMemberService(MemberService searchMemberService) {
		this.searchMemberService = searchMemberService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "/member/modify";
		
		String id = request.getParameter("id");
		
		try {
			MemberVO member = searchMemberService.getMember(id);
			
			String originalFileName = MakeFileName.parseFileNameFromUUID(member.getPicture(), "\\$\\$");
			member.setPicture(originalFileName);
			request.setAttribute("member", member);
		} catch (Exception e) {
			e.printStackTrace();
			url = "/member/detail_fail";
		}
		
		return url;
	}

}
