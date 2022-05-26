package com.jsp.action.common;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsp.action.Action;
import com.jsp.dto.MenuVO;
import com.jsp.service.MenuService;

public class SubMenuAction implements Action {

	private MenuService menuService;
	

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = null;
		
		
		String mCode = request.getParameter("mCode");
		
		List<MenuVO> subMenuList = null;
		try {			
			
			subMenuList = menuService.getSubMenuList(mCode);
			
			//json데이터로 출력하기 위한 mapper
			ObjectMapper mapper = new ObjectMapper();
			
			//content type 설정
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			
			//출력
			out.println(mapper.writeValueAsString(subMenuList));
			
			out.close();
		
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return url;
	}

}
