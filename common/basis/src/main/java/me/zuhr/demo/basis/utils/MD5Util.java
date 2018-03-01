package me.zuhr.demo.basis.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by welink on 2015/4/1.
 * 进行密码加密和验证的类
 */
public class MD5Util {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String generatePassword(String inputString) throws NoSuchAlgorithmException {
        return encodeByMD5(inputString);
    }

    public static boolean validatePassword(String password,String inputString) throws NoSuchAlgorithmException {
        if(password.equals(encodeByMD5(inputString))){
            return true;
        }else{
            return false;
        }
    }

   /* public  static String returnEncodeByMD5(String originString){

        return encodeByMD5(originString);

    }
   */

    public static String encodeByMD5(String originString) throws NoSuchAlgorithmException {
        if(originString!=null){
            MessageDigest md=MessageDigest.getInstance("MD5");

            byte[] result =md.digest(originString.getBytes());
            String resultString=byteArrayToHexString(result);
            String pass=resultString.toUpperCase();
            return pass;
        }

        return null;
    }

    public static byte[] encodeByMD5_byte(String originString) throws NoSuchAlgorithmException {
        if(originString!=null){
            MessageDigest md=MessageDigest.getInstance("MD5");
            return md.digest(originString.getBytes());
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] result) {
        StringBuffer resultSb=new StringBuffer();
        for(int i=0;i<result.length;i++){
            resultSb.append(byteToHexString(result[i]));
          }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1]+hexDigits[d2];
    }
}
