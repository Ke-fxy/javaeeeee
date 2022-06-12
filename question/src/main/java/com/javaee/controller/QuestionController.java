package com.javaee.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.entities.*;
import com.javaee.service.QuestionService;
import com.javaee.util.JavaWebToken;
import com.javaee.util.ObjectTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/6 21:21
 * @Description
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping(value = "/question")
@CrossOrigin(maxAge = 3600, value = "*")
public class QuestionController {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    QuestionService questionService;

    private String checkup(String token) {

        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        String s = forValue.get("userToken:" + token);
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;

    }


    @PostMapping(value = "/choice/addQuestion")
    public CommonResult<String> addQuestion(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        String text = map.get("text");
        String option1 = map.get("option1");
        String option2 = map.get("option2");
        String option3 = map.get("option3");
        String option4 = map.get("option4");
        String answer = map.get("answer");
        //Integer createrId = Integer.valueOf(map.get("createrId"));
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        System.out.println(createTime);
        Integer chapterId = Integer.valueOf(map.get("chapterId"));
        Integer modularId = Integer.valueOf(map.get("modularId"));
        Integer diffculyt = Integer.valueOf(map.get("diffculyt"));
        Integer courseId = Integer.valueOf(map.get("courseId"));

        Integer createrId = Integer.parseInt(checkup);

        Integer integer = questionService.addQuestion(null, text, option1, option2, option3, option4, answer, createrId, courseId, createTime, chapterId, modularId, diffculyt);

        if (integer == 0) {
            return new CommonResult<>(200, "插入失败");
        } else {
            return new CommonResult<>(100, "插入成功");
        }


    }

    @PostMapping(value = "/choice/getQuestion")
    public CommonResult<QuestionPublicSc> getQuestionById(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer id = Integer.parseInt(map.get("id"));
        QuestionPublicSc questionById = questionService.getQuestionById(id);

        if (questionById != null) {
            return new CommonResult<>(100, "查询成功", questionById);
        } else {
            return new CommonResult<>(200, "未查询到此题", null);
        }

    }

    @PostMapping(value = "/choice/getAllQuestion")
    public CommonResult<List<QuestionPublicSc>> getAllQuestion(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult(200, "用户未登录或登录状态失效", null);
        }

        List<QuestionPublicSc> allQuestion = questionService.getAllSC();

        if (allQuestion != null && allQuestion.size() != 0) {
            return new CommonResult<>(100, "查询成功", allQuestion);
        } else {
            return new CommonResult<>(200, "查询失败");
        }
    }

    @PostMapping(value = "/choice/checkUser")
    public CommonResult<String> checkUser(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult(200, "用户未登录或登录状态失效", null);
        }

        Integer userId = Integer.parseInt(checkup);
        boolean b = questionService.checkUser(userId);

        if (b) {
            return new CommonResult<>(100, "当前用户可上传题目");
        } else {
            return new CommonResult<>(200, "当前用户无法上传题目");
        }
    }

    @PostMapping(value = "/choice/deleteQuestion")
    public CommonResult deleteQuestion(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult(200, "用户未登录或登录状态失效", null);
        }

        Integer choiceId = Integer.parseInt(map.get("choiceId"));

        if (choiceId != null) {
            int result = questionService.deleteQuestion(choiceId);
            if (result > 0) {
                return new CommonResult(100, "删除成功");
            } else {
                return new CommonResult(200, "删除失败", null);
            }
        } else {
            return new CommonResult(200, "未查询到此题", null);
        }
    }

    @PostMapping(value = "/choice/updateQuestion")
    public CommonResult<QuestionPublicSc> updateQuestion(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);
        String text = map.get("text");
        String option1 = map.get("option1");
        String option2 = map.get("option2");
        String option3 = map.get("option3");
        String option4 = map.get("option4");
        String answer = map.get("answer");
        //Integer createrId = Integer.valueOf(map.get("createrId"));
