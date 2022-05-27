package com.jsp.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.Criteria;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;

public interface MemberDAO {
	// 회원 리스트 조회
	List<MemberVO> selectMemberList(SqlSession session) throws Exception;
	List<MemberVO> selectMemberList(SqlSession session, Criteria cri) throws Exception;
	
	
	// 일반 리스트 전체 개수
	int selectMemberListCount(SqlSession session) throws Exception;
	// 회원검색
	MemberVO selectMemberById(SqlSession session, String id) throws SQLException;
	// 회원추가
	void insertMember(SqlSession session, MemberVO memberVO) throws SQLException;
	// 회원정보수정
	void updateMember(SqlSession session, MemberVO memberVO) throws SQLException;
	// 회원삭제
	void deleteMember(SqlSession session, String id) throws SQLException;
	// 회원상태수정
	void enabledMember(SqlSession session, String id, int enable) throws Exception;
}
