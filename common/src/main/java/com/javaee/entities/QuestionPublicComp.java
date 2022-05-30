package com.javaee.entities;

import lombok.*;

import java.sql.Timestamp;

/**
 * @Author Ke
 * @Date 2022/3/16 21:17
 * @Description 填空题(系统)
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QuestionPublicComp {

    private Integer id;

    private String content;

    private String answer1;

    private String answer2;

    private String answer3;

    private Integer createrId;

    private Timestamp createTime;

    private Integer courseId;

    private Integer examine;

    private Integer examineId;

    private Timestamp examineTime;

    private Integer chapterId;

    private Integer modularId;

    private Integer difficulty;

    private Integer type;

}