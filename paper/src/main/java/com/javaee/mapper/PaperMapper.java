package com.javaee.mapper;

import com.javaee.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperMapper {

    Integer addPaper(Paper paper);

    Integer submitPaper(@Param("paperId") Integer paperId,
                        @Param("questionIndex") Integer questionIndex,
                        @Param("result") String result,
                        @Param("studentId") Integer studentId,
                        @Param("score") Integer score,
                        @Param("fullMark") Integer fullMark,
                        @Param("questionId") Integer questionId,
                        @Param("questionType") Integer questionType);

    List<SubmitPaper> getPaperTested();

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

    Integer insertTested(@Param("testId") Integer testId,
                         @Param("studentId") Integer studentId);

    List<Paper> getAll();
    List<Paper> getAllByCourseId(@Param("courseId") Integer courseId);

    Integer getSum(@Param("courseId") Integer courseId,
                   @Param("type") int type);

    List<PaperAndQuestion> getPaperQuestion(@Param("paperId") Integer paperId);
    List<PaperAndQuestionTested> getQuestionTestedByPaperId(@Param("id") Integer id);
    List<PaperAndQuestionTestedCorrect> getQuestionTestedCorrectByPaperId(@Param("id") Integer id);

    List<QuestionPublicSc> getQuestionByPaperId(@Param("id") Integer id);

    Integer updateScore(@Param("score") Integer score,
                        @Param("paperId") Integer paperId,
                        @Param("studentId") Integer studentId,
                        @Param("questionId") Integer questionId);
}
