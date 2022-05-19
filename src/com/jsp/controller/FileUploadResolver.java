package com.jsp.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class FileUploadResolver {
	
	public static List<File> fileUpload(FileItem[] items, String uploadPath) throws Exception{
		List<File> uploadFileList = new ArrayList<File>();
		
		File file = new File(uploadPath);
		file.mkdirs();
		
		if(items != null) {
			for(FileItem item : items) {
				// FileItem.getName()은 파일 경로까지 다 나오기 때문에 File객체로 다시 만들어서
				// getName()호출하면 파일명만 나온다.
				String fileName = new File(item.getName()).getName();
				// 고유 파일명 만들기
				fileName = MakeFileName.toUUIDFileName(fileName, "$$");
				
				String filePath = uploadPath + File.separator + fileName;
				File storeFile = new File(filePath);
				
				// local HDD에 저장
				try {					
					item.write(storeFile);
				}catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			
				uploadFileList.add(storeFile);
			}
		}
		return uploadFileList;
	}
}
