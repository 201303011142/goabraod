package com.base.util;

public class SHA {
//    public static void main(String[] args) {
////        String x1 = "";
//        String x2 = "123456";
//        SHA sha = new SHA();
////        String hexDigest1 = sha.encrypt(x1);
//        String hexDigest2 = sha.encrypt(x2);
//        System.out.println("\nstring:" + hexDigest2);
//    }
    // padding
    public static StringBuffer PAD(String x) {
        int ml; // message length
        StringBuffer mbStr = new StringBuffer(); // message convert to binary string
        for (int i = 0; i < x.length(); ++i) {
            StringBuffer temp = new StringBuffer(Long.toBinaryString(x.charAt(i))); // word binary string
            while (temp.length() < 8) {
                temp.insert(0, 0);
            }
            mbStr.append(temp);
        }
        ml = mbStr.length();

        //calculate the d
        int d = 447 - ml; // the number of zeros to complement the message
        while (d < 0) {
            d += 512;
        }

        //complement the message length to 64 bits
        StringBuffer l = new StringBuffer(Long.toBinaryString(ml));
        while (l.length() < 64) {
            l.insert(0, 0);
        }

        //padding mbStr
        mbStr.append(1);
        for (int i = 0; i < d; ++i) {
            mbStr.append(0);
        }
        mbStr.append(l);
        return mbStr;
    }

    //loop left shift
    public static StringBuffer ROTL(StringBuffer temp, int s) {
        while (temp.length() < 32) {
            temp.insert(0, 0);
        }

        //loop left shift
        for (int i = 0; i < s; ++i) {
            temp.append(temp.charAt(0));
            temp.deleteCharAt(0);
        }
        return temp;
    }

    // SHA-1
    public static String encrypt(String x) {
        long h0 = 0x67452301L;
        long h1 = 0xEFCDAB89L;
        long h2 = 0x98BADCFEL;
        long h3 = 0x10325476L;
        long h4 = 0xC3D2E1F0L;

        //SHA-1-PAD
        StringBuffer mbStr = PAD(x);

        //group mbStr by 512 bits
        int groupNum = mbStr.length() / 512;
        StringBuffer[] mbStrGroup = new StringBuffer[groupNum];
        for (int i = 0; i < groupNum; ++i) {
            mbStrGroup[i] = new StringBuffer(mbStr.substring(i * 512, (i + 1) * 512));
        }

        //calculate message digest
        for (int i = 0; i < groupNum; ++i) {
            StringBuffer[] w = new StringBuffer[80];

            //initialize ABCDE
            long a = h0;
            long b = h1;
            long c = h2;
            long d = h3;
            long e = h4;

            //initialize w0 to w15
            for (int j = 0; j < 16; ++j) {
                w[j] = new StringBuffer(mbStrGroup[i].substring(j * 32, (j + 1) * 32));
            }

            //initialize w16 to w79
            for (int j = 16; j < 80; ++j) {
                w[j] = ROTL(new StringBuffer(Long
                        .toBinaryString(Long.parseLong(w[j - 3].toString(), 2) ^ Long.parseLong(w[j - 8].toString(), 2)
                                ^ Long.parseLong(w[j - 14].toString(), 2) ^ Long.parseLong(w[j - 16].toString(), 2))),
                        1);
            }

            //loop 0 to 79
            long mod = (long) Math.pow(2, 32);
            for (int j = 0; j < 80; ++j) {
                Long f, k;
                if (j >= 0 && j <= 19) {
                    f = (b & c) | ((~b) & d);
                    k = 0x5A827999L;
                } else if (j >= 20 && j <= 39) {
                    f = b ^ c ^ d;
                    k = 0x6ED9EBA1L;

                } else if (j >= 40 && j <= 59) {
                    f = (b & c) | (b & d) | (c & d);
                    k = 0x8F1BBCDCL;
                } else {
                    f = b ^ c ^ d;
                    k = 0xCA62C1D6L;
                }
                long temp = (Long.parseLong(ROTL(new StringBuffer(Long.toBinaryString(a)), 5).toString(), 2) + f + e
                        + Long.parseLong(w[j].toString(), 2) + k) % mod;
                e = d;
                d = c;
                c = Long.parseLong(ROTL(new StringBuffer(Long.toBinaryString(b)), 30).toString(), 2);
                b = a;
                a = temp;
            }
            h0 = (h0 + a) % mod;
            h1 = (h1 + b) % mod;
            h2 = (h2 + c) % mod;
            h3 = (h3 + d) % mod;
            h4 = (h4 + e) % mod;
        }
        return Long.toHexString(h0) + Long.toHexString(h1) + Long.toHexString(h2) + Long.toHexString(h3)
                + Long.toHexString(h4);
    }
    
    

