package com.jsp.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONResolver {

	public static void view(HttpServletResponse response, Object target) throws Exception{
		//출력
		ObjectMapper mapper = new ObjectMapper();
		
		//contentType 결정
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		//내보내기
		out.print(mapper.writeValueAsString(target));
		out.close();
	}
}
