package com.javaee.mapper;

import com.javaee.entities.Test;
import com.javaee.entities.TestExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestMapper {

    List<Test> getAllTestByCourseId(@Param("id") Integer id);
    List<Test> getAllTestByCourseIdTimeOut(@Param("id") Integer id);
    List<Test> getAllTestByCourseIdAfter(@Param("id") Integer id);
    List<Test> getAllTested(@Param("id") Integer id);

    long countByExample(TestExample example);

    int deleteByExample(TestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    List<Test> selectByExample(TestExample example);

    Test selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Test record, @Param("example") TestExample example);

    int updateByExample(@Param("record") Test record, @Param("example") TestExample example);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    int insertWithPaper(@Param("testId") int testId,
                        @Param("paperId") Integer paperId);
}