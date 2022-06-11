package com.javaee.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.entities.*;
import com.javaee.mapper.PaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaperService {

    @Resource
    PaperMapper paperMapper;

    public Integer addPaper(Paper paper) {

        Integer result = paperMapper.addPaper(paper);

        return result;
    }

    public Integer submitPaper(Integer paperId,Integer questionIndex,String result,Integer studentId,
                               Integer score,Integer fullMark,Integer questionId,Integer questionType){
        return paperMapper.submitPaper(paperId, questionIndex, result, studentId, score, fullMark, questionId, questionType);
    }

    public Integer addPaperQuestion(Integer paperId, List<Map> questionList) {

        Integer result1 = 0;
        Integer result2 = 0;

        for (int i = 0; i <questionList.size() ; i++) {
            String questionType = questionList.get(i).get("questionType").toString();
            String privateType = questionList.get(i).get("private").toString();

            if (questionType.equals("1") && privateType.equals("0")) {

                result1 = paperMapper.addPaperQuestion(paperId,
                        1,
                        0,
                        Integer.parseInt(questionList.get(i).get("questionId").toString()),
                        Integer.parseInt(questionList.get(i).get("mark").toString()),
                        Integer.parseInt(questionList.get(i).get("index").toString()));
            } else if (questionType.equals("4") && privateType.equals("0")) {
                result2 = paperMapper.addPaperQuestion(paperId,
                        4,
                        0,
                        Integer.parseInt(questionList.get(i).get("questionId").toString()),
                        Integer.parseInt(questionList.get(i).get("mark").toString()),
                        Integer.parseInt(questionList.get(i).get("index").toString()));
            }
        }
        return result1*result2;
    }

    public PageInfo getAllPaper(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        List<Paper> papers = paperMapper.getAll();

        if (papers!=null&&papers.size()!=0){
            PageInfo pageInfo=new PageInfo(papers);
            return pageInfo;
        }else {
            return null;
        }

    }
    public PageInfo getPaperTested(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);

        List<SubmitPaper> papers = paperMapper.getPaperTested();

        if (papers!=null&&papers.size()!=0){
            PageInfo pageInfo=new PageInfo(papers);
            return pageInfo;
        }else {
            return null;
        }
    }
    public List<Paper> getAllPaper2() {


        return paperMapper.getAll();


    }
    public List<Paper> getAllPaperByCourseId(Integer courseId) {


        return paperMapper.getAllByCourseId(courseId);


    }
    public Integer deletePaper(Integer paperId) {

        Integer result = paperMapper.deletePaper(paperId);

        return result;
    }

    public Integer updatePaper(Paper paper) {

        Integer result = paperMapper.updatePaper(paper);

        return result;
    }

    public Integer deletePaperQuestion(Integer paperId){
        return paperMapper.deletePaperQuestion(paperId);
    }

    public Paper getPaper(Integer paperId) {
        Paper paper = paperMapper.getPaper(paperId);
        return paper;
    }

    public boolean addPaperQuestionAuto(Integer choiceNum, Integer choiceScore, Integer comNum, Integer comScore,Integer courseId) {

        Integer choiceSum = paperMapper.getSum(courseId, 1);

        return false;
    }

    public Integer insertTested(Integer testId,Integer studentId){
     return paperMapper.insertTested(testId, studentId);
    }

//    public List<Paper> getAllPaper() {
//        List<Paper> paperList = paperMapper.getAllPaper();
//        return paperList;
//    }


    public List<QuestionPublicSc> getQuestionByPaperId(Integer id){
        return paperMapper.getQuestionByPaperId(id);
    }

    public List<PaperAndQuestion> getPaperQuestion(Integer paperId){
//        System.out.println("paperMapper.getPaperQuestion(paperId):"+paperMapper.getPaperQuestion(paperId));
        return paperMapper.getPaperQuestion(paperId);
    }

    public List<PaperAndQuestionTested> getPaperQuestionTested(Integer paperId){
//        System.out.println("paperMapper.getPaperQuestion(paperId):"+paperMapper.getPaperQuestion(paperId));
        return paperMapper.getQuestionTestedByPaperId(paperId);
    }

    public List<PaperAndQuestionTestedCorrect> getQuestionTestedCorrectByPaperId(Integer paperId){
//        System.out.println("paperMapper.getPaperQuestion(paperId):"+paperMapper.getPaperQuestion(paperId));
        return paperMapper.getQuestionTestedCorrectByPaperId(paperId);
    }

    public Integer updateScore(Integer score,Integer paperId,Integer studentId,Integer questionId){
        return paperMapper.updateScore(score, paperId, studentId, questionId);
    }

}
