package com.jsp.action.member;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.controller.GetUploadPath;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;

public class MemberRemoveAction implements Action {

	private MemberService searchMemberService;
	
	public void setSearchMemberService(MemberService searchMemberService) {
		this.searchMemberService = searchMemberService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "/member/remove_success";
		
		String id = request.getParameter("id");
		
		try {			
			//기존 사진 파일 삭제
			MemberVO member = searchMemberService.getMember(id);
			String picture = member.getPicture();
			
			String uploadPath = GetUploadPath.getUploadPath("member.picture.upload");
			
			if(picture != null) {
				File oldFile = new File(uploadPath + File.separator + picture);
				if(oldFile.exists()) {
					oldFile.delete();
				}
			}
			
			//DB에서 회원정보 삭제
			searchMemberService.remove(id);
		}catch(Exception e) {
			e.printStackTrace();
			url = "member/remove_fail";
		}
		
		
		
		return url;
	}

}
