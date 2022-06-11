package com.javaee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionList {

    private Integer questionId;

    private Integer paperId;

    private String text;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String answer;

    private String type;

    private Integer questionIndex;

    private Integer maxMark;

    private String result;

    private Integer PaperQuestionTestedId;

    private Integer score;

//    private Integer courseId;
}
