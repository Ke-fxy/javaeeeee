package com.javaee.entities;

import lombok.*;

import java.sql.Timestamp;

/**
 * @Author Ke
 * @Date 2022/3/23 20:53
 * @Description
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Face {

    private Integer id;

    private Integer userId;

    private String userType;

    private String face;

    private Timestamp createTime;

}