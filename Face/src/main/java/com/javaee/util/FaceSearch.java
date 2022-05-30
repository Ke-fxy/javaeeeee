package com.javaee.util;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author Ke
 * @Date 2022/3/23 23:16
 * @Description
 * @Version 1.0
 */
public class FaceSearch {

    public static JSONObject search(AipFace client, String image, String imageType, String groupId, String userId) {

        HashMap<String, String> options = new HashMap<>();
        // 传入可选参数调用接口
        options.put("match_threshold", "80");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        options.put("user_id", userId);
        options.put("max_user_num", "1");

        // 人脸搜索
        JSONObject res = client.search(image, imageType, groupId, options);

        return res;

    }

}