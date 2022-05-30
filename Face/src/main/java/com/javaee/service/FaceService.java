package com.javaee.service;

import com.javaee.mapper.FaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @Author Ke
 * @Date 2022/3/23 20:54
 * @Description
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FaceService {

    @Resource
    FaceMapper faceMapper;

    public boolean addFace(Integer userId, String imageBase64) {

        Integer integer = faceMapper.addFace(userId, imageBase64,new Timestamp(System.currentTimeMillis()));

        if (integer!=0){
            return true;
        }else {
            return false;
        }

    }
}