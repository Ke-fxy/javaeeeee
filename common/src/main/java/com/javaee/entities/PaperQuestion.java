package com.javaee.entities;

import lombok.*;

import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * create by: syy
 * description: TODO
 * create time: 2022/3/19 15:56

 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaperQuestion {

    private String token;

    private Integer id;

    private String paperName;

    private Integer maxMark;

    private Integer creatorId;

    private Integer courseId;

    private Time createTime;

    private Integer change;

    private List<Map<String,String>> questionList;





}