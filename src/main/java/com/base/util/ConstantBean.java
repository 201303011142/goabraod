package com.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class ConstantBean {

    static String keyName = "/tmp/" + new Date().getTime() + (int) Math.random() * 10 + "";

    public static String get(String file_name_wm) {

        String response = "";
        //链接
        String url = "http://apicall.id-photo-verify.com/api/take_pic_wm/" + file_name_wm;
        //解析
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        //状态吗
        int statusCode = 0;
        try {
            statusCode = httpClient.executeMethod(getMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //301、302重定向
        if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
            Header locationHeader = getMethod.getResponseHeader("location");
            String location = null;
            if (locationHeader != null) {
                location = locationHeader.getValue();
                System.out.println("重定向跳转页面:" + location);
                response = get(location);//用跳转后的页面重新请求。
            } else {
                System.err.println("location is null.");
            }
        } else {
            System.out.println(getMethod.getStatusLine());
            try {
                byte[] b = getMethod.getResponseBody();
                BASE64Encoder encoder = new BASE64Encoder();
                response = encoder.encode(b);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            getMethod.releaseConnection();
        }

        String pathName = "";
        try {
            String putBase64image = new UploadImg().putBase64image(response, new Date().getTime() + (int) (Math.random() * 10) + ".jpg");
            pathName = "https://cdn.travbao.com/" + putBase64image;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pathName;
    }

    public static void post(String strURL, String params) {
        try {
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);
            out.flush();
            out.close();
            // 读取响应  
            InputStream is = connection.getInputStream();
            getFile(is, keyName);
        } catch (IOException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
    }

    public static void getFile(InputStream is, String fileName) throws IOException {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        in = new BufferedInputStream(is);
        out = new BufferedOutputStream(new FileOutputStream(fileName));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        System.out.println("保存成功ß");
        in.close();
        out.close();
    }

    //证件照文件上传
    public static String post0(String strURL, String params) {
        String str = "";
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[0];
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            str = new String(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }

    //上传图片文件
    public static JSONObject upLoadPictrue(String guVisitCou,InputStream inputStream) {
    	String PhototCode="";
    	if (guVisitCou.contains("老挝")) {
    		PhototCode = "2";
		}else {
			PhototCode = "11";
		}
        JSONObject jObject = new JSONObject();
        try {
            //		InputStream inputStream = new FileInputStream(new File("/Users/liyangguang/Downloads/timg.jpeg"));

            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();

            BASE64Encoder base64Encoder = new BASE64Encoder();
            String encode = base64Encoder.encode(data);
//			System.out.println(encode);
            //开始调用接口
            String url_strString = "http://apicall.id-photo-verify.com/api/cut_check_pic";

            //参数
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file", encode);
            jsonObject.put("spec_id", PhototCode);
            jsonObject.put("app_key", "f230e1968d3737a2c6c79ec649d249015fc33d35");
            //获取返回结果
            JSONObject post = JSON.parseObject(ConstantBean.post0(url_strString, jsonObject.toJSONString()));
            String code = post.getString("code").trim();
            jObject.put("code",code);
            if (code.equals("200")) {
                JSONObject js2 = post.getJSONObject("result");
                String file_name_wm = js2.getJSONArray("file_name_wm").getString(0);
                jObject.put("file_name_wm", file_name_wm);
                String file_name = js2.getJSONArray("file_name").getString(0);
                jObject.put("file_name", file_name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  jObject;
    }


    public static String upLoadPhoto(String file_name) {
        //路径
        String pathName = "";
        //制作连接
        String url = "http://apicall.id-photo-verify.com/api/take_cut_pic";
        //传递参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file_name", file_name);
        jsonObject.put("app_key", "f230e1968d3737a2c6c79ec649d249015fc33d35");
        //上传
        post(url, jsonObject.toJSONString());

        try {
            FileInputStream is = new FileInputStream(new File(keyName));
            byte[] data = null;
            try {
                data = new byte[is.available()];
                is.read(data);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(data);// 返回Base64编码过的字节数组字符串
            try {
                String putBase64image = new UploadImg().putBase64image(base64, new Date().getTime() + (int) (Math.random() * 10) + "");

                pathName = "https://cdn.travbao.com/" + putBase64image;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String_operation.deleteFile(keyName);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pathName;
    }

}
