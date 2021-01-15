package com.java.register.controller;


import com.aliyuncs.exceptions.ClientException;
import com.java.model.WebUsersEntity;
import com.java.register.util.HtmlMail;
import com.java.register.util.MD5;
import com.java.register.util.SmsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:快乐风男
 * time:16:54
 */
@Controller
@RequestMapping("/webusers")
public class WebUsersController extends BaseController<WebUsersEntity>{
    //重写方法，对密码进行MD5加密
    @Override
    public String saveT(WebUsersEntity entity) {
        //对注册的用户密码设置MD5加密
        entity.setPwd(MD5.md5crypt(entity.getPwd()));
        return super.saveT(entity);
    }

    //手机短信发送
    @RequestMapping("/sendSms")
    public @ResponseBody
    String sendSms(String phone, String code){

        // return "OK";
       try {
            return SmsUtil.sendSms(phone,code);

        } catch (ClientException e) {
            e.printStackTrace();
            return ERROR;
        }
    }

    //发送邮件
    @RequestMapping("/sendEmail")
    public @ResponseBody String sendEmail(String email,String msg){
        return HtmlMail.sendEmail(email,msg);
    }
}
