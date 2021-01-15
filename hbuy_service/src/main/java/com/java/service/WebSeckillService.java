package com.java.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author djin
 *    WebSeckill业务层接口
 * @date 2020-03-09 10:05:58
 */
public interface WebSeckillService{

    //1.将秒杀数据放入到redis中
    Map<String, Object> addSecKillToRedis() throws Exception;

    //2.将秒杀的商品状态改为开始秒杀
    String updateUPSecKillStatus() throws Exception;

    //3.将秒杀商品的状态改为已结束
    String updateEndSecKillStatus() throws Exception;

    //4.执行秒杀
    Map<String,Object> doSeckill(String token, Long secId) throws Exception;

    //5.页面加载开始秒杀的商品
    List<Map<String,Object>> findUPSecKill() throws Exception;
}
