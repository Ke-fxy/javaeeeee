package com.javaee.entities;

import lombok.*;

/**
 * @Author Ke
 * @Date 2022/3/13 19:27
 * @Description
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    private Integer id;

    private Integer tNo;

    private String name;

    private String phone;

    private String headImg;

    private String major;

    private String password;

    private String email;

    private String gender;

    /**
     * 权限（0，1，2）
     */
    private Integer role;

}