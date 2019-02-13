package com.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.goabraod.aliyun.Client;
import com.goabraod.aliyun.Constants;
import com.goabraod.aliyun.ContentType;
import com.goabraod.aliyun.HttpHeader;
import com.goabraod.aliyun.MessageDigestUtil;
import com.goabraod.aliyun.Method;
import com.goabraod.aliyun.Request;
import com.goabraod.aliyun.Response;


/**
 * 调用api网关，通过app_key进行身份认证
 */
public class ImgByOcr {
	
	//定义变量
	
	public static final String Host0 = "https://ocrhz.market.alicloudapi.com";
	
	public static final String Path0 = "/rest/160601/ocr/ocr_passport.json";
//	
//	public static final String APP_SECRET = "1f3fc9270ecd00a836c94a76199054cf";
//	
//	public static final String APP_KEY = "24831890";
	
	public static final String Host = "https://tysbgpu.market.alicloudapi.com";
	
	public static final String Path = "/api/predict/ocr_general";
	
	public static final String APP_SECRET = "1f3fc9270ecd00a836c94a76199054cf";
	
	public static final String APP_KEY = "24831890";
	

    //自定义参与签名Header前缀（可选,默认只有"X-Ca-"开头的参与到Header签名）
    private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();

    /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static JSONObject getImgJson(String imgBase64) {
    	
    	//定义jsonObject
    	JSONObject jsonObject = null;
    	//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        Boolean is_old_format = true; 
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();
        
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Body内容
        String body = requestObj.toString();


        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body).replaceAll("[\\s*\t\n\r]", ""));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);

        Request request = new Request(Method.POST_STRING, Host, Path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        request.setStringBody(body);


        //调用服务端
        Response response=null;
		try {
			response = Client.execute(request);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        if(response.getStatusCode() != 200){
            System.out.println("Http code: " + response.getStatusCode());
            System.out.println("Http header error: " + response.getHeader("X-Ca-Error-Message"));
            System.out.println("Http body error msg: " + response.getBody());
            return null;
        }
        String res = response.getBody();
        JSONObject res_obj = JSON.parseObject(res);
        if(is_old_format) {
            JSONArray outputArray = res_obj.getJSONArray("outputs");
            String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
            jsonObject = JSON.parseObject(output);
            
        }else{
        	jsonObject = res_obj;
        }
        
		
        return jsonObject;
		
    }
    
    public static JSONObject getImgJson0(String imgBase64) {
    	
    	//定义jsonObject
    	JSONObject jsonObject = null;
    	//如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        Boolean is_old_format = true; 
        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();
        
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            if(is_old_format) {
                JSONObject obj = new JSONObject();
                obj.put("image", getParam(50, imgBase64));
                if(config_str.length() > 0) {
                    obj.put("configure", getParam(50, config_str));
                }
                JSONArray inputArray = new JSONArray();
                inputArray.add(obj);
                requestObj.put("inputs", inputArray);
            }else{
                requestObj.put("image", imgBase64);
                if(config_str.length() > 0) {
                    requestObj.put("configure", config_str);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Body内容
        String body = requestObj.toString();


        Map<String, String> headers = new HashMap<String, String>();
        //（必填）根据期望的Response内容类型设置
        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body).replaceAll("[\\s*\t\n\r]", ""));
        //（POST/PUT请求必选）请求Body内容格式
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);

        Request request = new Request(Method.POST_STRING, Host0, Path0, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
        request.setHeaders(headers);
        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
        request.setStringBody(body);


        //调用服务端
        Response response=null;
		try {
			response = Client.execute(request);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        if(response.getStatusCode() != 200){
            System.out.println("Http code: " + response.getStatusCode());
            System.out.println("Http header error: " + response.getHeader("X-Ca-Error-Message"));
            System.out.println("Http body error msg: " + response.getBody());
            return null;
        }
        String res = response.getBody();
        JSONObject res_obj = JSON.parseObject(res);
        if(is_old_format) {
            JSONArray outputArray = res_obj.getJSONArray("outputs");
            String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
            jsonObject = JSON.parseObject(output);
            
        }else{
        	jsonObject = res_obj;
        }
        return jsonObject;
    }
}
