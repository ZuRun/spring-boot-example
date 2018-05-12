package me.zuhr.demo.basis.utils;

import me.zuhr.demo.basis.constants.ErrorCode;
import me.zuhr.demo.basis.exception.AesException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * 微信小程序aes
 *
 * @author zurun
 * @link=https://developers.weixin.qq.com/miniprogram/dev/api/signature.html#wxchecksessionobject
 * @link=http://lib.csdn.net/article/wechat/59562?knId=1796 (注意, 示例代码线程不安全)
 * <p>
 * 说明:
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
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/模式/填充方式
     */
    private static final String algorithmStr = "AES/CBC/PKCS7Padding";


    /**
     * 默认对称解密算法初始向量 iv
     */
    private static byte[] iv = {0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38};

    private static byte[] init(BiFunction<Key, Cipher, byte[]> callback, byte[] keyBytes) {
        Key key;
        Cipher cipher;


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
            return callback.apply(key, cipher);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new AesException(ErrorCode.aes.CIPHER_INSTANCE);
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
        return encryptOfDiyIV(content, keyBytes, iv);
    }

    /**
     * 此方法加密,返回的结果会base64加密
     *
     * @param content
     * @param keyBytes
     * @return
     */
    public static String encrypt(String content, String keyBytes) {
        byte[] bytes = encryptOfDiyIV(content.getBytes(), keyBytes.getBytes(), iv);
        return Base64Utils.encryptToString(bytes);
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
        return decryptOfDiyIV(encryptedData, keyBytes, iv);
    }

    /**
     * 此方法解密,密文需要先base64解密
     *
     * @param encryptedData
     * @param keyBytes
     * @return
     */
    public static String decrypt(String encryptedData, String keyBytes) {
        byte[] bytes = decryptOfDiyIV(Base64Utils.decryptBASE64(encryptedData), keyBytes.getBytes(), iv);
        return new String(bytes);
    }

    /**
     * 微信小程序用到,一般用于返回base64过的密文,密钥和iv
     *
     * @param encryptedData
     * @param keyBytes
     * @param iv
     * @return
     */
    public static String decrypt(String encryptedData, String keyBytes, String iv) {
        byte[] bytes = decryptOfDiyIV(Base64Utils.decryptBASE64(encryptedData), Base64Utils.decryptBASE64(keyBytes), Base64Utils.decryptBASE64(iv));
        return new String(bytes);
    }

    /**
     * 加密方法
     * ---自定义对称解密算法初始向量 iv
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @param ivs      自定义对称解密算法初始向量 iv
     * @return encryptedText 加密的结果
     */
    public static byte[] encryptOfDiyIV(byte[] content, byte[] keyBytes, byte[] ivs) {
        return init((key, cipher) -> {
//            System.out.println("IV：" + new String(ivs));
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivs));
                return cipher.doFinal(content);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AesException(ErrorCode.aes.AES_ERROR);
            }
        }, keyBytes);
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @param ivs           自定义对称解密算法初始向量 iv
     * @return decryptedText
     */
    public static byte[] decryptOfDiyIV(byte[] encryptedData, byte[] keyBytes, byte[] ivs) {
        return init((key, cipher) -> {
//            System.out.println("IV：" + new String(ivs));
            try {
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
                return cipher.doFinal(encryptedData);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AesException(ErrorCode.aes.AES_ERROR);
            }
        }, keyBytes);
    }


    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String keyBytes = "aaaaaaa1aaaaaaaaa";
        String cleartext = "ttest";

        System.out.println("--------------------------");
        System.out.println("明文:"+cleartext);
        System.out.println("密文:"+encrypt(cleartext,keyBytes));
        System.out.println("密文解密:"+decrypt(encrypt(cleartext, keyBytes), keyBytes));


        // 小程序解密
        String encryptedData = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZMQmRzooG2xrDcvSnxIMXFufNstNGTyaGS9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+3hVbJSRgv+4lGOETKUQz6OYStslQ142dNCuabNPGBzlooOmB231qMM85d2/fV6ChevvXvQP8Hkue1poOFtnEtpyxVLW1zAo6/1Xx1COxFvrc2d7UL/lmHInNlxuacJXwu0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn/Hz7saL8xz+W//FRAUid1OksQaQx4CMs8LOddcQhULW4ucetDf96JcR3g0gfRK4PC7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns/8wR2SiRS7MNACwTyrGvt9ts8p12PKFdlqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYVoKlaRv85IfVunYzO0IKXsyl7JCUjCpoG20f0a04COwfneQAGGwd5oa+T8yO5hzuyDb/XcxxmK01EpqOyuxINew==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        System.out.println("-------------小程序-------------");
        System.out.println(decrypt(encryptedData, sessionKey, iv));
    }


}

