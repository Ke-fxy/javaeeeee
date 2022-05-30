package com.javaee.entities;

import lombok.*;

import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/29 19:59
 * @Description
 * @Version 1.0
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity {

    private Integer type;

    private String subject;

    private List<com.javaee.entities.Answer> answers;

    private List<String> correctAnswers;

    private Integer isHook;

}
