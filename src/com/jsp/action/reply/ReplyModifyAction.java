package com.jsp.action.reply;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josephoconnell.html.HTMLInputFilter;
import com.jsp.action.Action;
import com.jsp.dto.ReplyVO;
import com.jsp.service.ReplyService;

public class ReplyModifyAction implements Action{

	private ReplyService replyService;
	
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = null;
		
		ObjectMapper mapper = new ObjectMapper();
		
		ReplyVO reply = mapper.readValue(request.getReader(), ReplyVO.class);
		reply.setReplytext(HTMLInputFilter.htmlSpecialChars(reply.getReplytext()));
		try {			
			replyService.modifyReply(reply);
		}catch(SQLException e) {
			e.printStackTrace();
			response.sendError(response.SC_INTERNAL_SERVER_ERROR);
		}
		
		
		return url;
	}

}
