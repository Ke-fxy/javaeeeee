package com.javaee.util;

import java.util.Random;

/**
 * @Author Ke
 * @Date 2022/6/12 16:10
 * @Description
 * @Version 1.0
 */
public class RandomUtil {

    public static int getRandom(int size){
        Random random = new Random();
        System.out.println(random.nextInt(size));
        return 0;
    }

    public static void main(String[] args) {
        RandomUtil.getRandom(5);
    }

}