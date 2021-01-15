package com.java.web.service.impl;

import com.java.model.WebMenuEntity;
import com.java.web.mapper.WebMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.web.service.WebMenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djin
 * WebMenu业务层实现类
 * @date 2020-03-09 10:06:00
 */
@Service
@Transactional
public class WebMenuServiceImpl extends BaseServiceImpl<WebMenuEntity> implements WebMenuService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<WebMenuEntity> findAll() throws Exception {

        //1.新建一个list菜单集合
        List<WebMenuEntity> webMenus = new ArrayList<WebMenuEntity>();
        //2.得到redis操作菜单list集合的对象
        ListOperations listOperations = redisTemplate.opsForList();
        webMenus = listOperations.range("webMenus", 0, -1);
        if (webMenus.size() == 0) {
            webMenus = baseMapper.queryAll();
            //没有数据则添加数据
            listOperations.rightPushAll("webMenus",webMenus);
            System.out.println("数据来源于mysql");
        }else {
            System.out.println("数据来源于redis缓存");
        }

        return webMenus;
    }
}
