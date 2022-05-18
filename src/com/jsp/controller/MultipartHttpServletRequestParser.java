package com.jsp.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.jsp.exception.NotMultipartFormDataException;

public class MultipartHttpServletRequestParser {

	private Map<String, List<String>> paramString = new HashMap<String, List<String>>();
	private Map<String, List<FileItem>> paramFile = new HashMap<String, List<FileItem>>();

	public MultipartHttpServletRequestParser(HttpServletRequest request, int memory_threshold, int max_file_size, int max_request_size)
		throws NotMultipartFormDataException,UnsupportedEncodingException,FileUploadException{
		
		// request 파일 첨부 여부 확인.
		if(!ServletFileUpload.isMultipartContent(request)) {
			throw new NotMultipartFormDataException();
		}
		
		ServletFileUpload upload = ServletFileUploadBuilder.build(memory_threshold, max_file_size, max_request_size);
		
		List<FileItem> formItems = upload.parseRequest(request);
		if(formItems != null) for(FileItem item : formItems) {
			String paramName = item.getFieldName();
			
			if(item.isFormField()) {
				// text
				List<String> paramValueList = this.paramString.get(paramName);
				if(paramValueList == null) {
					paramValueList = new ArrayList<String>();
					this.paramString.put(paramName, paramValueList);
				}
				
				paramValueList.add(item.getString("utf-8"));
			}else {
				// file
				List<FileItem> files = this.paramFile.get(paramName);
				
				if(files == null) {
					files = new ArrayList<FileItem>();
					this.paramFile.put(paramName, files);
				}
				
				files.add(item);
			}
		}
	}
	
	public String getParameter(String paramName) {
		List<String> paramValueList = paramString.get(paramName);
		
		String paramValue = paramValueList.get(0);
		
		return paramValue;
	}
	
	public String[] getParameterValues(String paramName) {
		List<String> paramValueList = paramString.get(paramName);
		
		String[] paramValueArray = null;
		if(paramValueList != null) {
			paramValueArray = new String[paramValueList.size()];
			paramValueList.toArray(paramValueArray);
		}
		return paramValueArray;
	}
	
	public FileItem getFileItem(String paramName) {
		return null;
	}
	
	public FileItem[] getFileItems(String paramName) {
		return null;
	}
	
	public Enumeration<String> getParameterNames(){
		return null;
	}
}
