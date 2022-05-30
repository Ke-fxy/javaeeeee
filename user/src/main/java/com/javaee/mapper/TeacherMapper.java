package com.javaee.mapper;

import com.javaee.entities.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/13 19:32
 * @Description
 * @Version 1.0
 */
@Mapper
public interface TeacherMapper {
    /**
     * 通过tNo返回信息
     * @param tNo
     * @return
     */
    Teacher login(@Param("tNo") Integer tNo);

    /**
     * 通过id获得老师信息
     * @param id
     * @return
     */
    Teacher getTeacherById(@Param("id") Integer id);

    /**
     * create by: syy
     * description: 修改教师资料
     * create time: 2022/3/14 20:07
     *
      * @Param: null
     * @return
     */
    Integer updateTeacher(@Param("teacherId") Integer teacherId,
                          @Param("teacherName") String teacherName,
                          @Param("phone")String phone,
                          @Param("major")String major,
                          @Param("gender")String gender,
                          @Param("email")String email);

    /**
     * 获得所有老师信息
     * @return
     */
    List<Teacher> getTeachers();


    Integer updateRole(@Param("teacherId") Integer teacherId,
                       @Param("role") Integer role);
}