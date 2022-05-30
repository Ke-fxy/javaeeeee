package com.javaee.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;

/**
 * @Author Ke
 * @Date 2022/3/23 20:55
 * @Description
 * @Version 1.0
 */
@Mapper
public interface FaceMapper {
    /**
     * 为用户添加一个人脸数据
     * @param userId
     * @param imageBase64
     * @param createTime
     * @return
     */
    Integer addFace(@Param("userId") Integer userId,
                    @Param("face") String imageBase64,
                    @Param("createTime") Timestamp createTime);
}