package me.zuhr.demo.basis.utils;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author zurun
 * @date 2018/5/10 10:56:27
 */
public class Base64Utils {
    /**
     * BASE64解密
     *
     * @param encryptedData
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(byte[] encryptedData) {
        return (new Base64()).decode(encryptedData);
    }

    public static byte[] decryptBASE64(String encryptedData) {
        return decryptBASE64(encryptedData.getBytes());
    }

    public static String decryptToString(String encryptedData) {
        return decryptToString(encryptedData.getBytes());
    }

    public static String decryptToString(byte[] encryptedData) {
        return new String(decryptBASE64(encryptedData));
    }

    /**
     * BASE64加密
     *
     * @param decryptedData
     * @return
     * @throws Exception
     */
    public static byte[] encryptBASE64(byte[] decryptedData) {
        return (new Base64()).encode(decryptedData);
    }

    public static byte[] encryptBASE64(String decryptedData) {
        return encryptBASE64(decryptedData.getBytes());
    }

    public static String encryptToString(String decryptedData) {
        return encryptToString(decryptedData.getBytes());
    }

    public static String encryptToString(byte[] decryptedData) {
        return new String(encryptBASE64(decryptedData));
    }


    public static void main(String[] args) {
        String data = "http://aub.iteye.com/";
        System.out.println("加密前：" + data);
        System.out.println("加密后：" + encryptBASE64(data));
        System.out.println("加密后：" + encryptToString(data));
        System.out.println("解密后：" + decryptToString(encryptToString(data)));
        System.out.println("解密后：" + decryptToString(encryptBASE64(data)));
    }
}
