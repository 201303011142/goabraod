package com.goabraod.alipay;

public class AlipayConfig {

		//↓↓↓↓↓↓↓↓↓↓配置基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	    //合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	    public static String partner = "";

	    //商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	    public static String private_key = "";

	    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	    public static String alipay_public_key  = "";

	    // 签名方式
	    public static String sign_type = "RSA2";

	    // 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	    public static String log_path ="/tmp/log/";

	    // 字符编码格式 目前支持 gbk 或 utf-8
	    public static String input_charset = "UTF-8";

	    // 接收通知的接口名
	    public static String service = "https://www.travbao.com/goabraod/alipay/callbacks.do";
	    //public static String service = "mobile.securitypay.pay";

	    //APPID
	    public static String app_id="2018031002349117";
	    
	    //支付接口
	    public static String pay_url = "https://openapi.alipay.com/gateway.do";
	    
	    //请求格式
	    public static String request_format = "json";

		
}
