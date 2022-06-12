package com.javaee.util;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @Author Ke
 * @Date 2022/6/11 20:57
 * @Description 将object类型转换为list
 * @Version 1.0
 */
public class ObjectTo {

    public static ArrayList<Integer> swap(Object object) {
        if (object == null) {
            return null;
        }

        ArrayList<Integer> list = (ArrayList<Integer>)object;
        return list;
    }

}