//        Timestamp createTime = new Timestamp(System.currentTimeMillis());
//        System.out.println(createTime);
        Integer chapterId = Integer.valueOf(map.get("chapterId"));
        Integer modularId = Integer.valueOf(map.get("modularId"));
        Integer diffculyt = Integer.valueOf(map.get("diffculyt"));

        if (checkup == null) {
            return new CommonResult(200, "用户未登录或登录状态失效", null);
        }

        Integer id = Integer.parseInt(map.get("id"));

        QuestionPublicSc question = questionService.getQuestionById(id);

        if (question != null) {
            int result = questionService.updateQuestion(id, text, option1, option2, option3, option4, answer, chapterId, modularId, diffculyt);
            QuestionPublicSc newquestion = questionService.getQuestionById(id);
            if (result > 0) {
                return new CommonResult(100, "更新成功", newquestion);
            } else {
                return new CommonResult(200, "更新失败", newquestion);
            }
        } else {
            return new CommonResult(200, "未查询到此题", null);
        }

    }

    @PostMapping(value = "/checkUser2")
    public CommonResult<String> checkUser2(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer userId = Integer.parseInt(checkup);
        boolean b = questionService.checkUser2(userId);

        if (b) {
            return new CommonResult<>(100, "当前用户可审核题目");
        } else {
            return new CommonResult<>(200, "当前用户无法审核题目");
        }

    }

    @PostMapping(value = "/getNoExamineQuestion")
    public CommonResult<List<QuestionPublicSc>> getNoExamineQuestion(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult(200, "用户未登录或登录状态失效", null);
        }

        Integer courseId = Integer.parseInt(map.get("courseId"));
        List<QuestionPublicSc> questionWithoutExamine = questionService.getQuestionWithoutExamine(courseId);

        if (questionWithoutExamine != null && questionWithoutExamine.size() != 0) {
            return new CommonResult<>(100, "查询成功", questionWithoutExamine);
        } else {
            return new CommonResult<>(200, "查询失败，当前课程没有题目可以审核");
        }

    }

    @PostMapping(value = "/examineQuestion")
    public CommonResult<String> examine(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        Integer userId = (Integer) JavaWebToken.parserJavaWebToken(token).get("id");
        Integer questionId = Integer.parseInt(map.get("questionId"));
        /**
         * 审核结果（通过1/未通过2）
         */
        Integer result = Integer.parseInt(map.get("result"));
        boolean examineQuestion = questionService.examineQuestion(userId, questionId, result);

        if (examineQuestion) {
            return new CommonResult<>(100, "更新成功");
        }

        return new CommonResult<>(200, "更新失败");
    }

    @PostMapping(value = "/completion/addQuestion")
    public CommonResult<String> addQuestionCp(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        String content = map.get("content");
        String answer1 = map.get("answer1");
        String answer2 = map.get("answer2");
        String answer3 = map.get("answer3");
        Integer createrId = Integer.parseInt(checkup);
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        Integer chapterId = Integer.parseInt(map.get("chapterId"));
        Integer modularId = Integer.parseInt(map.get("modularId"));

        Integer difficulty = null;
        try {
            difficulty = Integer.parseInt(map.get("difficulty"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            difficulty = 1;
        }

        boolean b = questionService.addQuestionComp(content, answer1, answer2, answer3, createrId, createTime, chapterId, modularId, difficulty);

        if (b) {
            return new CommonResult<>(100, "添加成功");
        } else {
            return new CommonResult<>(200, "添加失败");
        }

    }

    @PostMapping(value = "/completion/deleteQuestion")
    public CommonResult<String> deleteQuestionCp(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        Integer id = Integer.parseInt(map.get("id"));
        Integer result = questionService.deleteComp(id);

        if (result > 0) {
            return new CommonResult<>(100, "删除成功");
        } else {
            return new CommonResult<>(200, "删除失败");
        }

    }

    @PostMapping(value = "/completion/updateQuestion")
    public CommonResult<String> updateQuestionCp(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        String content = map.get("content");
        String answer1 = map.get("answer1");
        String answer2 = map.get("answer2");
        String answer3 = map.get("answer3");
        Integer id = Integer.parseInt(map.get("id"));
        Integer chapterId = Integer.parseInt(map.get("chapterId"));
        Integer modularId = Integer.parseInt(map.get("modularId"));
        Integer difficulty = Integer.parseInt(map.get("difficulty"));

        Integer result = questionService.updateComp(id, content, answer1, answer2, answer3, chapterId, modularId, difficulty);

        if (result > 0) {
            return new CommonResult<>(100, "修改成功");
        } else {
            return new CommonResult<>(200, "修改失败");
        }

    }


    @PostMapping(value = "/completion/getQuestion")
    public CommonResult<QuestionPublicComp> getQuestionCp(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        Integer id = Integer.parseInt(map.get("id"));

        QuestionPublicComp questionPublicComp = questionService.getCompById(id);

        if (questionPublicComp != null) {
            return new CommonResult<QuestionPublicComp>(100, "获取成功", questionPublicComp);
        } else {
            return new CommonResult<QuestionPublicComp>(200, "获取失败", null);
        }

    }

    @PostMapping(value = "/completion/getAllQuestion")
    public CommonResult<List<QuestionPublicComp>> getAllQuestionCp(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        map.get("map");

        List<QuestionPublicComp> questionPublicComp = questionService.getAllComp();

        if (questionPublicComp != null) {
            return new CommonResult<List<QuestionPublicComp>>(100, "获取成功", questionPublicComp);
        } else {
            return new CommonResult<List<QuestionPublicComp>>(200, "获取失败", null);
        }

    }

    /*@PostMapping(value = "/getQue")
    public CommonResult<String> getQue(@RequestBody HashMap<String,Object> map){

        List<Map> list = (List<Map>) map.get("list");

        Iterator<Map> iterator = list.iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next().get("a"));
        }

        return null;
    }*/

    /**
     * 根据条件查询题目（）
     * @param map
     * @return
     */
    @PostMapping(value = "/getQuestion")
    public CommonResult<Object> getQuestion(@RequestBody HashMap<String, Object> map) {

        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        Integer courseId = Integer.parseInt(String.valueOf(map.get("courseId")));

        if (courseId == null) {
            return new CommonResult<>(200, "courseId呢");
        }

        String type = null;
        Integer chapterId = null;
        Integer modularId = null;
        String content = null;
        try {
            chapterId = Integer.parseInt(String.valueOf(map.get("chapterId")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            modularId = Integer.parseInt((String) map.get("modularId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        type = String.valueOf(map.get("type"));
        content = String.valueOf(map.get("content"));

        Integer page = null;
        try {
            page = Integer.parseInt(String.valueOf(map.get("page")));
        } catch (Exception e) {
            page = 1;
            e.printStackTrace();
        }

        Integer limit = Integer.parseInt(String.valueOf(map.get("limit")));

        PageHelper.startPage(page, limit);
        List<Object> list = questionService.getAllQuestionInConditionWithName(type, courseId, chapterId, modularId, content);

        PageInfo pageInfo = new PageInfo(list);
        if (list != null && list.size() != 0) {
            return new CommonResult<>(100, "查询成功", pageInfo);
        } else {
            return new CommonResult<>(200, "查询失败");
        }


        //questionService.getQuestionInCondition(courseId,type,chapterId,modularId,content);

    }

    @PostMapping(value = "/addPaperQuestion")
    public CommonResult<String> addPaperQuestion(@RequestBody HashMap<String, Object> map) {
        System.out.println("map = " + map);
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        List<PaperQuestion> paperQuestionList2 = new ArrayList<>();

        List<PaperQuestion> paperQuestionList = (List<PaperQuestion>) map.get("questionList");
//遍历list
        for (Object object : paperQuestionList) {
            // 将list中的数据转成json字符串
            String jsonObject = JSON.toJSONString(object);
            //将json转成需要的对象
            PaperQuestion paperQuestion = JSONObject.parseObject(jsonObject, PaperQuestion.class);
            paperQuestionList2.add(paperQuestion);
//            System.out.println(courseInfo);
        }

        Integer questionType = null;
        Integer questionId = null;
        Integer mark = null;
        Integer questionIndex = null;
        Integer privateQ = null;
        Integer result = 1;
        System.out.println("paperQuestionList2 = " + paperQuestionList2);
        for (int i = 0; i < paperQuestionList2.size(); i++) {
            questionType = Integer.parseInt(paperQuestionList2.get(i).getQuestionType());
            questionId = paperQuestionList2.get(i).getQuestionId();
            mark = paperQuestionList2.get(i).getMark();
            questionIndex = paperQuestionList2.get(i).getQuestionIndex();
            privateQ = paperQuestionList2.get(i).getPrivateQ();
            result = questionService.addPaperQuestion(paperId, questionType, questionId, mark, questionIndex, privateQ) * result;
        }

        if (result > 0) {
            return new CommonResult<>(100, "查询成功");
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }


    @PostMapping(value = "/getQuestionEntity")//根据id和题目的类型返回题目信息
    public CommonResult<List<QuestionEntity>> getQuestionEntity(@RequestBody HashMap<String, Object> map) {

        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效");
        }
        Integer type = null;
        System.out.println(map.get("questionIdList"));
        ArrayList<Integer> questionIdList = null;

        try {
            type = (Integer) map.get("type");
            questionIdList = ObjectTo.swap(map.get("questionIdList"));
            System.out.println(questionIdList);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult<>(200, "数据传输错误");
        }

        //questionEntity类只有前端所需信息，不包含题目完整信息
        List<QuestionEntity> questions = questionService.getQuestionEntity(type, questionIdList);

        if (questions != null) {
            return new CommonResult<>(100, "查询成功", questions);
        } else {
            return new CommonResult<>(200, "查询失败");
        }

    }

    @PostMapping(value = "/getAllQuestionNotApproved")//没有经过审核的题目
    public CommonResult<Object> getAllQuestionNotApproved(@RequestBody HashMap<String, String> map) {

        String token = map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        PageInfo<Student> studentList = questionService.getAllNotApproved(map.get("type"), map.get("limit"), map.get("page"));

        return studentList != null ? new CommonResult<>(100, "获取题目列表成功", studentList) : new CommonResult<>(200, "获取题目列表失败");

    }


}