package com.jsp.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jsp.command.Criteria;
import com.jsp.dao.MemberDAO;
import com.jsp.dao.MemberDAOImpl;
import com.jsp.datasource.OracleMybatisSqlSessionFactory;
import com.jsp.dto.MemberVO;

public class MemberServiceImpl implements MemberService {
	
	private SqlSessionFactory sqlSessionFactory = new OracleMybatisSqlSessionFactory();
	private MemberDAO memberDAO = new MemberDAOImpl();
	
	@Override
	public List<MemberVO> getMemberList() throws Exception {
		SqlSession session = sqlSessionFactory.openSession(false);
		
		List<MemberVO> memberList = null;
		try {			
			memberList = memberDAO.selectMemberList(session);
			
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		}
		
		return memberList;
	}

	@Override
	public List<MemberVO> getMemberList(Criteria cri) throws Exception {
		SqlSession session = sqlSessionFactory.openSession(false);
		
		List<MemberVO> memberList = null;
		try {			
			memberList = memberDAO.selectMemberList(session, cri);
			
			session.commit();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
			throw e;
		}
		
		return memberList;
	}

}
