package com.javaee.controller;

import com.javaee.entities.CommonResult;
import com.javaee.entities.Teacher;
import com.javaee.service.TeacherService;
import com.javaee.util.JavaWebToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Ke
 * @Date 2022/3/13 19:31
 * @Description
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping(value = "/user/teacher")
@CrossOrigin(maxAge = 3600,value = "*")
public class TeacherController {

    @Resource
    TeacherService teacherService;

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

    @PostMapping(value = "/login")
    public CommonResult<String> login(@RequestBody HashMap<String, String> map) {

        @NotNull
        Integer tNo = Integer.parseInt(map.get("tNo"));
        @NotNull
        String password = map.get("password");

        String token = teacherService.login(tNo, password);

        if (token != null) {
            return new CommonResult<>(100, "登录成功", token);
        }

        return new CommonResult<>(200, "登录失败");
    }

    @PostMapping(value = "/getTeacher")
    public CommonResult<Teacher> getTeacher(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Map<String, Object> webToken = JavaWebToken.parserJavaWebToken(token);
        Integer id = (Integer) webToken.get("id");

        Teacher teacher = teacherService.getTeacher(id);

        return teacher != null ? new CommonResult<>(100, "查询成功", teacher) : new CommonResult<>(200, "查询失败");

    }

    @PostMapping(value = "/getTeachers")
    public CommonResult<List<Teacher>> getTeachers(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        List<Teacher> teachers = teacherService.getTeachers();

        if (teachers != null) {
            return new CommonResult<>(100, "查询成功", teachers);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }

    @PostMapping(value = "/updateTeacher")
    public CommonResult<String> updateTeacher(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Map<String, Object> webToken = JavaWebToken.parserJavaWebToken(token);
        Integer id = (Integer) webToken.get("id");

        String teacherName = map.get("name");
        String phone = map.get("phone");
        String major = map.get("major");
        String email = map.get("email");
        String gender = map.get("gender");

        Integer result = teacherService.updateTeacher(id, teacherName, phone, major, gender, email);

        return result > 0 ? new CommonResult<>(100, "更新成功") : new CommonResult<>(200, "更新失败");

    }

    @PostMapping(value = "updateTeacherRole")
    public CommonResult<String> updateTeacherRole(@RequestBody HashMap<String,String> map){

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer teacherId = Integer.parseInt(map.get("teacherId"));
        Integer role = Integer.parseInt(map.get("role"));

        Integer result=0;
        if(role>-1&&role<=2){
            result = teacherService.updateRole(teacherId,role);
        } else {
            return new CommonResult<>(200,"role的取值应为0,1,2");
        }

        return result>0 ? new CommonResult<>(100,"更新成功"):new CommonResult<>(200,"更新失败");

    }

}