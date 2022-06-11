package com.javaee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperAndQuestionTestedCorrect {
    private Integer id;

    private Integer paperId;

    private Integer questionIndex;

    private String result;

    private Integer studentId;

    private Integer score;

    private Integer fullMark;

    private Integer questionId;

    private String questionType;


}
