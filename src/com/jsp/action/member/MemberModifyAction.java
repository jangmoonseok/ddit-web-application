package com.jsp.action.member;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import com.jsp.action.Action;
import com.jsp.command.MemberModifyCommand;
import com.jsp.controller.FileUploadResolver;
import com.jsp.controller.GetUploadPath;
import com.jsp.controller.HttpRequestParameterAdapter;
import com.jsp.controller.MakeFileName;
import com.jsp.controller.MultiParameterAdapter;
import com.jsp.controller.MultipartHttpServletRequestParser;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;

public class MemberModifyAction implements Action {
	
	// 업로드 파일 환경 설정
	private static final int MEMORY_THRESHOLD = 1024 * 500; // 500KB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 1; // 1MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 2; // 2MB
	
	private MemberService searchMemberService;
	
	public void setSearchMemberService(MemberService searchMemberService) {
		this.searchMemberService = searchMemberService;
	}
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//화면
		String url = "/member/modify_success";
		try {			
			// request 파싱
			MultipartHttpServletRequestParser multi = new MultipartHttpServletRequestParser(request, MEMORY_THRESHOLD, MAX_FILE_SIZE, MAX_REQUEST_SIZE);
			
			// 수정된 정보를 담은 VO
			MemberVO modifyedMember = MultiParameterAdapter.execute(multi, MemberVO.class);
			
			// 기존 회원정보가 담긴 VO
			String id = multi.getParameter("id");
			MemberVO member = searchMemberService.getMember(id);
			String oldPicture = member.getPicture();
			
			if(!multi.getParameter("uploadPicture").equals("")) {				
				// 이미지 변경 시 기존 파일 삭제하고 업로드
				String uploadPath = GetUploadPath.getUploadPath("member.picture.upload"); // 저장 할 경로
				FileItem[] items = multi.getFileItems("picture");
				List<File> uploadFiles = FileUploadResolver.fileUpload(items, uploadPath);
				String uploadFileName = uploadFiles.get(0).getName();
				
				if(oldPicture != null) {
					File oldFile = new File(uploadPath + File.separator + oldPicture);
					if(oldFile.exists()) {
						oldFile.delete();
					}
				}
				modifyedMember.setPicture(uploadFileName);
			}else {				
				modifyedMember.setPicture(oldPicture);
			}
			
			//수정된 회원 정보 저장
			request.setAttribute("member", modifyedMember);
			
			searchMemberService.modify(modifyedMember);
			
			// 로그인 사용자 확인
			request.setAttribute("parentReload",false);

			HttpSession session = request.getSession();
			MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
			if (loginUser!=null && member.getId().equals(loginUser.getId())) {
				request.setAttribute("parentReload",true);
				session.setAttribute("loginUser", searchMemberService.getMember(member.getId()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			url = "/member/modify_fail";
		}
		
		return url;
	}

}
