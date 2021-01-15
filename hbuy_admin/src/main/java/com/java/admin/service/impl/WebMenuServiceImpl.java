package com.java.admin.service.impl;

import com.java.admin.service.WebMenuService;
import com.java.model.WebMenuEntity;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:快乐风男
 * time:19:34
 */
@Service
@Transactional
public class WebMenuServiceImpl extends BaseServiceImpl<WebMenuEntity> implements WebMenuService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String save(WebMenuEntity webMenuEntity) throws Exception {
        Integer insert = baseMapper.insert(webMenuEntity);
        if (insert > 0){
            ListOperations listOperations = redisTemplate.opsForList();
            listOperations.rightPushAll("webMenus",webMenuEntity);
            return "saveSuccess";
        }else {
            return "fail";
        }
    }
}
