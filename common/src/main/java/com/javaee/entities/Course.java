package com.javaee.entities;

import lombok.*;

import java.util.List;

/**
 * @Author Ke
 * @Date 2022/3/9 23:06
 * @Description 课程
 * @Version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {

    private Integer id;

    /**
     * 课程编号
     */
    private String number;

    /**
     * 学分
     */
    private Integer credit;

    /**
     * 课程类型
     */
    private String type;

    /**
     * 课程名字
     */
    private String courseName;

    /**
     * 是否可以手动组卷（可以0/不可以1）
     */
    private Integer mode;

    /**
     * 创建人id(管理员)
     */
    private Integer createrId;

    /**
     * 模块
     */
    private List<Modular> modularise;

    /**
     * 章节
     */
    private List<com.javaee.entities.Chapter> chapters;

    /**
     * 是否启用，启用为1，未启用为0
     */
    private Integer status;

}