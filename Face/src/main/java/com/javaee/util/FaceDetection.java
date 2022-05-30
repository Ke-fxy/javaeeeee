package com.javaee.util;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;

import java.util.HashMap;


public class FaceDetection {//人脸检测的类

	public static JSONObject Facedetection(AipFace client, String image, String imageType){
		HashMap<String, String> options= new HashMap<String, String>();
		options.put("face_field", "age,beauty");//返回的人脸信息
		options.put("max_face_num", "1");//最大人脸识别数1
		options.put("face_type", "LIVE");//照骗类型 生活照
		JSONObject res=client.detect(image, imageType, options);
		return res;
	}

}

