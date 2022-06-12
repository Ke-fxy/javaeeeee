package com.javaee.controller;

import com.github.pagehelper.PageInfo;
import com.javaee.entities.CommonResult;
import com.javaee.entities.Student;
import com.javaee.service.StudentService;
import com.javaee.util.JavaWebToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user/student")
@CrossOrigin(maxAge = 3600, value = "*")
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

    @PostMapping(value = "/getCode")
    public CommonResult<String> getCode(@RequestBody Map map) {

        /*String token = (String) map.get("token");
        String checkResult = studentService.checkup(token);

        if (checkResult == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }*/

        String phone = (String) map.get("phone");
        if (phone.length() != 11) {
            return new CommonResult<>(200, "手机号码格式不正确");
        }

        String code = studentService.getCode(phone);

        return new CommonResult<>(100, "?", code);
    }

    @PostMapping(value = "/checkCode")
    public CommonResult<String> checkCode(@RequestBody HashMap<String, Object> map) {

        String phone = null;
        String code = null;

        try {
            phone = String.valueOf(map.get("phone"));
            code = String.valueOf(map.get("code"));
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<>(200, "数据传输错误");
        }
        String token = studentService.checkCode(phone, code);
        if (token != null) {
            return new CommonResult<>(100, "登录成功", token);
        }else {
            return new CommonResult<>(200,"登录失败，验证码错误");
        }

    }

    @PostMapping(value = "/changePwd")
    public CommonResult changePwd(@RequestBody Map map) {
        String oldPassword = (String) map.get("oldPassword");
        String token = (String) map.get("token");
        System.out.println("oldPassword:" + oldPassword);
        System.out.println("token:" + token);
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

    @PostMapping(value = "/getStudent")
    public CommonResult<Student> getStudent(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = studentService.checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Integer id = Integer.parseInt(checkup);
        Student student = studentService.getById(id);

        return student != null ? new CommonResult<>(100, "获取学生信息成功", student) : new CommonResult<>(200, "获取学生信息失败");

    }

    @PostMapping(value = "/delete")
    public CommonResult<String> delete(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = studentService.checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer result = studentService.delete(Integer.parseInt(map.get("id")));

        return result > 0 ? new CommonResult<>(100, "删除成功") : new CommonResult<>(200, "删除失败");

    }


    @PostMapping(value = "/getAllStudent")
    public CommonResult<PageInfo<Student>> getAllStudent(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = studentService.checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        PageInfo<Student> studentList = studentService.getAll(map.get("numStr"), map.get("nameStr"), map.get("limit"), map.get("page"));

        return studentList != null ? new CommonResult<>(100, "获取学生列表成功", studentList) : new CommonResult<>(200, "获取学生列表失败");

    }

    @PostMapping(value = "/getAllStudentByTeacherId")
    public CommonResult<PageInfo<Student>> getAllStudentByTeacherId(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = studentService.checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Map<String, Object> webToken = JavaWebToken.parserJavaWebToken(token);
        Integer id = (Integer) webToken.get("id");

        PageInfo<Student> studentList = studentService.getAllStudentByTeacherId(id, map.get("numStr"), map.get("nameStr"), map.get("limit"), map.get("page"));

        return studentList != null ? new CommonResult<>(100, "获取学生列表成功", studentList) : new CommonResult<>(200, "获取学生列表失败");

    }

}