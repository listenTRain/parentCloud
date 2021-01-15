package com.java.buycar.controller;

import com.java.model.Good;
import com.java.model.WebUsersEntity;
import com.java.service.BuyCarService;
import com.java.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:快乐风男
 * time:12:46
 */
@Controller
@RequestMapping("/buyCar")
public class BuyCarController {

    @Autowired
    private BuyCarService buyCarService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitMQService rabbitMQService;

    /**
     *   未登录的情况下实现购物车添加
     * @param goodId  商品id
     * @param num  商品数量
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    @RequestMapping("/addBuyCar")
    public @ResponseBody
    Map<String, Object> addBuyCar(Integer goodId, Integer num,String token, HttpServletRequest request, HttpServletResponse response){
        try {
            //若令牌为null，则肯定没有登陆
            if(token == null){
                System.out.println("没有token..");
                return buyCarService.addBuyCar(goodId,num,request,response); //未登录添加购物车
            }else {
                System.out.println("存在token");
                //若令牌不为null，到redis中找用户数据
                ValueOperations vop = redisTemplate.opsForValue();
                WebUsersEntity loginUser = ((WebUsersEntity)vop.get("sessionId" + token));
                if (loginUser == null){
                    System.out.println("不存在登陆用户");
                    return buyCarService.addBuyCar(goodId,num,request,response); //未登录添加购物车
                }else {
                    System.out.println("存在登陆用户uid:"+loginUser.getId());
                    return buyCarService.addBuyCarAfterLogin(goodId,num,loginUser.getId().intValue(),request,response); //已登录添加购物车
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //合并用户登陆后的购物车
    @RequestMapping("/loginToAppendBuyCar")
    public @ResponseBody Map<String,Object> loginToAppendBuyCar(Integer uid,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = null;
        try {
            //执行购物车合并
            map = buyCarService.appendBuyCarByLogin(uid,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code","error");
        }
        return map;
    }
    //加载购物车数据
    @RequestMapping("/loadBuyCar")
    public @ResponseBody
    List<Good> loadBuyCar(String token, HttpServletRequest request){
        try {
            System.out.println("在controller层加载购物车");
            System.out.println(buyCarService.findBuyCar(token,request));
            return buyCarService.findBuyCar(token,request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/addRabbitMQToExCFormBuyCar")
    public @ResponseBody Map<String,Object> addRabbitMQToExCFormBuyCar(String proIds,Float zPrice, String token){
        Map<String,Object> map = new HashMap<String, Object>();
        ValueOperations vop = redisTemplate.opsForValue();
        WebUsersEntity loginUser = (WebUsersEntity)vop.get("sessionId" + token);
        //若令牌不为null，到redis中找用户数据
        if (loginUser != null){
            try {
                rabbitMQService.addRabbitMQToExCFormBuyCar(proIds,zPrice,loginUser.getId().intValue());
                map.put("code",0);
                map.put("msg","数据装载成功。。");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code",200);
                map.put("msg","数据装载异常！！");
            }
        }else {
            map.put("code",404);
            map.put("msg","用户未登录！！");
        }

        return map;
    }

    //删除提交订单的购物车商品数据
    @RequestMapping("/delGoodByProIds")
    public @ResponseBody Map<String,Object> delGoodByProIds(String token,String proIds){
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            //若令牌不为null，到redis中找用户数据
            ValueOperations vop = redisTemplate.opsForValue();
            WebUsersEntity loginUser = ((WebUsersEntity)vop.get("sessionId" + token));
            if(loginUser != null){
                buyCarService.delGoodByProIds(loginUser.getId().intValue(),proIds);
                map.put("code",0);
                map.put("msg","购物车数据删除成功。。");
            }else {
                map.put("code",404);
                map.put("msg","用户未登录！！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",200);
            map.put("msg","购物车数据删除异常！！");
        }
        return map;
    }

}
