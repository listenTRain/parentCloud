package com.java.sso.service.impl;

import com.java.model.WebUsersEntity;

import com.java.service.WebUsersService;
import com.java.sso.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * author:快乐风男
 * time:21:13
 */
@Service
@Transactional
public class WebUsersServiceImpl extends BaseServiceImpl<WebUsersEntity> implements WebUsersService{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> loginUser(WebUsersEntity users) throws Exception {
        //新建Map集合装入登录用户信息
        Map<String, Object> map = new HashMap<String, Object>();
        //加密登录密码
        users.setPwd(MD5.md5crypt(users.getPwd()));
        //对比数据库用户是否登录成功
        WebUsersEntity loginUser = baseMapper.queryObjectByPramas(users);
        if (loginUser != null){
            //状态码0表示登录成功
            map.put("code",0);
            map.put("loginUser",loginUser);
            //产生登陆令牌token
            String token = UUID.randomUUID().toString();
            map.put("token",token);
            System.out.println("业务层产生的token为："+token);
            //往redis中装入用户数据
            ValueOperations vop = redisTemplate.opsForValue();
            //往redis中存放用户数据，设置20分钟有效
            vop.set("sessionId"+token,loginUser,20, TimeUnit.MINUTES);

        }else {
            map.put("code",200);
        }
        return map;
    }
}
