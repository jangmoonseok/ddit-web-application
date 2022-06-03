package com.jsp.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;

import com.josephoconnell.html.HTMLInputFilter;
import com.jsp.exception.NotMultipartFormDataException;

public class XSSMultipartHttpServletRequestParser extends MultipartHttpServletRequestParser {

	public XSSMultipartHttpServletRequestParser(HttpServletRequest request, int memory_threshold, int max_file_size,
													int max_request_size) throws NotMultipartFormDataException,
																			UnsupportedEncodingException, FileUploadException {
		super(request, memory_threshold, max_file_size, max_request_size);
	}
	
	public String getXSSParameter(String paramName) {
		String result = HTMLInputFilter.htmlSpecialChars(getParameter(paramName));
		return result;
	}
	
	public String[] getXSSParameterValues(String paramName) {
		String[] paramValues = new String[getParameterValues(paramName).length];
		
		for(int i = 0 ; i < paramValues.length; i++) {
			paramValues[i] = HTMLInputFilter.htmlSpecialChars(getParameterValues(paramName)[i]);
		}
		
		return paramValues;
	}

}
