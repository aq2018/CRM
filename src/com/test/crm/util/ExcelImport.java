package com.test.crm.util;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ExcelImport {
	public static String imports(HttpServletRequest request) {
		ServletContext application = request.getServletContext();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD);
		String tmpPath = application.getRealPath("tmp");
		factory.setRepository(new File(tmpPath));
		
		ServletFileUpload fileUpload = new ServletFileUpload(factory);//需要格式化工厂
		String filePath = null;
		try {
			//解析request请求
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			for (FileItem fileItem : fileItems){
				if(fileItem.isFormField()){
					//是一个比普通的form表单
					String fileName = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");
				}else{
					//是一个文件
					String fileName = fileItem.getName();
					filePath = application.getRealPath("file") + "/"+fileName;//注意/结尾
					fileItem.write(new File(filePath));
					
				}
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//D:\apache-tomcat-9.0.0.M10\webapps\fileupload\file/acts2.xls
		return filePath.replaceAll("\\\\", "/");
	}
}
