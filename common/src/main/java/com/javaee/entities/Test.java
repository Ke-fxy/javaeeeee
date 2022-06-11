package com.javaee.entities;

import lombok.*;

import java.sql.Timestamp;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Test {
    private Integer id;

    private String textName;

    private Integer createrId;

    private Integer paperId;

    private Timestamp createTime;

    private Timestamp beginTime;

    private Integer limitTime;

    private Integer time;

    private String place;

    private Integer courseId;

    private Integer examinerId;

    private Integer examine;

    private Timestamp examineTime;

    private Integer state;

    private Integer invigilatorId;

    private Integer online;
}