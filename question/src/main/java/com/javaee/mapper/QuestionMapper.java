package com.javaee.mapper;

import com.javaee.entities.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/6 21:52
 * @Description
 * @Version 1.0
 */
@Mapper
public interface QuestionMapper {
    /**
     * 新增记录
     * @param text
     * @param option1
     * @param option2
     * @param option3
     * @param option4
     * @param answer
     * @param createrId
     * @param createTime
     * @param chapterId
     * @param modularId
     * @param diffculyt
     * @return
     */
    Integer insert(@Param("id") Integer id,
                   @Param("text") String text,
                   @Param("option1")String option1,
                   @Param("option2")String option2,
                   @Param("option3")String option3,
                   @Param("option4")String option4,
                   @Param("answer")String answer,
                   @Param("createrId")Integer createrId,
                   @Param("courseId") Integer courseId,
                   @Param("createTime") Timestamp createTime,
                   @Param("chapterId")Integer chapterId,
                   @Param("modularId")Integer modularId,
                   @Param("diffculyt")Integer diffculyt);

    /**
     * 根据题目id查询出题目
     * @param id
     * @return
     */
    QuestionPublicSc get(@Param("id") Integer id);

    List<QuestionPublicSc> getQuestionByPaperId(@Param("id") Integer id);

    Integer updateScore(@Param("score") Integer score,
                        @Param("paperId") Integer paperId,
                        @Param("studentId") Integer studentId,
                        @Param("questionId") Integer questionId);

    int delete(@Param("id") Integer id);

    Integer update(@Param("id") Integer id,
                   @Param("text") String text,
                   @Param("option1")String option1,
                   @Param("option2")String option2,
                   @Param("option3")String option3,
                   @Param("option4")String option4,
                   @Param("answer")String answer,
                   @Param("chapterId")Integer chapterId,
                   @Param("modularId")Integer modularId,
                   @Param("diffculyt")Integer diffculyt);

    /**
     * 查询所有题目
     * @return
     */
    List<QuestionPublicSc> getAll();

    List<PaperAndQuestion> getPaperQuestion(@Param("paperId") Integer paperId);
    List<PaperAndQuestionTested> getQuestionTestedByPaperId(@Param("id") Integer id);
    List<PaperAndQuestionTestedCorrect> getQuestionTestedCorrectByPaperId(@Param("id") Integer id);

    /**
     * 通过userId获得当前用户的权限
     * @param userId
     * @return
     */
    Integer getUserRoleById(@Param("userId") Integer userId);

    Integer addPaperQuestion(@Param("paperId") Integer paperId,
                             @Param("questionType") Integer questionType,
                             @Param("questionId") Integer questionId,
                             @Param("mark") Integer mark,
                             @Param("questionIndex") Integer questionIndex,
                             @Param("privateQ") Integer privateQ);

    /**
     * 根绝课程id获得所有未审核的题目
     * @param courseId
     * @return
     */
    List<QuestionPublicSc> getQuesWithoutExamByCId(Integer courseId);

    /**
     * 更新题目审核状态
     * @param userId
     * @param questionId
     * @param result
     * @param examineTime
     * @return
     */
    Integer updateExamine(@Param("userId") Integer userId,
                          @Param("questionId") Integer questionId,
                          @Param("result") Integer result,
                          @Param("examineTime") Timestamp examineTime);

    /**
     * 添加选择题
     * @param content
     * @param answer1
     * @param answer2
     * @param answer3
     * @param createrId
     * @param createTime
     * @param chapterId
     * @param modularId
     * @param difficulty
     * @return
     */
    Integer insertComp(@Param("content") String content,
                       @Param("answer1") String answer1,
                       @Param("answer2") String answer2,
                       @Param("answer3") String answer3,
                       @Param("createrId") Integer createrId,
                       @Param("createTime") Timestamp createTime,
                       @Param("chapterId") Integer chapterId,
                       @Param("modularId") Integer modularId,
                       @Param("difficulty") Integer difficulty);

    /**
     * 删除选择题
     * @param id
     * @return
     */
    Integer deleteComp(@Param("id") Integer id);

    /**
     *  修改填空题
     * @param id
     * @param content
     * @param answer1
     * @param answer2
     * @param answer3
     * @param chapterId
     * @param modularId
     * @param difficulty
     * @return
     */
    Integer updateComp(@Param("id") Integer id,
                       @Param("content") String content,
                       @Param("answer1") String answer1,
                       @Param("answer2") String answer2,
                       @Param("answer3") String answer3,
                       @Param("chapterId") Integer chapterId,
                       @Param("modularId") Integer modularId,
                       @Param("difficulty") Integer difficulty);

    /**
     * 查询选择题
     * @param id
     * @return
     */
    QuestionPublicComp getComp(@Param("id") Integer id);

    List<QuestionPublicComp> getAllComp();

    /**
     *  根据条件获得所有选择题
     * @param courseId
     * @param chapterId
     * @param modularId
     * @param content
     * @return
     */
    List<QuestionPublicScWithName> getQuestionScInCondition(@Param("courseId") Integer courseId,
                                                            @Param("chapterId") Integer chapterId,
                                                            @Param("modularId") Integer modularId,
                                                            @Param("content") String content);

    /**
     * 根据条件获得所有填空题
     * @param courseId
     * @param chapterId
     * @param modularId
     * @param content
     * @return
     */
    List<QuestionPublicCompWithName> getQuestionCompInCondition(@Param("courseId") Integer courseId,
                                                                @Param("chapterId") Integer chapterId,
                                                                @Param("modularId") Integer modularId,
                                                                @Param("content") String content);

    /**
     * create by: Chalksyy
     * description: 获得所有未审核的选择题
     * create time: 2022/5/23 18:40
     * @return
     */
    List<QuestionPublicScWithName> getQuestionSc();

    /**
     * create by: Chalksyy
     * description: 获得所有未审核填空题
     * create time: 2022/5/23 18:45
     * @return
     */
    List<QuestionPublicCompWithName> getQuestionComp();
}
