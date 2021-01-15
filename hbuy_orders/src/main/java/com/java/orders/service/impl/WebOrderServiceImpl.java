package com.java.orders.service.impl;

import com.java.model.WebOrderEntity;
import com.java.service.WebOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:快乐风男
 * time:15:13
 */
@Service
@Transactional(readOnly = false)
public class WebOrderServiceImpl extends BaseServiceImpl<WebOrderEntity> implements WebOrderService {

    //注入redis
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?") // 间隔5秒执行
    @Override
    public void listenerOrder() throws Exception {
        //查询超时未支付的订单数据
        List<WebOrderEntity> orderses = webOrderMapper.listenerOrder();
        //通过循环将订单状态修改，然后在redis中修改数据
        if (orderses != null){
            for (WebOrderEntity order : orderses){
                order.setOrderstatus("5");
                Integer count = baseMapper.update(order);
                if (count > 0){
                    System.out.println("id为："+order.getId()+"订单状态已修改。。");
                    //操作redis
                    SetOperations sop = redisTemplate.opsForSet();
                    //构建key和value
                    String key = "secKill_"+order.getSecid()+"_"+order.getProids();
                    String value = order.getSecid()+","+order.getUserid()+","+order.getProids();
                    Long reCount = 0L;
                    //删除redis中的set里面的秒杀中的用户数据
                    if (sop.isMember(key,value)){ //判断redis中存在该数据
                        reCount = sop.remove(key, value);  //删除已存在的秒杀中的数据
                    }
                    //将此秒杀的数据加回到redis的list中  seckill.getId() + "_" + seckill.getProductid();
                    ListOperations lop = redisTemplate.opsForList();
                    Long addCount = lop.leftPush(order.getSecid() + "_" + order.getProids(), order.getSecid() + "_" + order.getProids());
                    if(reCount>0 && addCount>0){
                        System.out.println(order.getSecid() + "_" + order.getProids()+"已加回去");
                    }
                }else {
                    System.out.println("id为："+order.getId()+"订单状态修改失败！！");
                }
            }
        }else {
            System.out.println("没有超时未支付的订单。。");
        }
    }
}
