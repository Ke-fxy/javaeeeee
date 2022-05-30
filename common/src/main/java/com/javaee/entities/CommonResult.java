package com.javaee.entities;

import lombok.*;

/**
 * @Author Ke
 * @Date 2022/1/14 15:18
 * @Description json实体类
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    /**
     * 规定100为成功，200为失败
     */
    private Integer code;

    private String message;

    private T data;

    public CommonResult(Integer code, String message){
        this(code,message,null);
    }

}