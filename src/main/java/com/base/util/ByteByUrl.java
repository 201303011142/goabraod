package com.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sun.misc.BASE64Encoder;

public class ByteByUrl {

	public static byte[] getImageFromURL(String urlPath) { 
        byte[] data = null; 
        InputStream is = null; 
        HttpURLConnection conn = null; 
        try { 
            URL url = new URL(urlPath); 
            conn = (HttpURLConnection) url.openConnection(); 
            conn.setDoInput(true); 
            // conn.setDoOutput(true); 
            conn.setRequestMethod("GET"); 
            conn.setConnectTimeout(6000); 
            is = conn.getInputStream(); 
            if (conn.getResponseCode() == 200) { 
                data = readInputStream(is); 
            } else{ 
                data=null; 
            } 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                if(is != null){ 
                    is.close(); 
                }                
            } catch (IOException e) { 
                e.printStackTrace(); 
            } 
            conn.disconnect();           
        } 
        return data; 
    } 
 
 
     /**
  * 读取InputStream数据，转为byte[]数据类型
  * @param is
  *            InputStream数据
  * @return 返回byte[]数据
  */
 
public static byte[] readInputStream(InputStream is) { 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        byte[] buffer = new byte[1024]; 
        int length = -1; 
        try { 
            while ((length = is.read(buffer)) != -1) { 
                baos.write(buffer, 0, length); 
            } 
            baos.flush(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        byte[] data = baos.toByteArray(); 
        try { 
            is.close(); 
            baos.close(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return data; 
    }

	public static String GetImageStr(InputStream inputStream) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	
	    byte[] data = null;
	    // 读取图片字节数组
	    try {
	    	//输入流
	//        InputStream in = new FileInputStream(imgFilePath);
	        //数据
	        data = new byte[inputStream.available()];
	        //读取
	        inputStream.read(data);
	        //关闭
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    // 对字节数组Base64编码
	    BASE64Encoder encoder = new BASE64Encoder();
	    return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
}
