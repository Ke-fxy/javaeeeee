package com.javaee.service;

import com.javaee.entities.Test;
import com.javaee.entities.TestExample;
import com.javaee.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/24 21:23
 * @Description
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class TestService {

    @Resource
    TestMapper testMapper;

    public boolean addTest(Test test, Integer paperId) {

        int testId = testMapper.insertSelective(test);
        int i = testMapper.insertWithPaper(testId, paperId);

        if (i != 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteTest(Integer id) {

        int i = testMapper.deleteByPrimaryKey(id);

        if (i != 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean updateTest(Test test) {

        int i = testMapper.updateByPrimaryKeySelective(test);

        if (i != 0) {
            return true;
        } else {
            return false;
        }

    }

    public Test getTest(Integer id) {

        Test test = testMapper.selectByPrimaryKey(id);

        if (test != null) {
            return test;
        } else {
            return null;
        }

    }

    public List<Test> getAllTest() {

        List<Test> tests = testMapper.selectByExample(new TestExample());
        if (tests!=null&&tests.size()!=0){
            return tests;
        }else {
            return null;
        }

    }

    public List<Test> getAllTest(Integer createrId) {

        TestExample testExample = new TestExample();
        TestExample.Criteria criteria = testExample.createCriteria();
        criteria.andCreaterIdEqualTo(createrId);

        List<Test> tests = testMapper.selectByExample(testExample);

        if (tests!=null&&tests.size()!=0){
            return tests;
        }else {
            return null;
        }

    }
}