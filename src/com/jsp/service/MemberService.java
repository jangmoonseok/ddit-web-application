package com.jsp.service;

import java.util.List;
import java.util.Map;

import com.jsp.command.Criteria;
import com.jsp.dto.MemberVO;

public interface MemberService {

	// 회원 검색
	List<MemberVO> getMemberList() throws Exception;
	List<MemberVO> getMemberList(Criteria cri) throws Exception;
	
	public Map<String, Object> getMemberListForPage(Criteria cri) throws Exception;
	
	// 회원 상세
	public MemberVO getMember(String id) throws Exception;
	
	// 회원 등록
	public void regist(MemberVO member) throws Exception;
	
	// 회원 수정
	public void modify(MemberVO member) throws Exception;
	
	// 회원 탈퇴
	public void remove(String id) throws Exception;
	
	// 회원 상태 변경
	public void enabled(String id, int enabled) throws Exception;
	
}
