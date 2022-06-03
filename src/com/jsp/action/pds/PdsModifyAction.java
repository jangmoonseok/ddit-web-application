package com.jsp.action.pds;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jsp.action.Action;
import com.jsp.command.PdsModifyCommand;
import com.jsp.controller.XSSHttpRequestParameterAdapter;
import com.jsp.dto.PdsVO;
import com.jsp.service.PdsService;

public class PdsModifyAction implements Action{

	private PdsService pdsService;
	
	public void setPdsService(PdsService pdsService) {
		this.pdsService = pdsService;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "redirect:/pds/detail.do?pno=" + request.getParameter("pno");
		
		PdsModifyCommand pdsCMD = XSSHttpRequestParameterAdapter.execute(request, PdsModifyCommand.class, true);
		PdsVO pds = pdsCMD.toPdsVO();
		pds.setContent(request.getParameter("content"));
		
		try {
			pdsService.modify(pds);
		}catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return url;
	}

}
