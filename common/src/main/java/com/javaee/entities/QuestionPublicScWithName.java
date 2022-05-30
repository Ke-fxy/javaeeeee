package com.javaee.entities;

import lombok.*;

import java.sql.Timestamp;

/**
 * @Author Ke
 * @Date 2022/3/6 21:06
 * @Description
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPublicScWithName {

    private Integer id;

    private String text;

    private String option1;

    private String option2;

    private String option3;

    private String option4;

    private String answer;

    private Integer createrId;

    private Timestamp createTime;

    private Integer courseId;

    private Integer chapterId;

    private Integer modularId;

    private Integer diffculyt;

    private Integer examine;

    private Integer examinerId;

    private Timestamp examinerTime;

    private Integer type;

    private String modularName;

    private String chapterName;

}