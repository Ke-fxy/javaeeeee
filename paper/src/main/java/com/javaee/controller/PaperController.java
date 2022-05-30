package com.javaee.controller;

import com.github.pagehelper.PageInfo;
import com.javaee.entities.CommonResult;
import com.javaee.entities.Paper;
import com.javaee.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/paper")
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

    @PostMapping("/addPaper")
    public CommonResult<String> addPaper(@RequestBody HashMap<String, Object> map) {
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

        return result > 0 ? new CommonResult<>(100, "添加成功") : new CommonResult<>(200, "添加失败");
    }

    @PostMapping("/addPaperQuestion")
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

    @PostMapping("/deletePaper")
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

    @PostMapping("/updatePaper")
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

    @PostMapping("/getPaper")
    public CommonResult<Paper> getPaper(@RequestBody HashMap<String, Object> map) {
        String token = (String) map.get("token");
        String checkup = checkup(token);

        if (checkup == null) {
            return new CommonResult<>(200, "用户未登录或登录状态失效", null);
        }

        Integer paperId = Integer.parseInt(map.get("paperId").toString());

        Paper paper = paperService.getPaper(paperId);

        if (paper != null) {
            return new CommonResult<>(100, "查找成功", paper);
        } else {
            return new CommonResult<>(200, "失败成功", null);
        }
    }

    @PostMapping ("/getAllPaper")
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

    @PostMapping("/updatePaperQuestion")
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
        Integer courseId = null;
        Integer paperId = null;
        try {
            choiceNum = (Integer) map.get("choiceNum");
            choiceScore = (Integer) map.get("choiceScore");
            comNum = (Integer) map.get("comNum");
            comScore = (Integer) map.get("comScore");
            courseId = (Integer) map.get("courseId");
            paperId = (Integer) map.get("paperId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        paperService.addPaperQuestionAuto(choiceNum, choiceScore, comNum, comScore, courseId,paperId);

        return new CommonResult<>(100,"添加成功");

    }

}