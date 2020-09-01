package com.ruoyi.common.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    private MD5() {
    }

    public final static String getMessageDigest(byte[] buffer) {
        //首先初始化一个字符数组，用来存放每个16进制字符
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成"SHA1"）
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            // buffer 是输入字符串转换得到的字节数组
            mdTemp.update(buffer);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] md = mdTemp.digest();

            int j = md.length;
            // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
            char str[] = new char[j * 2];
            int k = 0;
            //遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            //字符数组转换成字符串返回
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}