package com.javaee.controller;

import com.javaee.entities.CommonResult;
import com.javaee.entities.Student;
import com.javaee.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Ke
 * @Date 2022/2/9 21:56
 * @Description
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Resource
    StudentService studentService;

    @PostMapping(value = "/login")
    public CommonResult login(@RequestBody Map<String, String> map) {

//        System.out.println("sNo:"+sNo);
//        System.out.println("password:"+password);

        Integer sNo = Integer.parseInt(map.get("sNo"));
        String password = map.get("password");

        Map<String, Object> studentMap = studentService.login(sNo, password);
        if (studentMap == null) {
            return new CommonResult(200, "用户名不存在或密码错误");
        }
        Student student = (Student) studentMap.get("student");
        String token = (String) studentMap.get("token");

        if (student == null) {
            return new CommonResult(200, "用户名不存在或密码错误");
        } else {
            if (student.getPhone().length() == 0) {
                return new CommonResult(100, "未绑定手机号", token);
            } else {
                return new CommonResult(100, "登录成功", token);
            }
        }

    }


    @PostMapping(value = "/test")
    public String test(@RequestBody Map map) {

        String str = (String) map.get("str");

        return str;

    }

    @PostMapping(value = "getCode")
    public CommonResult<String> getCode(@RequestBody Map map) {

        String token = (String) map.get("token");
        String checkResult = studentService.checkup(token);

        if (checkResult == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        String phone = (String) map.get("phone");

        String code = studentService.getCode(phone, checkResult);

        return new CommonResult<>(100, "?", code);
    }

    @PostMapping(value = "changePwd")
    public CommonResult changePwd(@RequestBody Map map) {
        String oldPassword = (String) map.get("oldPassword");
        String token = (String) map.get("token");
        System.out.println("oldPassword:" + oldPassword);
        System.out.println("token:" + token);


//        String token = (String) map.get("token");
//        //"data": "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MX0.xxzv08P4YGKnWdyIqVCOaFFQQr7kjQKa1L3BhlrdA48"
//        String checkResult = studentService.checkup(token);
//
//        if (checkResult==null){
//            return new CommonResult(200,"用户未登录或登录状态失效");
//        }
//
//        String oldPassword = (String) map.get("oldPassword");
//        String newPassword = (String) map.get("newPassword");
//        Boolean flag = studentService.checkPwd(token,oldPassword);
//
//        String result = "";
//        if (flag){
//
//            studentService.deleteToken(token);
//            result = studentService.updatePassword(token,newPassword);

//        }
//
//
//        if(result.equals("AC")){
//            return new CommonResult(100,"修改成功",checkResult);
//        }else {
//            return new CommonResult(200,"修改失败",checkResult);
//        }
        return new CommonResult(200, "修改失败");

    }

    @PostMapping(value = "updateStudentRole")
    public CommonResult<String> updateStudentRole(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = studentService.checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer studentId = Integer.parseInt(map.get("studentId"));
        Integer role = Integer.parseInt(map.get("role"));

        Integer result = 0;
        if (role > -1 && role <= 1) {
            result = studentService.updateRole(studentId, role);
        } else {
            return new CommonResult<>(200, "role的取值应为0,1");
        }

        return result > 0 ? new CommonResult<>(100, "更新成功") : new CommonResult<>(200, "更新失败");

    }


}