package me.zuhr.demo.basis.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;

/**
 * 微信小程序aes
 *
 * @author zurun
 * @link=https://developers.weixin.qq.com/miniprogram/dev/api/signature.html#wxchecksessionobject
 * @link=http://lib.csdn.net/article/wechat/59562?knId=1796 说明:
 * 1.对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
 * 2.对称解密的目标密文为 Base64_Decode(encryptedData)。
 * 3.对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
 * 4.对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
 * @date 2018/5/9 17:38:41
 */
public class AesUtils {
    /**
     * 算法名称
     */
    static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/模式/填充方式
     */
    static final String algorithmStr = "AES/CBC/PKCS7Padding";
    private static Key key;
    private static Cipher cipher;
    boolean isinited = false;

    /**
     * 默认对称解密算法初始向量 iv
     */
    static byte[] iv = {0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38};

    public static void init(byte[] keyBytes) {

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr, "BC");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 加密方法
     * --使用默认iv时
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = encryptOfDiyIV(content, keyBytes, iv);
        return encryptedText;
    }


    /**
     * 解密方法
     * --使用默认iv时
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes) {
        byte[] encryptedText = decryptOfDiyIV(encryptedData, keyBytes, iv);
        return encryptedText;
    }

    /**
     * 加密方法
     * ---自定义对称解密算法初始向量 iv
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param ivs      自定义对称解密算法初始向量 iv
     * @return 加密的结果
     */
    public static byte[] encryptOfDiyIV(byte[] content, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        System.out.println("IV：" + new String(ivs));
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return
     */
    public static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        byte[] encryptedText = null;
        init(keyBytes);
        System.out.println("IV：" + new String(ivs));
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }


    public static void test2() throws Exception {
        byte[] keyBytes = "aaaaaaaaaaaaaaaa".getBytes();
        byte[] cleartext = "ttest".getBytes();

        System.out.println(new String(decrypt(encrypt(cleartext,keyBytes),keyBytes)));
//
//        byte[] encryptedData = encrypt(Base64Utils.encryptBASE64("tttest".getBytes()), keyBytes);
//        System.out.println(encryptedData);
//
//        byte[] decryptedData = decrypt(Base64Utils.decryptBASE64(encryptedData), keyBytes);
//        System.out.println(ByteUtils.toObject(decryptedData));
//        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
//        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
//        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
//        toStr(sessionKey, encryptedData, iv);
    }
//
public static void main(String[] args) throws Exception {
    test2();
    String  encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
    String iv="r7BXXKkLb8qrSNn05n0qiA==";
    String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
    toStr(sessionKey,encryptedData,iv);
}

    private static void  toStr(String sessionKey,String  encryptedData ,String iv) throws Exception{
        byte[] sessionKeyBy = Base64Utils.decryptBASE64(sessionKey.getBytes());
        byte[] encryptedDataBy = Base64Utils.decryptBASE64(encryptedData.getBytes());
        byte[] ivBy = Base64Utils.decryptBASE64(iv.getBytes());
        byte[] dec = decryptOfDiyIV(encryptedDataBy, sessionKeyBy,ivBy);
        System.out.println(new String(dec));

    }


}

