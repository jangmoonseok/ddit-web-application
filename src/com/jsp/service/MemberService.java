package com.jsp.service;

import java.util.List;

import com.jsp.command.Criteria;
import com.jsp.dto.MemberVO;

public interface MemberService {

	List<MemberVO> getMemberList() throws Exception;
	List<MemberVO> getMemberList(Criteria cri) throws Exception;
}
