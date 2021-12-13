package com.ruoyi.common.utils;

import java.util.Random;

/**
 * 随机生成字母和数字组合
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
public class RandomMathLetter {
    /**
     * 产生随机字符串
     */
    private static Random randGen = null;
    /**
     * 数字和字母
     */
    private static char[] numbersAndLetters = null;

    /**
     * 字符串
     */
    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
        }
        return new String(randBuffer);
    }
}
