package com.javaee.service;

import com.javaee.entities.Student;
import com.javaee.entities.StudentExample;
import com.javaee.entities.Teacher;
import com.javaee.mapper.TeacherMapper;
import com.javaee.util.CreateCode;
import com.javaee.util.JavaWebToken;
import com.javaee.util.RSAEncrypt;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Resource
    CodeService codeService;

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
                map.put("uuid", UUID.randomUUID().toString().substring(0,6));//添加随机数
                String token = JavaWebToken.createJavaWebToken(map);

                ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
                forValue.set("userToken:" + token, String.valueOf(teacher.getId()));
                stringRedisTemplate.expire("userToken:" + token, 7, TimeUnit.DAYS);
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

    public String getCode(String phone) {

        String code = CreateCode.getCode(6);
        /*StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<Student> students = teacherMapper.selectByExample(studentExample);
        String checkResult = String.valueOf(students.get(0).getSno());
        String s = codeService.setCodeWithoutAddr(code, phone,checkResult);*/

        Teacher teacherByPhone = teacherMapper.getTeacherByPhone(phone);
        String checkResult = String.valueOf(teacherByPhone.getTNo());
        String s = codeService.setCodeWithoutAddr(code, phone,checkResult);

        return s;
    }

    public String checkCode(String phone, String code) {

        Teacher teacherByPhone = teacherMapper.getTeacherByPhone(phone);

        if (codeService.verifyCode(String.valueOf(teacherByPhone.getTNo()),code)){
            Map<String, Object> map = new HashMap<>(2);
            map.put("id", teacherByPhone.getId());
            map.put("uuid", UUID.randomUUID().toString().substring(0,6));//添加随机数
            String token = JavaWebToken.createJavaWebToken(map);

            ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
            forValue.set("userToken:" + token, String.valueOf(teacherByPhone.getId()));
            stringRedisTemplate.expire("userToken:" + token, 7, TimeUnit.DAYS);

            return token;
        }else {
            return null;
        }

    }
}