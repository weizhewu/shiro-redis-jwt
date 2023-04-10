package com.waltz.springshirostudy.utils.number;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author MrWei
 * @version 1.0.0
 * @date 2023/1/4 18:06
 * @description 密码加密
 */
public class DifficultPassword {
    /**
     *
     * @param way 加密方式,如：MD5
     * @param content 要加密的字符串
     * @param salt 盐
     * @param times 加密次数
     * @return 加密后的字符串
     */
    public static String difficult(String way,String content,String salt,int times){
        SimpleHash sh = new SimpleHash(way,content,salt,times);
        // 生成16进制密文
        return sh.toHex();
    }

    public static void main(String[] args) {
        System.out.println(difficult("MD5","123456","232623",3));
    }
}
