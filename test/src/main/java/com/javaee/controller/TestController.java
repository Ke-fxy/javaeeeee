package com.javaee.controller;

import com.github.pagehelper.PageInfo;
import com.javaee.entities.CommonResult;
import com.javaee.entities.Test;
import com.javaee.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/24 20:13
 * @Description
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping(value = "/test")
@CrossOrigin(maxAge = 3600,value = "*")
public class TestController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    TestService testService;

    private String checkup(String token) {

        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        String s = forValue.get("userToken:" + token);
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;

    }

    @PostMapping(value = "/createTest")
    public CommonResult<String> createTest(@RequestBody HashMap<String, Object> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        String testName = null;
        Integer paperId = null;
        String beginTime = null;
        Integer limitTime = null;
        Integer time = null;
        String place = null;
        Integer courseId = null;
        Integer invigilatorId = null;
        Integer online = null;

        try {
            testName = String.valueOf(map.get("testName"));
            paperId = Integer.parseInt(map.get("paperId").toString());
            beginTime = String.valueOf(map.get("beginTime"));
            limitTime = Integer.parseInt(map.get("limitTime").toString());
//            time = (Integer) map.get("time");
//            place = String.valueOf(map.get("place"));
            courseId = Integer.parseInt(map.get("courseId").toString());
//            invigilatorId = (Integer) map.get("invigilatorId");
//            online = (Integer) map.get("online");
        } catch (Exception e) {
            return new CommonResult<>(200, "参数添加失败");
        }

        Timestamp timestamp = null;

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parse = simpleDateFormat.parse(beginTime);
            timestamp = new Timestamp(parse.getTime());
        } catch (ParseException e) {
            return new CommonResult<>(200,"时间格式错误");
        }

        Test test = new Test(null, testName, Integer.parseInt(checkup), paperId, new Timestamp(System.currentTimeMillis()), timestamp, limitTime, time, place, courseId, null, null, null, null, invigilatorId, online);
        boolean b = testService.addTest(test, paperId);

        if (b) {
            return new CommonResult<>(100, "添加成功");
        } else {
            return new CommonResult<>(200, "添加失败");
        }


    }

    @PostMapping(value = "/deleteTest")
    public CommonResult<String> deleteTest(@RequestBody HashMap<String, Object> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer id = null;

        try {
            id = (Integer) map.get("id");
        } catch (Exception e) {
            return new CommonResult<>(200, "参数有误");
        }

        boolean b = testService.deleteTest(id);

        if (b) {
            return new CommonResult<>(100, "删除成功");
        } else {
            return new CommonResult<>(200, "删除失败");
        }

    }

    @PostMapping(value = "/updateTest")
    public CommonResult<String> updateTest(@RequestBody HashMap<String, Object> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer id = null;
        String testName = null;
        Integer paperId = null;
        String beginTime = null;
        Integer limitTime = null;
        Integer time = null;
        String place = null;
        Integer courseId = null;
        Integer invigilatorId = null;
        Integer online = null;

        try {
            id = (Integer) map.get("id");
            testName = String.valueOf(map.get("testName"));
            //paperId = (Integer) map.get("paperId");
            beginTime = String.valueOf(map.get("beginTime"));
            limitTime = (Integer) map.get("limitTime");
            time = (Integer) map.get("time");
            place = String.valueOf(map.get("place"));
            paperId = (Integer) map.get("paperId");
            courseId = (Integer) map.get("courseId");
            invigilatorId = (Integer) map.get("invigilatorId");
            online = (Integer) map.get("online");
        } catch (Exception e) {
            return new CommonResult<>(200, "参数添加失败");
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Timestamp datetime = null;

        try {
            datetime = (Timestamp) simpleDateFormat.parse(beginTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Test test = new Test(id, testName, Integer.parseInt(checkup), paperId,new Timestamp(System.currentTimeMillis()), datetime, limitTime, time, place, courseId, null, null, null, null, invigilatorId, online);
        boolean b = testService.updateTest(test);

        if (b) {
            return new CommonResult<>(100, "修改成功");
        } else {
            return new CommonResult<>(200, "修改失败");
        }


    }

    @PostMapping(value = "/getTest")
    public CommonResult<Test> getTest(@RequestBody HashMap<String, Object> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer id = null;

        try {
            id = (Integer) map.get("id");
        } catch (Exception e) {
            return new CommonResult<>(200, "参数错误");
        }

        Test test = testService.getTest(id);

        if (test != null) {
            return new CommonResult<>(100, "查询成功", test);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }

    @PostMapping(value = "/getAllTest")
    public CommonResult<List<Test>> getAllTest(@RequestBody HashMap<String, Object> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        List<Test> allTest = testService.getAllTest();

        if (allTest != null) {
            return new CommonResult<>(100, "查询成功", allTest);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }

    @PostMapping(value = "/getAllTestByCourseIdTimeOut")
    public CommonResult<Object> getAllTestByCourseIdTimeOut(@RequestBody HashMap<String, String> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Integer id = Integer.parseInt(checkup);

        Integer pageNum = Integer.parseInt(map.get("pageNum"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));

        PageInfo<Test> allTest = testService.getAllTestByCourseIdTimeOut(id,pageNum, pageSize);

        if (allTest != null) {
            return new CommonResult<>(100, "查询成功", allTest);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }

    @PostMapping(value = "/getAllTestByCourseIdAfter")
    public CommonResult<Object> getAllTestByCourseIdAfter(@RequestBody HashMap<String, String> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Integer id = Integer.parseInt(checkup);
        System.out.println(id);

        Integer pageNum = Integer.parseInt(map.get("pageNum"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));

        PageInfo<Test> allTest = testService.getAllTestByCourseIdAfter(id,pageNum, pageSize);

        if (allTest != null) {
            return new CommonResult<>(100, "查询成功", allTest);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }

    @PostMapping(value = "/getAllTestByCourseId")
    public CommonResult<Object> getAllTestByCourseId(@RequestBody HashMap<String, String> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Integer id = Integer.parseInt(checkup);

        Integer pageNum = Integer.parseInt(map.get("pageNum"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));

        PageInfo<Test> allTest = testService.getAllTestByCourseId(id,pageNum, pageSize);

        if (allTest != null) {
            return new CommonResult<>(100, "查询成功", allTest);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }


    @PostMapping(value = "/getAllTestWithCId")
    public CommonResult<List<Test>> getAllTestWithCId(@RequestBody HashMap<String, Object> map) {

        String token = String.valueOf(map.get("token"));
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer createrId = (Integer) map.get("createrId");

        List<Test> allTest = testService.getAllTest(createrId);

        if (allTest != null) {
            return new CommonResult<>(100, "查询成功", allTest);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }


}