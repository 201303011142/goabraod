package com.goabraod.weixin;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.collections.map.HashedMap;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import net.sf.json.JSONObject;

/**
 * 
 * @author Sunshine
 *  获取微信信息工具类
 */
public class GetOpenid {
    String appid = "wx779756596b7a9d45";
    String secret = "ebd2aedd6e8a6ef5f4d012899ac78437";
    
    String appid1 = "wxd00070574986b364";
    String secret1 = "8adad997af6cb9d2206b4fef337b5ba3";
    //可获取openid及session_key,其实这里openid不需要获取，encryptedData解密后包含openid
    public String get(String code) throws Exception {
        //官方接口，需要自己提供appid，secret和js_code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        //HttpRequestor是一个网络请求工具类，贴在了下面
        String oppid = new HttpRequestor().doGet(requestUrl);
        System.out.println("-----oppid--"+oppid+"----------");
        String requestUrl0 = "https://api.weixin.qq.com/sns/jscode2session";
        @SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
        map.put("secret", secret);
        map.put("appid", appid);
        map.put("code", code);
        //HttpRequestor是一个网络请求工具类，贴在了下面
        String oppid0 = new HttpRequestor().doPost(requestUrl0,map);
        System.out.println("-----oppid0--"+oppid0+"----------");
        
        
        JSONObject oppidObj = JSONObject.fromObject(oppid);
        @SuppressWarnings("unused")
		String openid = (String) oppidObj.get("openid");
        String session_key = (String) oppidObj.get("session_key");
        return session_key;
    }
    /**
     * 获取信息
     */
    public JSONObject getUserInfo(String encryptedData,String sessionkey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
               // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //可获取openid及session_key,其实这里openid不需要获取，encryptedData解密后包含openid
    public String get1(String code) throws Exception {
        //官方接口，需要自己提供appid1，secret1和js_code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid1="+appid1+"&secret1="+secret1+"&js_code="+code+"&grant_type=authorization_code";
        //HttpRequestor是一个网络请求工具类，贴在了下面
        String oppid = new HttpRequestor().doGet(requestUrl);
        System.out.println("-----oppid--"+oppid+"----------");
        String requestUrl0 = "https://api.weixin.qq.com/sns/jscode2session";
        @SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
        map.put("secret1", secret1);
        map.put("appid1", appid1);
        map.put("code", code);
        //HttpRequestor是一个网络请求工具类，贴在了下面
        String oppid0 = new HttpRequestor().doPost(requestUrl0,map);
        System.out.println("-----oppid0--"+oppid0+"----------");
        
        
        JSONObject oppidObj = JSONObject.fromObject(oppid);
        @SuppressWarnings("unused")
		String openid = (String) oppidObj.get("openid");
        String session_key = (String) oppidObj.get("session_key");
        return session_key;
    }
    /**
     * 获取信息
     */
    public JSONObject getUserInfo1(String encryptedData,String sessionkey,String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
               // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
