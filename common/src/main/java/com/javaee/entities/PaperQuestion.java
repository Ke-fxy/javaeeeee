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

    private String questionType;

    private Integer questionId;

    private Integer questionIndex;

    private Integer privateQ;

//    private Time createTime;

    private Integer mark;

}