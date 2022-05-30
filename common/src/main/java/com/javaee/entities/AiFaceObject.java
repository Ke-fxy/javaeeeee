package com.javaee.entities;

import com.baidu.aip.face.AipFace;

/**
 * @Author Ke
 * @Date 2022/3/22 21:33
 * @Description 人脸识别实例
 * @Version 1.0
 */
public class AiFaceObject {

        //设置APPID/AK/SK
        public static final String APP_ID = "25820236";
        public static final String API_KEY = "mnERWMmRckA3uZW4Np2o2msk";
        public static final String SECRET_KEY = "kL1fGupHYz2EQNNNSU4KfeYosK1OdRzr";
        //这上面的东西在你申请百度接口的时候 都会给的

        //创建一个aipface对象
        private static AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        //创建单例的原因是避免多次获取sdk
        public static AipFace getClient(){
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);
            return client;
        }



}