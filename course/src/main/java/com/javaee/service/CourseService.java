package com.javaee.service;

import com.javaee.entities.Chapter;
import com.javaee.entities.Course;
import com.javaee.entities.Modular;
import com.javaee.mapper.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/9 23:11
 * @Description
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseService {

    @Resource
    CourseMapper courseMapper;

    public boolean addCourse(String number, Integer credit, String type, String courseName, Integer mode, Integer createrId) {

        Integer id = null;
        Integer add = courseMapper.add(id, number, credit, type, courseName, mode, createrId);

        if (add == 1) {
            return true;
        } else {
            return false;
        }

    }

    public List<Course> getAllCourses() {

        List<Course> courses = courseMapper.selectAll();

        try {
            for (Course c : courses) {
                Integer courseId = c.getId();
                List<Chapter> chapters = courseMapper.getChapters(courseId);
                List<Modular> modulars = courseMapper.getModulars(courseId);
                c.setChapters(chapters);
                c.setModularise(modulars);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return courses;

    }

    public Course getCourse(Integer courseId) {

        Course course = courseMapper.getCourseById(courseId);
        List<Chapter> chapters = courseMapper.getChapters(courseId);
        List<Modular> modulars = courseMapper.getModulars(courseId);

        try {
            course.setChapters(chapters);
            course.setModularise(modulars);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return course;
    }

    public Integer deleteCourse(Integer courseId){
        Integer i = courseMapper.delete(courseId);
        return i;

    }

    public boolean addCourseToStu(Integer courseId, Integer stuId, Integer teaId) {

        Integer integer = null;

        try {
            integer = courseMapper.addCourseToStu(courseId, stuId, teaId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (integer == 1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean addModular(Integer courseId, String modularName) {

        Integer integer = null;

        try {
            integer = courseMapper.addModular(courseId, modularName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (integer == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addChapter(Integer courseId, String chapName) {

        Integer integer = null;

        try {
            integer = courseMapper.addChapter(courseId, chapName);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if (integer == 1) {
            return true;
        } else {
            return false;
        }

    }

    public List<Chapter> getAllChapters(Integer courseId) {

        List<Chapter> chapters = courseMapper.getChapters(courseId);

        if (chapters != null && chapters.size() != 0) {
            return chapters;
        } else {
            return null;
        }

    }

    public List<Modular> getAllModularise(Integer courseId) {

        List<Modular> modularise = courseMapper.getModulars(courseId);

        if (modularise != null && modularise.size() != 0) {
            return modularise;
        } else {
            return null;
        }

    }

    public boolean updateCourse(Integer id, String number, Integer credit, String type, String courseName, Integer mode) {

        Integer update = courseMapper.update(id, number, credit, type, courseName, mode);
        System.out.println("update = " + update);

        if (update > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Integer deleteChapter(Integer courseId, String chapName) {

        return courseMapper.deleteChapter(courseId, chapName);

    }

    public Integer deleteModular(Integer courseId, String modularName) {

        return courseMapper.deleteModular(courseId, modularName);

    }

    public boolean updateModular(Integer modularId, String modularName) {

        Integer updateModular = courseMapper.updateModular(modularId, modularName);

        if (updateModular != 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateChapter(Integer chapterId, String chapterName) {

        Integer updateModular = courseMapper.updateChapter(chapterId, chapterName);

        if (updateModular != 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean addCourseWithCM(Course course, List<Modular> modularList, List<Chapter> chapterList) {

        Integer integer = courseMapper.addCourseWithCM(course);
        Integer courseId = course.getId();

        this.addChapters(chapterList, courseId);
        this.addModulars(modularList, courseId);


        if (integer != 0) {
            return true;
        } else {
            return false;
        }

    }

    public void addChapters(List chapterList, Integer courseId) {

        for (int i = 0; i < chapterList.size(); i++) {
            courseMapper.addChapter(courseId, (String) chapterList.get(i));
        }

    }

    public void addModulars(List modularList, Integer courseId) {

        for (int i = 0; i < modularList.size(); i++) {
            courseMapper.addModular(courseId, (String) modularList.get(i));
        }

    }

    public List<Course> getCourses(Integer credit,String type,Integer sort) {

        List<Course> courses = courseMapper.selectWithCts(credit,type,sort);

        if (courses!=null&&courses.size()!=0){
            return courses;
        }else {
            return null;
        }

    }

    public List<Course> getCourses(Integer status,Integer credit,String type,Integer sort) {

        List<Course> courseByStatus = courseMapper.getCourseByStatus(status,credit,type,sort);

        if (courseByStatus!=null&&courseByStatus.size()!=0){
            return courseByStatus;
        }else {
            return null;
        }

    }

    public List<Course> getCourses(String courseName,Integer credit,String type,Integer sort) {

        List<Course> courseList = courseMapper.getCoursesLikeName(courseName,credit,type,sort);

        if (courseList!=null&&courseList.size()!=0){
            return courseList;
        }else {
            return null;
        }

    }

    public List<Course> getCourses(String courseName, Integer status,Integer credit,String type,Integer sort) {

        List<Course> courseList = courseMapper.getCoursesWithStatusAndName(courseName,status,credit,type,sort);

        if (courseList!=null&&courseList.size()!=0){
            return courseList;
        }else {
            return null;
        }

    }

    public boolean updateStatus(Integer courseId, Integer status) {

        Integer integer = courseMapper.updateStatus(courseId, status);

        if (integer==1){
            return true;
        }else {
            return false;
        }

    }
}