package com.goabraod.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import com.base.page.Page;
import com.base.util.ApplicationException;
import com.base.util.String_operation;
import com.google.common.base.Strings;

public class BaseController {
	
	protected Page page=null;
	
	
	/**
	 * 检测参数值是否为空,为空自动扔异常.
	 */
	public void validateNullException(String value, String errorMsg) throws ApplicationException {
		if (Strings.isNullOrEmpty(value)) { // 值为空
			throw new ApplicationException(errorMsg);
			
		}
	}
	
	/**
	 * 检测参数值是否为空,为空自动扔异常.
	 */
	public void validateObjectNullException(Object object, String errorMsg) throws ApplicationException {
		if (object==null) {
			throw new ApplicationException(errorMsg);
		}
	}
	
	public void setCrossHeader(HttpServletResponse response) {
		
		 response.setHeader("Access-Control-Allow-Origin", "*");   
		 response.setContentType("application/json;charset=utf-8");
		 response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS, DELETE");
		 response.setHeader("Access-Control-Allow-Headers","DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type, Accept-Language, Origin, Accept-Encoding");
		 
//		response.setHeader("Access-Control-Allow-Headers", "content-type, accept");
//		response.setHeader("Access-Control-Allow-Methods", "POST");
//		response.setStatus(200);
//		response.setContentType("application/json;charset=utf-8");
//		response.setCharacterEncoding("utf-8");
	}
	
    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response,  String newPath, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            
            
            OutputStream outputStream = response.getOutputStream();
    		File filel = new File(newPath);
			InputStream inputStream = new FileInputStream(filel);
//    		int b = 0;   
//            byte[] buffer = new byte[1024];   
//            while (b != -1){   
//                b = inputStream.read(buffer);   
//                //4.写到输出流(out)中   
//                outputStream.write(buffer,0,b);   
//            }   
            
            byte[] buffer = new byte[1204];
            int bytesum = 0;
            int byteread = 0;
            while ((byteread = inputStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
              //4.写到输出流(out)中   
                outputStream.write(buffer, 0, byteread);
            }
    		//
            try {
            	inputStream.close();
                if(outputStream != null){
                	outputStream.flush();
                	outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    		String_operation.deleteFile(newPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
