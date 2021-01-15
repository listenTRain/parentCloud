package com.java.service;

/**
 * author:快乐风男
 * time:16:09
 */
public interface RabbitMQService {
    //将秒杀数据装入到消息队列中
    void addRabbitMQToExCFormSeckill(Long secId, Long proId,Float secPrice, Integer uid) throws Exception;

    //将购物车数据装入到消息队列中
    void addRabbitMQToExCFormBuyCar(String proIds, Float zPrice, Integer uid) throws Exception;
}
