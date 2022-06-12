package com.javaee.service;

import com.zhenzi.sms.ZhenziSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ihlov
 */
@Service
public class CodeService {

    @Autowired
    StringRedisTemplate redisTemplate;

    public String setCode(String addr,String code,String phone){

        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com","109811","10963602-1525-455d-826a-9566537c4d40");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("number",phone);
        params.put("templateId","6466");
        String[] templateParams = new String[2];
        templateParams[0] = code;
        templateParams[1] = "5分钟";
        params.put("templateParams",templateParams);
        String result = null;
        try {
            result = client.send(params);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        forValue.set(addr + "code",code);
        //String s = forValue.get("addr" + code);
        redisTemplate.expire(addr+"code",300*1000, TimeUnit.MILLISECONDS);

        return result;
    }

    public String setCodeWithoutAddr(String code,String phone,String checkResult){

        ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com","109811",
                "YTViMDg5MzktOGViYS00MTFjLWE3NGItNjZhYzNhYWM1NjZm");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("number",phone);
        params.put("templateId","6466");
        String[] templateParams = new String[1];
        templateParams[0] = code;
        params.put("templateParams",templateParams);
        String result = null;

        try {
            result = client.send(params);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        forValue.set(checkResult + "code",code);
        redisTemplate.expire(checkResult+"code",300*1000, TimeUnit.MILLISECONDS);

        return result;
    }

    public boolean verifyCode(String checkResult, String code) {
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String codeOld = forValue.get(checkResult + "code");
        if(codeOld!=null && codeOld.equals(code)){
            return true;
        }
        return false;
    }
}
