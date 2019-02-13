package com.base.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Iterator;

import org.jsoup.HttpStatusException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SaveHtml {

	 /**
     * 
     * Description: save String to file
     * @param filepath
     * file path which need to be saved
     * @param str
     * Method 鍒╃敤鏂伴椈鐨勬鏂囪繛鎺� 灏嗗叾瀛樺偍鎴恏tml鏍煎紡鐨勬枃妗�
     */
    public static void saveHtml(String filepath, String str){
        
        try {
            OutputStreamWriter outs = new OutputStreamWriter(new FileOutputStream(filepath, true), "gb2312");
            outs.write(str);
            outs.close();
        } catch (IOException e) {
            System.out.println("Error at save html...");
            e.printStackTrace();
        }
    }

    public static String InputStream2String(InputStream in_st,String charset) throws IOException{
        BufferedReader buff = new BufferedReader(new InputStreamReader(in_st, charset));
        StringBuffer res = new StringBuffer();
        String line = "";
        while((line = buff.readLine()) != null){
            res.append(line);
        }
        return res.toString();
    }
    
    public static boolean isValid(String strLink) {

	       URL url;

	       try {

	              url = new URL(strLink);

	              HttpURLConnection connt = (HttpURLConnection)url.openConnection();

	              connt.setRequestMethod("HEAD");

	              String strMessage = connt.getResponseMessage();

	              if (strMessage.compareTo("Not Found") == 0) {

	                     return false;

	              }

	              connt.disconnect();

	       	} catch (Exception e) {

	              return false;

	       	}

	       return true;

		}
    
	public static String getUrlString(String url_str,String charset){
	    //定义编码格式
        int sec_cont = 1000;
 
        url_str = String.format(url_str, 1, (new Date()).getTime());
        	
    	
			URL url=null;
			try {
				url = new URL(url_str);
			} catch (MalformedURLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			URLConnection url_con=null;
			try {
				url_con = url.openConnection();
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			url_con.setDoOutput(true);
			url_con.setReadTimeout(10 * sec_cont);
			url_con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			
			InputStream htm_in =null;
			try{
				htm_in = url_con.getInputStream();
			}catch(ConnectException e){
				System.out.println(htm_in);
			}catch (SocketTimeoutException e) {
				System.out.println(htm_in);
			}catch(HttpStatusException e){
				System.out.println("HttpStatusException:"+htm_in);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			String htm_str = "";
			try{
				htm_str = SaveHtml.InputStream2String(htm_in,charset);
			}catch(NullPointerException e){
				System.out.println(htm_in);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			return htm_str;
	} 
	
	public static JSONObject getWeatherInfo(String id){
		JSONObject jsObject = new JSONObject();
		//获取实时天气
		String str = "http://www.worldweather.cn/zh/json/present.xml";
		String urlString = SaveHtml.getUrlString(str, "UTF-8");
		try {
			//转化
			JSONObject parseObject = JSON.parseObject(urlString);
			JSONObject ite = parseObject.getJSONObject("present");
			Iterator<String> iterator = ite.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				//获取
				JSONObject jsonObject = ite.getJSONObject(key);
				//判断
				if (jsonObject.getString("cityId").equals(id)) {
					//获取天气 温度
					String weather = jsonObject.getString("wd");
					String temp = jsonObject.getString("temp");
					//放入
					jsObject.put("temp", temp);
					jsObject.put("weather", weather);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			jsObject.put("error", "异常");
		}
		return jsObject;
	}
}