    private final int[] abcde = { 
            0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476, 0xc3d2e1f0 
        }; 
    // 摘要数据存储数组 
    private int[] digestInt = new int[5]; 
    // 计算过程中的临时数据存储数组 
    private int[] tmpData = new int[80]; 
    // 计算sha-1摘要 
    private int process_input_bytes(byte[] bytedata) { 
        // 初试化常量 
        System.arraycopy(abcde, 0, digestInt, 0, abcde.length); 
        // 格式化输入字节数组，补10及长度数据 
        byte[] newbyte = byteArrayFormatData(bytedata); 
        // 获取数据摘要计算的数据单元个数 
        int MCount = newbyte.length / 64; 
        // 循环对每个数据单元进行摘要计算 
        for (int pos = 0; pos < MCount; pos++) { 
            // 将每个单元的数据转换成16个整型数据，并保存到tmpData的前16个数组元素中 
            for (int j = 0; j < 16; j++) { 
                tmpData[j] = byteArrayToInt(newbyte, (pos * 64) + (j * 4)); 
            } 
            // 摘要计算函数 
            encrypt(); 
        } 
        return 20; 
    } 
    // 格式化输入字节数组格式 
    private byte[] byteArrayFormatData(byte[] bytedata) { 
        // 补0数量 
        int zeros = 0; 
        // 补位后总位数 
        int size = 0; 
        // 原始数据长度 
        int n = bytedata.length; 
        // 模64后的剩余位数 
        int m = n % 64; 
        // 计算添加0的个数以及添加10后的总长度 
        if (m < 56) { 
            zeros = 55 - m; 
            size = n - m + 64; 
        } else if (m == 56) { 
            zeros = 63; 
            size = n + 8 + 64; 
        } else { 
            zeros = 63 - m + 56; 
            size = (n + 64) - m + 64; 
        } 
        // 补位后生成的新数组内容 
        byte[] newbyte = new byte[size]; 
        // 复制数组的前面部分 
        System.arraycopy(bytedata, 0, newbyte, 0, n); 
        // 获得数组Append数据元素的位置 
        int l = n; 
        // 补1操作 
        newbyte[l++] = (byte) 0x80; 
        // 补0操作 
        for (int i = 0; i < zeros; i++) { 
            newbyte[l++] = (byte) 0x00; 
        } 
        // 计算数据长度，补数据长度位共8字节，长整型 
        long N = (long) n * 8; 
        byte h8 = (byte) (N & 0xFF); 
        byte h7 = (byte) ((N >> 8) & 0xFF); 
        byte h6 = (byte) ((N >> 16) & 0xFF); 
        byte h5 = (byte) ((N >> 24) & 0xFF); 
        byte h4 = (byte) ((N >> 32) & 0xFF); 
        byte h3 = (byte) ((N >> 40) & 0xFF); 
        byte h2 = (byte) ((N >> 48) & 0xFF); 
        byte h1 = (byte) (N >> 56); 
        newbyte[l++] = h1; 
        newbyte[l++] = h2; 
        newbyte[l++] = h3; 
        newbyte[l++] = h4; 
        newbyte[l++] = h5; 
        newbyte[l++] = h6; 
        newbyte[l++] = h7; 
        newbyte[l++] = h8; 
        return newbyte; 
    } 
    private int f1(int x, int y, int z) { 
        return (x & y) | (~x & z); 
    } 
    private int f2(int x, int y, int z) { 
        return x ^ y ^ z; 
    } 
    private int f3(int x, int y, int z) { 
        return (x & y) | (x & z) | (y & z); 
    } 
    private int f4(int x, int y) { 
        return (x << y) | x >>> (32 - y); 
    } 
    // 单元摘要计算函数 
    private void encrypt() { 
        for (int i = 16; i <= 79; i++) { 
            tmpData[i] = f4(tmpData[i - 3] ^ tmpData[i - 8] ^ tmpData[i - 14] ^ 
                    tmpData[i - 16], 1); 
        } 
        int[] tmpabcde = new int[5]; 
        for (int i1 = 0; i1 < tmpabcde.length; i1++) { 
            tmpabcde[i1] = digestInt[i1]; 
        } 
        for (int j = 0; j <= 19; j++) { 
            int tmp = f4(tmpabcde[0], 5) + 
                f1(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
                tmpData[j] + 0x5a827999; 
            tmpabcde[4] = tmpabcde[3]; 
            tmpabcde[3] = tmpabcde[2]; 
            tmpabcde[2] = f4(tmpabcde[1], 30); 
            tmpabcde[1] = tmpabcde[0]; 
            tmpabcde[0] = tmp; 
        } 
        for (int k = 20; k <= 39; k++) { 
            int tmp = f4(tmpabcde[0], 5) + 
                f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
                tmpData[k] + 0x6ed9eba1; 
            tmpabcde[4] = tmpabcde[3]; 
            tmpabcde[3] = tmpabcde[2]; 
            tmpabcde[2] = f4(tmpabcde[1], 30); 
            tmpabcde[1] = tmpabcde[0]; 
            tmpabcde[0] = tmp; 
        } 
        for (int l = 40; l <= 59; l++) { 
            int tmp = f4(tmpabcde[0], 5) + 
                f3(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
                tmpData[l] + 0x8f1bbcdc; 
            tmpabcde[4] = tmpabcde[3]; 
            tmpabcde[3] = tmpabcde[2]; 
            tmpabcde[2] = f4(tmpabcde[1], 30); 
            tmpabcde[1] = tmpabcde[0]; 
            tmpabcde[0] = tmp; 
        } 
        for (int m = 60; m <= 79; m++) { 
            int tmp = f4(tmpabcde[0], 5) + 
                f2(tmpabcde[1], tmpabcde[2], tmpabcde[3]) + tmpabcde[4] + 
                tmpData[m] + 0xca62c1d6; 
            tmpabcde[4] = tmpabcde[3]; 
            tmpabcde[3] = tmpabcde[2]; 
            tmpabcde[2] = f4(tmpabcde[1], 30); 
            tmpabcde[1] = tmpabcde[0]; 
            tmpabcde[0] = tmp; 
        } 
        for (int i2 = 0; i2 < tmpabcde.length; i2++) { 
            digestInt[i2] = digestInt[i2] + tmpabcde[i2]; 
        } 
        for (int n = 0; n < tmpData.length; n++) { 
            tmpData[n] = 0; 
        } 
    } 
    // 4字节数组转换为整数 
    private int byteArrayToInt(byte[] bytedata, int i) { 
        return ((bytedata[i] & 0xff) << 24) | ((bytedata[i + 1] & 0xff) << 16) | 
        ((bytedata[i + 2] & 0xff) << 8) | (bytedata[i + 3] & 0xff); 
    } 
    // 整数转换为4字节数组 
    private void intToByteArray(int intValue, byte[] byteData, int i) { 
        byteData[i] = (byte) (intValue >>> 24); 
        byteData[i + 1] = (byte) (intValue >>> 16); 
        byteData[i + 2] = (byte) (intValue >>> 8); 
        byteData[i + 3] = (byte) intValue; 
    } 
    // 将字节转换为十六进制字符串 
    private static String byteToHexString(byte ib) { 
        char[] Digit = { 
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 
                'D', 'E', 'F' 
            }; 
        char[] ob = new char[2]; 
        ob[0] = Digit[(ib >>> 4) & 0X0F]; 
        ob[1] = Digit[ib & 0X0F]; 
        String s = new String(ob); 
        return s; 
    } 
    // 将字节数组转换为十六进制字符串 
    private static String byteArrayToHexString(byte[] bytearray) { 
        String strDigest = ""; 
        for (int i = 0; i < bytearray.length; i++) { 
            strDigest += byteToHexString(bytearray[i]); 
        } 
        return strDigest; 
    } 
    // 计算sha-1摘要，返回相应的字节数组 
    public byte[] getDigestOfBytes(byte[] byteData) { 
        process_input_bytes(byteData); 
        byte[] digest = new byte[20]; 
        for (int i = 0; i < digestInt.length; i++) { 
            intToByteArray(digestInt[i], digest, i * 4); 
        } 
        return digest; 
    } 
    // 计算sha-1摘要，返回相应的十六进制字符串 
    public String getDigestOfString(byte[] byteData) { 
        return byteArrayToHexString(getDigestOfBytes(byteData)); 
    } 
    
    public static void main(String[] args) {
    	String aa = "LIKLckvwlJT9cWIhEQTwfATXvuGU5zcjF-ZW0IznMq3PB38I604vATKQh2l72XQsxsjFLhG3KiZP61GzUI2C8A";
		String ab = "1049465194";
		String ac = "1547520587";
		String ad = "http://qa.travbao.com/goabraod/view/Other/Picc/index.html";
		
		String tsa = "jsapi_ticket="+aa+"&noncestr="+ab+"&timestamp="+ac+"&url="+ad;
		
		String digestOfString = new SHA().getDigestOfString(tsa.getBytes());
		
		System.out.println(digestOfString);
	}
}
