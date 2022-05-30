package com.javaee.entities;

import lombok.*;

import java.sql.Timestamp;

/**
 * @Author Ke
 * @Date 2022/4/7 21:44
 * @Description
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaperEntity {

    private Integer id;

    private String paperName;

    private Integer maxMark;

    private String creatorName;

    private String courseName;

    private Timestamp createTime;

    private Integer change;

}