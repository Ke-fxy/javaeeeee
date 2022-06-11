package com.javaee.controller;

import com.javaee.entities.CommonResult;
import com.javaee.service.MangerService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/user/manager")
@CrossOrigin(maxAge = 3600,value = "*")
public class ManagerController {

    @Resource
    MangerService mangerService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    private String checkup(String token) {

        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        String s = forValue.get("userToken:" + token);
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;

    }

    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody HashMap<String,String> map){



        Integer mno = Integer.parseInt(map.get("mno"));

        String password = map.get("password");

        String token = mangerService.login(mno, password);

        if (token != null) {
            return new CommonResult<>(100, "登录成功", token);
        }

        return new CommonResult<>(200, "登录失败");
    }
}
