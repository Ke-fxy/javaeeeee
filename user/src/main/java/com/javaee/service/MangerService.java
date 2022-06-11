package com.javaee.service;

import com.javaee.entities.Manager;
import com.javaee.mapper.ManagerMapper;
import com.javaee.util.JavaWebToken;
import com.javaee.util.RSAEncrypt;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class MangerService {

    @Resource
    ManagerMapper managerMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    /**
     * create by: Chalksyy
     * description: 组织管理员登陆
     * create time: 2022/4/25 11:11
     * @return Manager
     */
    public String login(Integer mno,String password){

        Manager manager = managerMapper.login(mno);


        if (manager != null) {
            String decrypt = "";

            try {
                decrypt = RSAEncrypt.decrypt(manager.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            if (decrypt.equals(password)) {
                HashMap<String, Object> map = new HashMap<>(2);
                map.put("id", manager.getId());
                String token = JavaWebToken.createJavaWebToken(map);

                ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
                forValue.set("userToken:" + token, String.valueOf(manager.getId()));
                //stringRedisTemplate.expire("userToken:" + token, 7, TimeUnit.DAYS);
                return token;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
