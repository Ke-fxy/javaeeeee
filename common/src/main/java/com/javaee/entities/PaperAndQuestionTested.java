package com.javaee.entities;

import lombok.*;

/**
 * create by: syy
 * description: TODO
 * create time: 2022/4/30 14:49
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaperAndQuestionTested {

    private Integer id;

    private Integer paperId;

    private String questionType;

    private Integer questionId;

    private Integer mark;

    private Integer questionIndex;

    private Integer privateQ;

    private String result;



//    private List<QuestionPublicSc> list;

}
