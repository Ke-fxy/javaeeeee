package com.javaee.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import org.json.JSONObject;

import java.util.ArrayList;


public class FaceComparison {
	
	public static JSONObject Facecomparison(AipFace client, String image, String image2,String imageType){

		MatchRequest req1 = new MatchRequest(image,imageType);
	    MatchRequest req2 = new MatchRequest(image2,imageType);
	    ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
	    requests.add(req1);
	    requests.add(req2);
	    JSONObject res = client.match(requests);
		return res;
		
	}

}

