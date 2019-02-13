package com.base.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRGenUtils {

    private static final int black = 0xFF000000;
    private static final int white  = 0xFFFFFFFF;

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? black : white);
            }
        }
        return image;
    }


    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createQRImage(String content, int width, int height, String path, String fileName) throws Exception {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        if (StringUtils.isNotBlank(path)) {
            if (!path.endsWith("/")) {
                path = path + "/";
            }
        } else {
            path = "";
        }
        String suffix = "jpg";
        if (fileName.indexOf(".") <= -1) {
            fileName = fileName + "." + suffix;
        } else {
            suffix = fileName.split("[.]")[1];
        }
        fileName = path + fileName;
        File file = new File(fileName);
        writeToFile(bitMatrix, suffix, file);
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static BufferedImage createQRImageBuffer(String content, int width, int height) throws  Exception{
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = toBufferedImage(bitMatrix);
        return image;
    }
    
    public static String getBase64(String content) {
//    	String content="123";
    	BufferedImage qrImageBuffer=null;
		try {
			qrImageBuffer = QRGenUtils.createQRImageBuffer(content, 300, 300);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ByteArrayOutputStream os=new ByteArrayOutputStream();
    	try {
			ImageIO.write(qrImageBuffer, "png", os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Base64 base64 = new Base64();
    	String base64Img = new String(base64.encode(os.toByteArray()));
    	
    	return base64Img;
	}
}
