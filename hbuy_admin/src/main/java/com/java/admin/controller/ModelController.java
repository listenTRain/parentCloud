package com.java.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转的控制器
 * author:快乐风男
 * time:15:42
 */
@Controller
@RequestMapping("/model")
public class ModelController {

    //跳转后台管理平台首页
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    //跳转后台用户管理首页
    @RequestMapping("/toAdminUsers")
    public String toAdminUsers(){
        return "adminusers";
    }

    //跳转后台菜单管理首页
    @RequestMapping("/toAdminMenus")
    public String toAdminMenus(){
        return "adminmenus";
    }

    //跳转前台菜单导航管理页面
    @RequestMapping("/toWebMenu")
    public String toWebMenu(){
        return "webmenu";
    }

}