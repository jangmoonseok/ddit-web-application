package com.jsp.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jsp.datasource.OracleMybatisSqlSessionFactory;
import com.jsp.dto.MemberVO;

public class TestMemberDaoImpl {

	private SqlSessionFactory factory = new OracleMybatisSqlSessionFactory();
	
	private SqlSession session;
	
	private MemberDAO memberDao = new MemberDAOImpl();
	@Before
	public void init() throws Exception{
		session = factory.openSession();
	}
	
	@After
	public void close() throws Exception{
		if(session != null) session.close();
	}
	
	@Test
	public void selectMemberListTest() throws Exception{
		List<MemberVO> selectMemberList = memberDao.selectMemberList(session);
		
		Assert.assertEquals(7, selectMemberList.size());
	}
}
