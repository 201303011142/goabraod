package com.base.util;

import com.goabraod.aliyun.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class String_operation {

	/**
	 * method 对string进行分词
	 * @param string
	 * @return
	 */
	public static String printAnalyzer(String string){
		
		

		@SuppressWarnings("resource")
		IKAnalyzer ikAnalyzer = new IKAnalyzer();
		ikAnalyzer.setUseSmart(true);
		
		TokenStream tokenStream = ikAnalyzer.tokenStream("context", new StringReader(string));
		tokenStream.addAttribute(CharTermAttribute.class);
		
		String attribute_str = "";
		try {
			while(tokenStream.incrementToken()){
				CharTermAttribute attribute = tokenStream.getAttribute(CharTermAttribute.class);
				attribute_str += attribute.toString()+",";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attribute_str;
	}
	
	/**
     * Method 瀵规柊闂荤殑姝ｆ枃鏍囬涓殑鐗规畩瀛楃杩涜澶勭悊鍚庝綔涓篽tml鏂囦欢鐨勬枃浠跺悕
     * @param text
     * @author Sunshine
     * @return
     */
    public static String delspecialsign(String text){
        if(text.indexOf("?")!=-1){
        	text = text.replaceAll("[?]", "");
        }
        if(text.indexOf("*")!=-1){
        	text = text.replaceAll("[*]", "");
        }
        if(text.indexOf("/")!=-1){
        	text = text.replaceAll("/", "");
        }
        if(text.indexOf("\\")!=-1){
        	text = text.replaceAll("\\", "");
        }
        if(text.indexOf("\\\\")!=-1){
        	text = text.replaceAll("\\\\", "");
        }
        if(text.indexOf(":")!=-1){
        	text = text.replaceAll(":", "");
        }
        if(text.indexOf("\"")!=-1){
        	text = text.replaceAll("\"", "");
        }
        if(text.indexOf("<")!=-1){
        	text = text.replaceAll("<", "");
        }
        if(text.indexOf(">")!=-1){
        	text = text.replaceAll(">", "");
        }
        if(text.indexOf("|")!=-1){
        	text = text.replaceAll("[|]", "");
        }
        if(text.indexOf("'")!=-1){
        	text = text.replaceAll("'", "");
        }
        if(text.indexOf("\\n")!=-1){
        	text = text.replaceAll("\\n", "");
        }
       
        return text;
    }
    
    public static String upperString(String str){
    	
    	StringBuffer stringbf = new StringBuffer();
	    Matcher m = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(str);
	
	    while (m.find()) {
	        m.appendReplacement(stringbf, m.group(1).toUpperCase() + m.group(2).toLowerCase());
	    }
	    
	    String upperStr = m.appendTail(stringbf).toString();
	    
	    return upperStr;
    }
    
    //将二进制字符串转换成int数组
    public static int[] BinstrToIntArray(String binStr) {    
	    char[] temp=binStr.toCharArray();
	    int[] result=new int[temp.length];  
	    for(int i=0;i<temp.length;i++) {
	      result[i]=temp[i]-48;
	    }
	    return result;
	  }
	   
	  //将二进制转换成字符
	 public static char BinstrToChar(String binStr){
	     int[] temp=BinstrToIntArray(binStr);
	     int sum=0;
	     for(int i=0; i<temp.length;i++){
	       sum +=temp[temp.length-1-i]<<i;
	     }  
	     return (char)sum;
	  }
	 //将二进制转换成字符串
	  public static String BinstrToStr(String str){
	    String[] tempStr=str.split(" ");
	    char[] tempChar=new char[tempStr.length];
	    for(int i=0;i<tempStr.length;i++) {
	      tempChar[i]=BinstrToChar(tempStr[i]);
	    }
	    return String.valueOf(tempChar);
	   }
	  
	  //将字符串转换成二进制
	  public static String stringToBin(String str){
		  char[] strChar=str.toCharArray();
		  String result="";
		  for(int i=0;i<strChar.length;i++){
		    result +=Integer.toBinaryString(strChar[i])+ " ";
		  }
		  return result;
	  }
	  
	  @SuppressWarnings("resource")
	public static void cunFile(InputStream inputStream,String path) throws IOException {  
	        // 通过url获取文件  

	        // 通过inputStream获取文件  
//	        InputStream inputStream = new FileInputStream(f);  
	        // 定义一个文件名字进行接收获取文件  
	        FileOutputStream fileOut = new FileOutputStream(new File(path));  
	        byte[] buf = new byte[1024 * 8];  
	        while (true) {  
	            int read = 0;  
	            if (inputStream != null) {  
	                read = inputStream.read(buf);  
	            }  
	            if (read == -1) {  
	                break;  
	            }  
	            fileOut.write(buf, 0, read);  
	        }  
	        // 查看文件获取是否成功  
	        if (fileOut.getFD().valid() == true) {  
	            System.out.println("获取文件保存成功");  
	        } else {  
	            System.out.println("获取文件失败");  
	        }  
	        fileOut.flush();  
	    } 
	  
	    public static boolean deleteDir(String path){  
	        File file = new File(path);  
	        if(!file.exists()){//判断是否待删除目录是否存在  
	            System.err.println("The dir are not exists!");  
	            return false;  
	        }  
	          
	        String[] content = file.list();//取得当前目录下所有文件和文件夹  
	        for(String name : content){  
	            File temp = new File(path, name);  
	            if(temp.isDirectory()){//判断是否是目录  
	                deleteDir(temp.getAbsolutePath());//递归调用，删除目录里的内容  
	                temp.delete();//删除空目录  
	            }else{  
	                if(!temp.delete()){//直接删除文件  
	                    System.err.println("Failed to delete " + name);  
	                }  
	            }  
	        }  
	        //删除空目录
	        boolean success = (new File(path)).delete();
	         if (success) {
	             System.out.println("Successfully deleted empty directory: " + path);
	         } else {
	             System.out.println("Failed to delete empty directory: " + path);
	         }
	        return true;  
	    } 
	    
	    public static boolean deleteFile(String fileName){
	        File file = new File(fileName);
	        if(file.isFile() && file.exists()){
	            Boolean succeedDelete = file.delete();
	            if(succeedDelete){
	                System.out.println("删除单个文件"+fileName+"成功！");
	                return true;
	            }
	            else{
	                System.out.println("删除单个文件"+fileName+"失败！");
	                return true;
	            }
	        }else{
	            System.out.println("删除单个文件"+fileName+"失败！");
	            return false;
	        }
	    }
	     
	     public static void ReadFile(InputStream in){
	    	 byte b[] = new byte[1024];   
		        int len = 0;   
		        int temp=0;          //所有读取的内容都使用temp接收   
		        try {
		        	System.out.println(in.read());
					while((temp=in.read())!=-1){    //当没有读取完时，继续读取   
					    b[len]=(byte)temp;   
					    len++;   
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
		        try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
		        System.out.println(new String(b,0,len));   
	     }
	     
	     public static String getRequestStringData(HttpServletRequest request) {
	    	    StringBuffer info = new java.lang.StringBuffer();
	    	    InputStream in = null;
	    	    try {
	    	        in = request.getInputStream();
	    	        BufferedInputStream buf = new BufferedInputStream(in);
	    	        byte[] buffer = new byte[1024];
	    	        int iRead;
	    	        while ((iRead = buf.read(buffer)) != -1) {
	    	            info.append(new String(buffer, 0, iRead, "utf-8"));
	    	        }
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    } finally {
	    	        try {
	    	            if (null != in) {
	    	                in.close();
	    	            }
	    	        } catch (IOException e) {
	    	            e.printStackTrace();
	    	        }
	    	    }
	    	    return info.toString();
	    	}
	     
	     /** 
	      * 删除目录（文件夹）以及目录下的文件 
	      * @param   sPath 被删除目录的文件路径 
	      * @return  目录删除成功返回true，否则返回false 
	      */  
	     public static boolean deleteDirectory(String sPath) {  
	         //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	         if (!sPath.endsWith(File.separator)) {  
	             sPath = sPath + File.separator;  
	         }  
	         File dirFile = new File(sPath);  
	         //如果dir对应的文件不存在，或者不是一个目录，则退出  
	         if (!dirFile.exists() || !dirFile.isDirectory()) {  
	             return false;  
	         }  
	         boolean flag = true;  
	         //删除文件夹下的所有文件(包括子目录)  
	         File[] files = dirFile.listFiles();  
	         for (int i = 0; i < files.length; i++) {  
	             //删除子文件  
	             if (files[i].isFile()) {  
	                 flag = deleteFile(files[i].getAbsolutePath());  
	                 if (!flag) break;  
	             } //删除子目录  
	             else {  
	                 flag = deleteDirectory(files[i].getAbsolutePath());  
	                 if (!flag) break;  
	             }  
	         }  
	         if (!flag) return false;  
	         //删除当前目录  
	         if (dirFile.delete()) {  
	             return true;  
	         } else {  
	             return false;  
	         }  
	     } 
	     
	     /**
	      * 删除空目录
	      * @param dir 将要删除的目录路径
	      */
	     public static void doDeleteEmptyDir(String dir) {
	         boolean success = (new File(dir)).delete();
	         if (success) {
	             System.out.println("Successfully deleted empty directory: " + dir);
	         } else {
	             System.out.println("Failed to delete empty directory: " + dir);
	         }
	     }

	/**
	 * 验证码发送
	 * @param code
	 * @param mobile
	 */
	public static void getVerificationCode(String code,String mobile) {
			String host = "https://cxkjsms.market.alicloudapi.com";
			String path = "/chuangxinsms/dxjk";
			String method = "POST";
			String appcode = "64b41e45e09b425ebf3de20a8450ab13";
			Map<String, String> headers = new HashMap<String, String>();
			//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			headers.put("Authorization", "APPCODE " + appcode);
			Map<String, String> querys = new HashMap<String, String>();
	//	    querys.put("Template", "短信模板中签名和内容自定义，请联系旺旺客服或qq：726980650报备。");
			querys.put("content", "【出国宝】尊敬的用户，您的短信验证码为："+code+"，如非本人操作请忽略本短信。");
			querys.put("mobile", mobile);
			Map<String, String> bodys = new HashMap<String, String>();


			try {
				/**
				 * 重要提示如下:
				 * HttpUtils请从
				 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
				 * 下载
				 *
				 * 相应的依赖请参照
				 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
				 */
				HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
				System.out.println(response.toString());
				//获取response的body
				//System.out.println(EntityUtils.toString(response.getEntity()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	public static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;

        }

        return false;

    }

  /**

   * 是否是英文

   * @param c

   * @return

   */

   public static boolean isEnglish(String charaString){

      return charaString.matches("^[a-zA-Z]*");

    }

  public static boolean isChinese(String str){

      String regEx = "[\\u4e00-\\u9fa5]+";

      Pattern p = Pattern.compile(regEx);

      Matcher m = p.matcher(str);

     if(m.find())

       return true;

     else

       return false;

   }

}
