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
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(byte[] key) {
        return (new Base64()).decode(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptBASE64(byte[] key) {
        return (new Base64()).encode(key);
    }

    public static void main(String[] args) {
        byte[] data = Base64Utils.encryptBASE64("http://aub.iteye.com/".getBytes());
        System.out.println("加密前：" + new String(data));
        byte[] byteArray = Base64Utils.decryptBASE64(data);
        System.out.println("解密后：" + new String(byteArray));
    }
}
