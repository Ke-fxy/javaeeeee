package com.javaee.util;

import java.util.Random;

/** 生成6位数字验证码的工具类
 * @author Ihlov
 */
public class CreateCode {


    public static String getCode(int length){
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
