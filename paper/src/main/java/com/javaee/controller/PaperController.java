package com.javaee.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.javaee.entities.*;
import com.javaee.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/paper")
@CrossOrigin(maxAge = 3600,value = "*")
public class PaperController {

    @Resource
    PaperService paperService;


    @Resource
    StringRedisTemplate stringRedisTemplate;

    private String checkup(String token) {

        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        String s = forValue.get("userToken:" + token);
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;
    }

    @RequestMapping("/addPaper")
    public CommonResult<Integer> addPaper(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        String paperName = (String) map.get("paperName");
        Integer maxMark = Integer.parseInt(map.get("maxMark").toString());
        Integer creatorId = Integer.parseInt(checkup);
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        Integer courseId = Integer.parseInt(map.get("courseId").toString());
        Integer change = Integer.parseInt(map.get("change").toString());

        Paper paper = new Paper(null, paperName, maxMark, creatorId, courseId, createTime, change);
        Integer result = paperService.addPaper(paper);
        Integer paperId = paper.getId();

        System.out.println("paperId = " + paperId);


//        System.out.println("paperId = " + paperId);
        return paperId > 0 ? new CommonResult<>(100, "添加成功",paperId) : new CommonResult<>(200, "添加失败");
    }

    @RequestMapping("/addPaperQuestion")
    public CommonResult<String> addPaperQuestion(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        List<Map> questionList = (List<Map>) map.get("questionList");

        Integer result = paperService.addPaperQuestion(paperId, questionList);

        return result > 0 ? new CommonResult<>(100, "添加成功") : new CommonResult<>(200, "添加失败");
    }

    @RequestMapping("/deletePaper")
    public CommonResult<String> deletePaper(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        Integer paperId = Integer.parseInt(map.get("paperId").toString());


        Integer result = paperService.deletePaper(paperId);

        return result > 0 ? new CommonResult<>(100, "删除成功") : new CommonResult<>(200, "删除失败");
    }

    @RequestMapping("/updatePaper")
    public CommonResult<String> updatePaper(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());
        String paperName = (String) map.get("paperName");
        Integer maxMark = Integer.parseInt(map.get("maxMark").toString());
        Integer creatorId = Integer.parseInt(checkup);
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        Integer courseId = Integer.parseInt(map.get("courseId").toString());
        Integer change = Integer.parseInt(map.get("change").toString());

        Paper paper = new Paper(paperId, paperName, maxMark, creatorId, courseId, createTime, change);
        Integer result = paperService.updatePaper(paper);

