package com.javaee.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaee.entities.Paper;
import com.javaee.entities.PaperEntity;
import com.javaee.mapper.PaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaperService {

    @Resource
    PaperMapper paperMapper;

    public Integer addPaper(Paper paper) {

        Integer result = paperMapper.addPaper(paper);

        return result;
    }

    public Integer addPaperQuestion(Integer paperId, List<Map> questionList) {

        Integer result1 = 0;
        Integer result2 = 0;

        for (int i = 0; i < questionList.size(); i++) {
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
        return result1 * result2;
    }

    public PageInfo getAllPaper(int pageNum, int pageSize) {

        List<Paper> papers = paperMapper.getAll();
        PageHelper.startPage(pageNum, pageSize);
        List<PaperEntity> paperEntities = new ArrayList<>();

        for(Paper paper:papers){
            PaperEntity paperEntity = new PaperEntity(paper.getId(),paper.getPaperName(),paper.getMaxMark(),paperMapper.getCreaterName(paper.getCreatorId()),paperMapper.getCourseName(paper.getCourseId()),paper.getCreateTime(),paper.getChange());
            paperEntities.add(paperEntity);
        }

        if (papers != null && papers.size() != 0) {
            PageInfo pageInfo = new PageInfo(paperEntities);
            return pageInfo;
        } else {
            return null;
        }

    }

    public Integer deletePaper(Integer paperId) {

        Integer result = paperMapper.deletePaper(paperId);

        return result;
    }

    public Integer updatePaper(Paper paper) {

        Integer result = paperMapper.updatePaper(paper);

        return result;
    }

    public Integer deletePaperQuestion(Integer paperId) {
        return paperMapper.deletePaperQuestion(paperId);
    }

    public Paper getPaper(Integer paperId) {
        Paper paper = paperMapper.getPaper(paperId);
        return paper;
    }

    public boolean addPaperQuestionAuto(Integer choiceNum, Integer choiceScore, Integer comNum, Integer comScore, Integer courseId, Integer paperId) {

        //Integer choiceSum = paperMapper.getSum(courseId, 1);

        List<Integer> choiceIdList = paperMapper.getIdList(1, courseId);
        List<Integer> comIdList = paperMapper.getIdList(4,courseId);

        Integer index = 0;

        for (int i = 0; i < choiceNum; i++) {
            Random random = new Random(10);
            int i1 = random.nextInt(choiceIdList.size());
            paperMapper.addPaperPublicSc(paperId, choiceIdList.get(i1), choiceScore, ++index);
        }

        for (int i = 0; i < comNum; i++) {
            Random random = new Random(10);
            int i1 = random.nextInt(comIdList.size());
            paperMapper.addPaperPublicComp(paperId, choiceIdList.get(i1), comScore, ++index);
        }

        return true;
    }

//    public List<Paper> getAllPaper() {
//        List<Paper> paperList = paperMapper.getAllPaper();
//        return paperList;
//    }

}
