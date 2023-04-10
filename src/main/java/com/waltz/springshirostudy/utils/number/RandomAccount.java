package com.waltz.springshirostudy.utils.number;

import java.util.Random;

/**
 * @author MrWei
 * @version 1.0.0
 * @date 2023/1/4 17:55
 * @description 随机8位账号
 */
public class RandomAccount {

    // 8位随机账号
    public static String randomAccount(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        // 第一位数字在[1,9]
        str.append(random.nextInt(9)+1);
        // 剩下七位数字
        for(int i=0;i<7;i++){
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    // 六位验证码
    public static String getCode(){
        StringBuilder s = new StringBuilder();
        int codeSize = 6;
        while (s.length() < codeSize) {
            s.append((int) (Math.random() * 10));
        }
        return s.toString();
    }
}
