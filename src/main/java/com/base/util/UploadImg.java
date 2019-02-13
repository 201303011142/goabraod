package com.base.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

public class UploadImg {
	// 定义常数
	// ak
	public static final String ACCESS_KEY = "XUgt8duM1iOCVLrWJ-VbdmciTgX6lYHnYTRQ5U5K";
	// sk
	public static final String SECRET_KEY = "c2X_j1MLxhPJ9YgQkTUjkjvt59e0NfG8ojA6pENq";
	// 链接
	public static final String BUCKET_HOST_NAME = "cdn.travbao.com";
	//
	private static final String MAC_NAME = "HmacSHA1";
	// 存储图片的文件夹
	public static final String BUCKET_NAME = "travbao";
	// 编码
	public static final String ENCODING = "utf-8";
	// 创建auto
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	// 抓取网络资源到空间
	public String testFetch(String remoteSrcUrl) {

		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		// ...其他参数参考类注释
		// String remoteSrcUrl =
		// "http://ojg32ej8x.bkt.clouddn.com/ce81d939-437e-45ec-8580-13913cd1264c.png";

		BucketManager bucketManager = new BucketManager(auth, cfg);

		String imgName = "";
		// 抓取网络资源到空间
		try {
			FetchRet fetchRet = bucketManager.fetch(remoteSrcUrl, BUCKET_NAME);

			imgName = fetchRet.key;
		} catch (QiniuException ex) {
			System.err.println(ex.response.toString());
		}
		return imgName;
	}

	public static String getNewUrl(String remoteSrcUrl) {
		// 获取文件名称
		String fetch = new UploadImg().testFetch(remoteSrcUrl);
		// 确定文件路径
		String imgPath = "http://" + BUCKET_HOST_NAME + "/" + fetch;
		// 返回
		return imgPath;
	}

	public String getUpToken() {
		return auth.uploadToken(BUCKET_NAME, null, 3600,
				new StringMap().put("insertOnly", 1));
	}

	public static Integer imageSize(String image) {
		String str = image.substring(22); // 1.需要计算文件流大小，首先把头部的data:image/png;base64,（注意有逗号）去掉。
		Integer equalIndex = str.indexOf("=");// 2.找到等号，把等号也去掉
		if (str.indexOf("=") > 0) {
			str = str.substring(0, equalIndex);
		}
		Integer strLength = str.length();// 3.原来的字符流大小，单位为字节
		Integer size = strLength - (strLength / 8) * 2;// 4.计算后得到的文件流大小，单位为字节
		return size;
	}

	public String put64image(String content, String key) throws Exception {
		// //定义字符串
		String key_str = "";
		// //生成图片的位数
		// int a = (int)(Math.random()*20)+20;
		// //生成图片的名字
		// String key = RandomStr.randomStr(a);
		// 图片转换成base64
		String file64 = QRGenUtils.getBase64(content).replaceAll(
				"[\\s*\t\n\r]", "");
		// 上传连接
		String url = "http://upload-z2.qiniu.com/putb64/-1/key/"
				+ UrlSafeBase64.encodeToString(key);
		RequestBody rb = RequestBody.create(null, file64);
		Request request = new Request.Builder().url(url)
				.addHeader("Content-Type", "application/octet-stream")
				.addHeader("Authorization", "UpToken " + getUpToken()).post(rb)
				.build();

		OkHttpClient client = new OkHttpClient();
		okhttp3.Response response = client.newCall(request).execute();
		String str = response.toString().substring(8);
		System.out.println("----------" + str + "------------");
		if (str.contains("200")) {
			key_str = key;
		}
		return key_str;
	}

	/**
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
			throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);
		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}

	public String putBase64image(String file64, String key) throws Exception {
		// //定义字符串
		String key_str = "";
		// //生成图片的位数
		// int a = (int)(Math.random()*20)+20;
		// //生成图片的名字
		// String key = RandomStr.randomStr(a);
		// 图片转换成base64
		// String file64 =
		// QRGenUtils.getBase64(content).replaceAll("[\\s*\t\n\r]", "");
		// 上传连接
		String url = "http://upload-z2.qiniu.com/putb64/-1/key/"
				+ UrlSafeBase64.encodeToString(key);
		RequestBody rb = RequestBody.create(null, file64);
		Request request = new Request.Builder().url(url)
				.addHeader("Content-Type", "application/octet-stream")
				.addHeader("Authorization", "UpToken " + getUpToken()).post(rb)
				.build();
		OkHttpClient client = new OkHttpClient();
		okhttp3.Response response = client.newCall(request).execute();
		String str = response.toString().substring(8);
//		System.out.println("----------" + str + "------------");
		if (str.contains("200")) {
			key_str = key;
		}
		return key_str;
	}

	public static void testFetch(byte[] uploadBytes) {

		Configuration cfg = new Configuration(Zone.zone2());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		String key = null;
		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(
				uploadBytes);
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		String upToken = auth.uploadToken(BUCKET_NAME);
		try {
			Response response = uploadManager.put(byteInputStream, key,
					upToken, null, null);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
					DefaultPutRet.class);
			System.out.println(putRet.key);
			System.out.println(putRet.hash);
		} catch (QiniuException ex) {
			ex.printStackTrace();
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
	}

	// //////////pdf转jpg//////////////
	public static List<String> pdfToImagePath(String filePath,String timestamp) {
		List<String> list = new ArrayList<>();
		String fileDirectory = filePath.substring(0, filePath.lastIndexOf("/"));// 获取去除后缀的文件路径

		String imagePath;
		File file = new File(filePath);
		try {
			File f = new File(fileDirectory);
			if (!f.exists()) {
				f.mkdir();
			}
			PDDocument doc = PDDocument.load(file);
			PDFRenderer renderer = new PDFRenderer(doc);
			int pageCount = doc.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				// 方式1,第二个参数是设置缩放比(即像素)
				// BufferedImage image = renderer.renderImageWithDPI(i, 296);
				// 方式2,第二个参数是设置缩放比(即像素)
				BufferedImage image = renderer.renderImage(i, 2.5f); // 第二个参数越大生成图片分辨率越高，转换时间也就越长
				imagePath = fileDirectory + "/" + timestamp + i + ".jpg";
				ImageIO.write(image, "PNG", new File(imagePath));
				list.add(imagePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	// ///////////判断图片是彩色图片还是黑白图片/////////////////
	/**
	 * 
	 * @param imagePath
	 *            下载图片的路径
	 * @return 返回true是彩色图片 返回false是黑灰图片
	 * @throws Exception
	 */
	public static Boolean execote(String imagePath) throws Exception {
		//转换字节流
		InputStream inputStream = new URL(imagePath).openConnection().getInputStream();
		//读取
		BufferedImage src = ImageIO.read(inputStream);
		int height = src.getHeight();
		int width = src.getWidth();
		int[] rgb = new int[4];
		int o = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixel = src.getRGB(i, j);
				rgb[1] = (pixel & 0xff0000) >> 16;
				rgb[2] = (pixel & 0xff00) >> 8;
				rgb[3] = (pixel & 0xff);
				// 如果像素点不相等的数量超过50个 就判断为彩色图片
				if (rgb[1] != rgb[2] && rgb[2] != rgb[3] && rgb[3] != rgb[1]) {
					o += 1;
					if (o >= 50) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
