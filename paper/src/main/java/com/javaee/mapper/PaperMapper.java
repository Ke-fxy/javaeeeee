package com.javaee.mapper;


import com.javaee.entities.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperMapper {

    Integer addPaper(Paper paper);

    Integer addPaperPublicSc(
            @Param("id") Integer id,
            @Param("questionId") Integer questionId,
            @Param("mark") Integer mark,
            @Param("index") Integer index);

    Integer addPaperPublicComp(
            @Param("id") Integer id,
            @Param("questionId") Integer questionId,
            @Param("mark") Integer mark,
            @Param("index") Integer index);

//    Integer addPaper(
//            @Param("id") Integer id,
//            @Param("questionId") Integer questionId,
//            @Param("mark") Integer mark,
//            @Param("index") Integer index);

    Integer deletePaper(@Param("id") Integer id);

    Integer updatePaper(Paper paper);

    Integer deletePaperQuestion(Integer paperId);

    Integer addPaperQuestion(@Param("id") Integer id,
                             @Param("questionType") Integer questionType,
                             @Param("privateType") Integer privateType,
                             @Param("questionId") Integer questionId,
                             @Param("mark") Integer mark,
                             @Param("index") Integer index);

    Paper getPaper(@Param("id") Integer id);

    List<Paper> getAll();

    Integer getSum(@Param("courseId") Integer courseId,
                   @Param("type") int type);

    List<Integer> getIdList(@Param("type") int type,
                            @Param("courseId") Integer courseId);

    @Select("select name from teacher where id=#{id}")
    String getCreaterName(@Param("id") Integer creatorId);

    @Select("select course_name from course where id=#{id}")
    String getCourseName(@Param("id") Integer courseId);
}
