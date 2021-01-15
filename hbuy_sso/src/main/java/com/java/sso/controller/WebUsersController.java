package com.java.sso.controller;

import com.java.model.WebUsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * author:快乐风男
 * time:21:03
 */
@Controller
@RequestMapping("/webusers")
public class WebUsersController extends BaseController<WebUsersEntity> {

    @Autowired
    private RedisTemplate redisTemplate;

    //登录
    @RequestMapping("/loginUser")
    public @ResponseBody Map<String,Object> loginUser(WebUsersEntity user, HttpServletResponse response){
        //1.新建map集合
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = webUsersService.loginUser(user);
            Integer code = (Integer) map.get("code");
            if (code == 0){
                //登陆成功,获取令牌
                String token = (String)map.get("token");
                System.out.println("controller获取的token为："+token);
                //新建cookie
                Cookie cookie = new Cookie("token",token);
                //设置有效时间
                cookie.setMaxAge(20*60);
                //设置共享路径
                cookie.setPath("/webusers");
                //将cookie存放在客户端
                response.addCookie(cookie);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //取到登陆模块的令牌
    @RequestMapping("/getToken")
    public @ResponseBody String getToken(HttpServletRequest request){
        //1.定义令牌
        String token = null;
        //获取cookie对象
        Cookie[] cookies = request.getCookies();
        //System.out.println("getToken取得的cookies数组为:"+cookies);
        if (cookies != null){
            //定义存储令牌token的cookie
            Cookie cookieToken = null;
            for (Cookie tCookie : cookies){
                String name = tCookie.getName();
                //获取令牌token的cookie对象，并存入cookieToken
                //System.out.println("cookie的token name是否取到"+name);
               //System.out.println("cookie的token name是否取到"+tCookie.getValue());
                if (name.equals("token")){
                    cookieToken = tCookie;   //或许可改造成不需要cookieToken，token = tCookie.getValue();
                    break;
                }
            }
            //判断cookieToken是否取得token令牌
           // System.out.println("cookieToken++++++++++++++++++++"+cookieToken.getValue());
            if (cookieToken != null){
                token = cookieToken.getValue();
            }
        }
       // System.out.println("token为为--------------------"+token);
        return token;
    }

    //根据令牌去获取用户信息
    @RequestMapping("/getRedisLoginUser")
    @ResponseBody
    public WebUsersEntity getRedisLoginUser(String token){
        //1.获取redis中操作字符串的模板
        ValueOperations vos = redisTemplate.opsForValue();
        //根据令牌获取登录用户数据
        Object o = vos.get("sessionId" + token);
        System.out.println("redis数据=========================="+o);
        //2.1定义登陆用户
        WebUsersEntity loginUser = null;
        loginUser = (WebUsersEntity)o;
        return loginUser;
    }

    //用户退出
    @RequestMapping("/exitUser")
    public @ResponseBody Boolean exitUser(HttpServletRequest request,HttpServletResponse response){
        //创建返回值
        Boolean exit = null;
        //创建令牌
        String token = null;
        //获取cookie数组
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            //定义token的cookie
            for (Cookie tCookie : cookies){
                String name = tCookie.getName();
                if (name.equals("token")){
                    token = tCookie.getValue();
                }
            }
            if (token != null){
                //删除redis中的用户数据
                exit = redisTemplate.delete("sessionId" + token);
                //清除用户cookie
                Cookie cookie = new Cookie("token","");
                cookie.setPath("/webusers");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        return exit;
    }

}
