package com.javaee.entities;

import lombok.*;

/**
 * @Author Ke
 * @Date 2022/3/9 23:35
 * @Description
 * @Version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Chapter {

    private Integer id;

    private Integer courseId;

    private String chapterName;

}