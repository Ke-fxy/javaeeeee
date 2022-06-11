package com.javaee.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        int i = testMapper.insertWithPaper(test.getId(), paperId);

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

    public PageInfo getAllTestByCourseId(Integer id,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Test> testList = testMapper.getAllTested(id);//正在考试的
        System.out.println("testList1:"+testList);
        //List<Test> testList = testMapper.getAllTestByCourseId(id);//
//        List<Test> testList1 = testMapper.getAllTested(id);
//        System.out.println("testList:"+testList);
       /* System.out.println("testList1:"+testList1);
        for (int i=0;i<testList.size();i++){
            for (int j = 0; j < testList1.size(); j++) {
                if (testList.get(i).getId().equals(testList1.get(j).getId())){
                    testList.remove(i);
                    i--;//不减减不行，list元素在减少
                    System.out.println("iiiiiiiiiiiiiiiiiiiii = " + i);

                }
            }

        }
        System.out.println("testList:"+testList);*/
        if (testList!=null&&testList.size()!=0){
            PageInfo pageInfo=new PageInfo(testList);
            return pageInfo;
        }else {
            return null;
        }

    }

    public PageInfo getAllTestByCourseIdTimeOut(Integer id,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Test> testList = testMapper.getAllTestByCourseIdTimeOut(id);
        List<Test> testList1 = testMapper.getAllTested(id);
        for (int i = 0; i < testList1.size(); i++) {
            testList.add(testList1.get(i));
        }
        if (testList!=null&&testList.size()!=0){
            PageInfo pageInfo=new PageInfo(testList);
            return pageInfo;
        }else {
            return null;
        }
    }

    public PageInfo getAllTestByCourseIdAfter(Integer id,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Test> testList = testMapper.getAllTestByCourseIdAfter(id);
        if (testList!=null&&testList.size()!=0){
            PageInfo pageInfo=new PageInfo(testList);
            return pageInfo;
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