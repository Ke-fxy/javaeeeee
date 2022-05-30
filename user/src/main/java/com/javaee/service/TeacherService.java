package com.javaee.service;

import com.javaee.entities.Teacher;
import com.javaee.mapper.TeacherMapper;
import com.javaee.util.JavaWebToken;
import com.javaee.util.RSAEncrypt;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/13 19:32
 * @Description
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherService {

    @Resource
    TeacherMapper teacherMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    public String login(Integer tNo, String password) {

        Teacher teacher = teacherMapper.login(tNo);

        if (teacher != null) {
            String decrypt = "";

            try {
                decrypt = RSAEncrypt.decrypt(teacher.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            if (decrypt.equals(password)) {
                HashMap<String, Object> map = new HashMap<>(2);
                map.put("id", teacher.getId());
                String token = JavaWebToken.createJavaWebToken(map);

                ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
                forValue.set("userToken:" + token, String.valueOf(teacher.getId()));
                //stringRedisTemplate.expire("userToken:" + token, 7, TimeUnit.DAYS);
                return token;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public Teacher getTeacher(Integer id) {

        Teacher teacher = teacherMapper.getTeacherById(id);

        if (teacher != null) {
            return teacher;
        } else {
            return null;
        }

    }

    public List<Teacher> getTeachers() {

        List<Teacher> teachers = teacherMapper.getTeachers();

        if (teachers != null && teachers.size() != 0) {
            return teachers;
        } else {
            return null;
        }

    }

    public Integer updateTeacher(Integer teacherId, String teacherName,String phone,String major,String gender,String email){

        return teacherMapper.updateTeacher(teacherId, teacherName, phone, major, gender, email);
    }

    public Integer updateRole(Integer teacherId,Integer role){
        return teacherMapper.updateRole(teacherId,role);
    }
}