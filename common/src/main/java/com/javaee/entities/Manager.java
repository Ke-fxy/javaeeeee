package com.javaee.entities;

import lombok.*;

/**
 * create by: Chalksyy
 * description: TODO
 * create time: 2022/4/25 10:59
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Manager {

    Integer id;

    /**
     * 组织管理员编号
     */
    Integer mno;

    String name;

    String password;

    String phone;

    String head_img;

    String email;

    String gender;

}
