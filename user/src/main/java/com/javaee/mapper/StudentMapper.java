package com.javaee.mapper;

import com.javaee.entities.Student;
import com.javaee.entities.StudentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    long countByExample(StudentExample example);

    int deleteByExample(StudentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    List<Student> selectByExample(StudentExample example);

    Student selectByPrimaryKey(Integer id);

    int updatePassword(@Param("id") Integer id,@Param("password") String password);

    int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByExample(@Param("record") Student record, @Param("example") StudentExample example);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    Integer updateRole(@Param("studentId") Integer teacherId,
                       @Param("role") Integer role);

    /**
     * create by: Chalksyy
     * description: 删除学生
     * create time: 2022/5/19 19:51
     * @return
     */
    Integer delete(@Param("id") Integer id);

    /**
     * create by: Chalksyy
     * description: 根据学号获取id
     * create time: 2022/6/4 15:50
     * @return
     */
    Integer getIdBySno(@Param("sno") Integer sno);

    /**
     * create by: Chalksyy
     * description: 通过id获取学生信息
     * create time: 2022/5/19 17:46
     * @return
     */
    Student getById(@Param("id") Integer id);

    List<Student> getAll(@Param("numStr") String numStr,
                         @Param("nameStr") String nameStr);

    List<Student> getAllByTeacherId(@Param("id") Integer id,
                                    @Param("numStr") String numStr,
                                    @Param("nameStr") String nameStr);

    Integer update(@Param("id") Integer id,
                   @Param("sno") Integer sno,
                   @Param("name") String name,
                   @Param("phone") String phone,
                   @Param("password") String password,
                   @Param("gender") Integer gender);
}