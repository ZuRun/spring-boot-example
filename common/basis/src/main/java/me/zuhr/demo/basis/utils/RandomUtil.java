package me.zuhr.demo.basis.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author zurun
 * @date 2017/12/18 11:03:17
 */
public class RandomUtil {

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

    /**
     * 生成随机字符串
     *
     * @param length 生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取随机整数
     *
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public static int getRandomNum(int min, int max) {
        int Num = new Random().nextInt(max - min + 1) + min;
        return Num;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 35; i++) {
            System.out.println(getRandomNum(-10, 12));

        }
    }

}
