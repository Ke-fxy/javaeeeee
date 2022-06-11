package com.javaee.mapper;

import com.javaee.entities.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * create by: Chalksyy
 * description:
 * create time: 2022/4/25 11:04
 * @return
 */
@Mapper
public interface ManagerMapper {

    /**
     * create by: Chalksyy
     * description: 管理员登陆
     * create time: 2022/4/25 11:05
     * @return Manager类
     */
    Manager login(@Param("mno") Integer mno);

}