        return result > 0 ? new CommonResult<>(100, "修改成功") : new CommonResult<>(200, "修改失败");
    }

    @RequestMapping("/getPaper")
    public CommonResult<Map<String,Object>> getPaper(@RequestBody HashMap<String, Object> map) {
        System.out.println("map = " + map);
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        Paper paper = paperService.getPaper(paperId);
        List<QuestionPublicSc> questionPublicScList = paperService.getQuestionByPaperId(paperId);

//        PaperAndQuestion paperAndQuestion = new PaperAndQuestion(paper.getId(),paper.getPaperName(),paper.getMaxMark(),paper.getCreatorId(),paper.getCourseId(),paper.getCreateTime(),paper.getChange(),null);

        List<PaperAndQuestion> paperAndQuestions = paperService.getPaperQuestion(paperId);

        List<QuestionList> questionList = new ArrayList<>();

        for (int i = 0;i<questionPublicScList.size();i++){
            QuestionList question = new QuestionList();
            question.setAnswer(questionPublicScList.get(i).getAnswer());
            question.setQuestionId(questionPublicScList.get(i).getId());
            question.setText(questionPublicScList.get(i).getText());
            question.setOption1(questionPublicScList.get(i).getOption1());
            question.setOption2(questionPublicScList.get(i).getOption2());
            question.setOption3(questionPublicScList.get(i).getOption3());
            question.setOption4(questionPublicScList.get(i).getOption4());
            question.setPaperId(paper.getId());
            question.setMaxMark(paperAndQuestions.get(i).getMark());
//            question.setCourseId(paper.getCourseId());
            question.setQuestionIndex(paperAndQuestions.get(i).getQuestionIndex());
            question.setType(paperAndQuestions.get(i).getQuestionType());
            questionList.add(question);
        }

        System.out.println("questionList = " + questionList);
        Map<String,Object> map1 = new HashMap();
        map1.put("paper",paper);
        map1.put("list",questionList);

        if (paper != null) {
            return new CommonResult<>(100, "查找成功", map1);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }

    @RequestMapping("/getPaperAndQuestionTested")
    public CommonResult<Map<String,Object>> getPaperAndQuestionTested(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        Paper paper = paperService.getPaper(paperId);
        List<QuestionPublicSc> questionPublicScList = paperService.getQuestionByPaperId(paperId);

//        PaperAndQuestion paperAndQuestion = new PaperAndQuestion(paper.getId(),paper.getPaperName(),paper.getMaxMark(),paper.getCreatorId(),paper.getCourseId(),paper.getCreateTime(),paper.getChange(),null);

        System.out.println("paperId = " + paperId);
        List<PaperAndQuestionTested> paperAndQuestions = paperService.getPaperQuestionTested(paperId);

        System.out.println("paperAndQuestions = " + paperAndQuestions);
        List<QuestionList> questionList = new ArrayList<>();

        for (int i = 0;i<questionPublicScList.size();i++){
            QuestionList question = new QuestionList();
            question.setAnswer(questionPublicScList.get(i).getAnswer());
            question.setQuestionId(questionPublicScList.get(i).getId());
            question.setText(questionPublicScList.get(i).getText());
            question.setOption1(questionPublicScList.get(i).getOption1());
            question.setOption2(questionPublicScList.get(i).getOption2());
            question.setOption3(questionPublicScList.get(i).getOption3());
            question.setOption4(questionPublicScList.get(i).getOption4());
            question.setPaperId(paper.getId());
            question.setMaxMark(paperAndQuestions.get(i).getMark());
//            question.setCourseId(paper.getCourseId());
            question.setQuestionIndex(paperAndQuestions.get(i).getQuestionIndex());
            question.setType(paperAndQuestions.get(i).getQuestionType());
            question.setResult(paperAndQuestions.get(i).getResult());
            question.setPaperQuestionTestedId(paperAndQuestions.get(i).getId());
            questionList.add(question);
        }

        Map<String,Object> map1 = new HashMap();
        map1.put("paper",paper);
        map1.put("list",questionList);

        if (paper != null) {
            return new CommonResult<>(100, "查找成功", map1);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }

    @RequestMapping("/getPaperAndQuestionTestedCorrect")
    public CommonResult<Map<String,Object>> getPaperAndQuestionTestedCorrect(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        Paper paper = paperService.getPaper(paperId);
        List<QuestionPublicSc> questionPublicScList = paperService.getQuestionByPaperId(paperId);

//        PaperAndQuestion paperAndQuestion = new PaperAndQuestion(paper.getId(),paper.getPaperName(),paper.getMaxMark(),paper.getCreatorId(),paper.getCourseId(),paper.getCreateTime(),paper.getChange(),null);

        System.out.println("paperId = " + paperId);
        List<PaperAndQuestionTestedCorrect> paperAndQuestions = paperService.getQuestionTestedCorrectByPaperId(paperId);

        System.out.println("paperAndQuestions = " + paperAndQuestions);
        List<QuestionList> questionList = new ArrayList<>();

        for (int i = 0;i<questionPublicScList.size();i++){
            QuestionList question = new QuestionList();
            question.setAnswer(questionPublicScList.get(i).getAnswer());
            question.setQuestionId(questionPublicScList.get(i).getId());
            question.setText(questionPublicScList.get(i).getText());
            question.setOption1(questionPublicScList.get(i).getOption1());
            question.setOption2(questionPublicScList.get(i).getOption2());
            question.setOption3(questionPublicScList.get(i).getOption3());
            question.setOption4(questionPublicScList.get(i).getOption4());
            question.setPaperId(paper.getId());
            question.setMaxMark(paperAndQuestions.get(i).getFullMark());
//            question.setCourseId(paper.getCourseId());
            question.setQuestionIndex(paperAndQuestions.get(i).getQuestionIndex());
            question.setType(paperAndQuestions.get(i).getQuestionType());
            question.setResult(paperAndQuestions.get(i).getResult());
            question.setPaperQuestionTestedId(paperAndQuestions.get(i).getId());
            question.setScore(paperAndQuestions.get(i).getScore());
            questionList.add(question);
        }

        Map<String,Object> map1 = new HashMap();
        map1.put("paper",paper);
        map1.put("list",questionList);

        if (paper != null) {
            return new CommonResult<>(100, "查找成功", map1);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }

    @RequestMapping("/getAllPaper")
    public CommonResult<Object> getAllPaper(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer pageNum = Integer.parseInt(map.get("pageNum").toString());

        Integer pageSize = Integer.parseInt(map.get("pageSize").toString());



        PageInfo<Paper> pageInfo = paperService.getAllPaper(pageNum, pageSize);

        if (pageInfo != null) {
            return new CommonResult<>(100, "查找成功", pageInfo);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }

    @RequestMapping("/getPaperTested")
    public CommonResult<Object> getPaperTested(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer pageNum = Integer.parseInt(map.get("pageNum").toString());

        Integer pageSize = Integer.parseInt(map.get("pageSize").toString());


        PageInfo<Paper> pageInfo = paperService.getPaperTested(pageNum, pageSize);

        if (pageInfo != null) {
            return new CommonResult<>(100, "查找成功", pageInfo);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }

    @RequestMapping("/getAllPaper2")
    public CommonResult<List<Paper>> getAllPaper2(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }


        List<Paper> paperList = paperService.getAllPaper2();

        if (paperList != null) {
            return new CommonResult<>(100, "查找成功", paperList);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }
    @RequestMapping("/getAllPaperByCourseId")
    public CommonResult<List<Paper>> getAllPaperByCourseId(@RequestBody HashMap<String, String> map) {
        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer courseId = Integer.parseInt(map.get("courseId"));
        List<Paper> paperList = paperService.getAllPaperByCourseId(courseId);

        if (paperList != null) {
            return new CommonResult<>(100, "查找成功", paperList);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }
    @RequestMapping("/updatePaperQuestion")
    public CommonResult<String> updatePaperQuestion(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        List<Map> questionList = (List<Map>) map.get("questionList");

        Integer result1 = paperService.deletePaperQuestion(paperId);

        Integer result2 = paperService.addPaperQuestion(paperId, questionList);

        return result1 * result2 > 0 ? new CommonResult<>(100, "修改成功") : new CommonResult<>(200, "修改失败");
    }

    @RequestMapping("/submitPaper")
    public CommonResult<String> submitPaper(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        //paperId, questionIndex, result, studentId, score, fullMark, questionId, questionType
        Integer studentId = Integer.parseInt(checkup);
        Integer testId = Integer.parseInt(map.get("testId").toString());
        List<SubmitPaper> questionList = (List<SubmitPaper>) map.get("list");
        List<SubmitPaper> questionList2 = new ArrayList<>();
        for(Object object:questionList){
            // 将list中的数据转成json字符串
            String jsonObject= JSON.toJSONString(object);
            //将json转成需要的对象
            SubmitPaper submitPaperTested2= JSONObject.parseObject(jsonObject,SubmitPaper.class);
            questionList2.add(submitPaperTested2);
        }


        Integer paperId = null;
        Integer questionIndex = null;
        String result = null;
        Integer questionId = null;
        Integer questionType = null;
        Integer score = null;
        Integer fullMark = null;

        Integer result2 = 1;
        for (int i = 0; i < questionList2.size(); i++) {
            paperId = questionList2.get(i).getPaperId();
            questionId = questionList2.get(i).getQuestionId();
            questionIndex = questionList2.get(i).getQuestionIndex();
            score = questionList2.get(i).getScore();
            result = questionList2.get(i).getResult();
            fullMark = questionList2.get(i).getFullMark();
            questionType = questionList2.get(i).getQuestionType();

            result2 = paperService.submitPaper(paperId,questionIndex,result,studentId,score,fullMark,questionId,questionType) * result2;
        }


        Integer result3 = paperService.insertTested(testId,studentId);

        return result2 * result3 > 0 ? new CommonResult<>(100, "交卷成功") : new CommonResult<>(200, "交卷失败");
    }

    @RequestMapping("/submitPaperTested")
    public CommonResult<String> submitPaperTested(@RequestBody HashMap<String, Object> map) {
//        System.out.println("map = " + map);
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }
        //paperId, questionIndex, result, studentId, score, fullMark, questionId, questionType
        Integer studentId = Integer.parseInt(checkup);
        List<submitPaperTested> questionList = (List<submitPaperTested>) map.get("list");
//        System.out.println("questionList = " + questionList);
        List<submitPaperTested> questionList2 = new ArrayList<>();
        for(Object object:questionList){
            // 将list中的数据转成json字符串
            String jsonObject= JSON.toJSONString(object);
            //将json转成需要的对象
            submitPaperTested submitPaperTested2= JSONObject.parseObject(jsonObject,submitPaperTested.class);
            questionList2.add(submitPaperTested2);
//            System.out.println(courseInfo);
        }
        Integer paperId = null;
        Integer paperQuestionTestedId = null;
        Integer score = null;
        Integer questionId = null;

        Integer result = 1;
//        System.out.println("paperQuestionList2 = " + paperQuestionList2);
        for (int i = 0; i < questionList2.size(); i++) {
            paperId = questionList2.get(i).getPaperId();
            questionId = questionList2.get(i).getQuestionId();
            paperQuestionTestedId = questionList2.get(i).getPaperQuestionTestedId();
            score = questionList2.get(i).getScore();

            result = paperService.updateScore(score,paperId,studentId,questionId) * result;
        }

        return result > 0 ? new CommonResult<>(100, "批改成功") : new CommonResult<>(200, "批改失败");
//        return new CommonResult<>(100, "交卷成功");
    }

    @PostMapping(value = "/autoInsert")
    public CommonResult<String> autoInsert(@RequestBody HashMap<String, Object> map) {

        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer choiceNum = null;
        Integer choiceScore = null;
        Integer comNum = null;
        Integer comScore = null;
        Integer courseId=null;
        try {
            choiceNum = (Integer) map.get("choiceNum");
            choiceScore = (Integer) map.get("choiceScore");
            comNum = (Integer) map.get("comNum");
            comScore = (Integer) map.get("comScore");
            courseId = (Integer)map.get("courseId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        paperService.addPaperQuestionAuto(choiceNum,choiceScore,comNum,comScore,courseId);
        return null;


    }

}