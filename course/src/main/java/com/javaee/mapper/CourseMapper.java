package com.javaee.mapper;

import com.javaee.entities.Chapter;
import com.javaee.entities.Course;
import com.javaee.entities.Modular;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/9 23:11
 * @Description
 * @Version 1.0
 */
@Mapper
public interface CourseMapper {

    /**
     * 添加课程
     *
     * @param id
     * @param number
     * @param credit
     * @param type
     * @param courseName
     * @param mode
     * @param createrId
     * @return
     */
    Integer add(@Param("id") Integer id,
                @Param("number") String number,
                @Param("credit") Integer credit,
                @Param("type") String type,
                @Param("courseName") String courseName,
                @Param("mode") Integer mode,
                @Param("createrId") Integer createrId);


    /**
     * 获得全部课程信息
     * @return
     */
    List<Course> selectAll();

    Integer update(@Param("id") Integer id,
                @Param("number") String number,
                @Param("credit") Integer credit,
                @Param("type") String type,
                @Param("courseName") String courseName,
                @Param("mode") Integer mode);

    /**
     * create by: syy
     * description: TODO
     * create time: 2022/3/19 22:43
     */
    Integer delete(@Param("id") Integer id);

    /**
     * 根据课程id获得章节
     *
     * @param courseId
     * @return
     */
    List<Chapter> getChapters(@Param("courseId") Integer courseId);

    /**
     * 根据课程id获得模块
     *
     * @param courseId
     * @return
     */
    List<Modular> getModulars(@Param("courseId") Integer courseId);

    /**
     * 根据上传的id获得课程
     *
     * @param courseId
     * @return
     */
    Course getCourseById(@Param("courseId") Integer courseId);

    /**
     * 根据courseId获得chapter
     *
     * @param courseId
     * @return
     */
    Chapter getChapter(@Param("courseId") Integer courseId);

    /**
     * 为课程学生老师表添加记录
     *
     * @param courseId
     * @param stuId
     * @param teaId
     * @return
     */
    Integer addCourseToStu(@Param("courseId") Integer courseId,
                           @Param("studentId") Integer stuId,
                           @Param("teacherId") Integer teaId);

    /**
     * 为课程添加模块
     *
     * @param courseId
     * @param modularName
     * @return
     */
    Integer addModular(@Param("courseId") Integer courseId,
                       @Param("modularName") String modularName);


    /**
     * 为课程添加章节
     *
     * @param courseId
     * @param chapName
     * @return
     */
    Integer addChapter(@Param("courseId") Integer courseId,
                       @Param("chapName") String chapName);

    /**
     * create by: syy
     * description: 删除章节
     * create time: 2022/3/11 11:35
     *
     * @Param: courseId
     * @Param: chapName
     * @return
     */
    Integer deleteChapter(@Param("courseId") Integer courseId,
                       @Param("chapName") String chapName);

    /**
     * create by: syy
     * description: 删除模块
     * create time: 2022/3/11 14:00
     *
      * @Param: null
     * @return
     */
    Integer deleteModular(@Param("courseId") Integer courseId,
                          @Param("modularName") String modularName);

    /**
     * 根据id更新模块信息
     * @param modularId
     * @param modularName
     * @return
     */
    Integer updateModular(@Param("modularId") Integer modularId,
                          @Param("modularName") String modularName);

    /**
     * 根据id更新章节信息
     * @param chapterId
     * @param chapterName
     * @return
     */
    Integer updateChapter(@Param("chapterId") Integer chapterId,
                          @Param("chapterName") String chapterName);

    /**
     * 创建课程的另一种方式
     * @param course
     * @return
     */
    Integer addCourseWithCM(Course course);

    /**
     * 查询所有状态为status的课程
     * @param status
     * @return
     */
    List<Course> getCourseByStatus(@Param("status") Integer status,
                                   @Param("credit") Integer credit,
                                   @Param("type") String type,
                                   @Param("sort") Integer sort);

    /**
     * 查询和courseName相似的课程
     * @param courseName
     * @return
     */
    List<Course> getCoursesLikeName(@Param("courseName") String courseName,
                                    @Param("credit") Integer credit,
                                    @Param("type") String type,
                                    @Param("sort") Integer sort);

    /**
     * 查询状态为status以及名字相似的课程
     * @param courseName
     * @param status
     * @return
     */
    List<Course> getCoursesWithStatusAndName(@Param("courseName") String courseName,
                                             @Param("status") Integer status,
                                             @Param("credit") Integer credit,
                                             @Param("type") String type,
                                             @Param("sort") Integer sort);

    /**
     * 报错
     * @param credit
     * @param type
     * @param sort
     * @return
     */
    List<Course> selectWithCts(@Param("credit") Integer credit,
                               @Param("type") String type,
                               @Param("sort") Integer sort);

    /**
     * 好噶哈哈
     * @param courseId
     * @return
     */
    Integer updateStatus(@Param("courseId") Integer courseId,
                         @Param("status") Integer status);
}