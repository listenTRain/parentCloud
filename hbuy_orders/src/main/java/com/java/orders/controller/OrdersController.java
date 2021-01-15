package com.java.orders.controller;

import com.java.model.WebOrderEntity;
import com.java.model.WebUsersEntity;
import com.java.orders.utils.OrderUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * author:快乐风男
 * time:14:51
 */
@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController<WebOrderEntity>{

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *    消息队列将请求发送给订单支付模块
     * @param dataMap  发送的数据，从MQ中取出秒杀数据
     * @param channel  执行的通道
     * @param headers  执行响应回的结果
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "que_hbuy_seckill"),//队列的名字
            exchange = @Exchange(value = "ex_hbuy_seckill",type = "headers")//交换机的名字,交换机的类型
           ))//配置发送数据的交换机和队列的基本信息
    @RabbitHandler //配置自动发送数据到订单模块
    public void sendOrdersData(@Payload Map<String,Object> dataMap, Channel channel, @Headers Map<String,Object> headers){
        try {
            //1.从配置中的交换机的队列中取出数据
            Long secId = (Long) dataMap.get("secId");  //秒杀的id
            Integer uid = (Integer) dataMap.get("uid");  //用户id
            Long proId = (Long) dataMap.get("proId");
            Float secPrice = (Float) dataMap.get("secPrice");
            Integer flag = (Integer) dataMap.get("flag");
            //2、安全监测
            System.out.println("OrderController.....安全监测");
            //3、创建订单
            WebOrderEntity orders = new WebOrderEntity();
            //设置订单编号
            orders.setOrderno(OrderUtils.generateOrderNo(uid));
            //设置订单的用户id
            orders.setUserid(Long.parseLong(uid.toString()));
            //设置订单状态
            orders.setOrderstatus("1");
            //订单总价
            orders.setCost(secPrice);
            //设置秒杀id
            orders.setSecid(secId);
            //设置商品id
            orders.setProids(proId.toString());
            //设置订单来源
            orders.setFlag(flag);
            //实例化日历工具类
            Calendar calendar = Calendar.getInstance();
            //设置创建时间，系统当前时间
            orders.setCreatedate(calendar.getTime());
            //设置结束时间
            //当前时间的10分钟之后的时间 (秒杀商品10分钟内支付有效)
            calendar.add(Calendar.MINUTE,10);
            //设置此订单10分钟内有效
            orders.setEnddate(calendar.getTime());
            //执行添加订单,mysql数据库的添加
            webOrderService.save(orders);
            //请求的线程暂停3秒钟
            Thread.sleep(3000);
            //4、支付订单，接入支付接口（支付宝，微信，网银。。。）
            System.out.println(orders.getOrderno()+"的订单已加入到mysql中");
            //5、手动确认订单处理完毕的数据条数
            long endTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
            System.out.println("endTag"+endTag);
            //MQ的通道发送请求数据执行的结果改为手动的
            channel.basicAck(endTag,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *    消息队列将请求发送给订单支付模块
     * @param dataMap  发送的数据，从MQ中取出购物车数据
     * @param channel  执行的通道
     * @param headers  执行响应回的结果
     */
    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(value = "que_hbuy_buycar"),  //队列的名字
            exchange = @Exchange(value = "ex_hbuy_buycar",type = "headers")  //交换机的名字,交换机的类型
    ))  //配置发送数据的交换机和队列的基本信息
    @RabbitHandler   //配置自动发送数据到订单模块
    public void sendOrdersData2(@Payload Map<String,Object> dataMap, Channel channel, @Headers Map<String,Object> headers){
        try {
            //1.从配置中的交换机的队列中取出数据
            Integer uid = (Integer) dataMap.get("uid");  //用户id
            String proIds = (String) dataMap.get("proIds");
            Float zPrice = (Float) dataMap.get("zPrice");
            Integer flag = (Integer) dataMap.get("flag");
            System.out.println("777777777777777777777777777777777777777777777777 uid为 :"+ uid);
            //2、安全监测
            System.out.println("OrderController.....安全监测");
            //3、创建订单
            //3.1、获取订单编号  16位数字字符串
            WebOrderEntity orders = new WebOrderEntity();
            //获取订单编号
            orders.setOrderno(OrderUtils.generateOrderNo(uid));
            //设置订单的用户id
            orders.setUserid(Long.parseLong(uid.toString()));
            //设置订单状态
            orders.setOrderstatus("1");
            //订单总价
            orders.setCost(zPrice);
            //设置商品id
            orders.setProids(proIds);
            //设置订单来源
            orders.setFlag(flag);
            //实例化日历工具类
            Calendar calendar = Calendar.getInstance();
            //设置创建时间，系统当前时间
            orders.setCreatedate(calendar.getTime());
            //执行添加订单,mysql数据库的添加

            webOrderService.save(orders);
            //请求的线程暂停3秒钟
            Thread.sleep(3000);
            //4、支付订单，接入支付接口（支付宝，微信，网银。。。）
            System.out.println(orders.getOrderno()+"的订单已加入到mysql中");
            //5、手动确认订单处理完毕的数据条数
            long endTag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
            System.out.println("endTag"+endTag);
            //MQ的通道发送请求数据执行的结果改为手动的
            channel.basicAck(endTag,false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  加载订单数据
     * @param token
     * @return
     */
    @RequestMapping("/loadOrdersByUid")
    public @ResponseBody
    List<WebOrderEntity> loadOrdersByUid(String token){
        //若令牌不为null，到redis中找用户数据
        ValueOperations vop = redisTemplate.opsForValue();
        WebUsersEntity loginUser = (WebUsersEntity) vop.get("sessionId" + token);
        if (loginUser != null){
            //构建查询的订单条件
            WebOrderEntity order = new WebOrderEntity();
            order.setUserid(loginUser.getId());
            try {
                return baseService.findManyByPramas(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
