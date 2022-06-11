package com.javaee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubmitPaper {

    private Integer id;

    private Integer paperId;

    private Integer studentId;

    private Integer questionIndex;

    private String result;

    private Integer score;

    private Integer fullMark;

    private Integer questionId;

    private Integer questionType;
}
