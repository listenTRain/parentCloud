package com.java.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.java.admin.model.AdminUserMenuEntity;
 
/**
 * 
 * @author djin
 *   AdminUserMenu控制器
 * @date 2020-03-07 10:04:53
 */
@Controller
@RequestMapping("/adminusermenu")
public class AdminUserMenuController extends BaseController<AdminUserMenuEntity>{
	
	/**
	 * 页面jsp
	 */
	@RequestMapping("/page")
	public String page(){
		return "adminusermenu";
	}

    /**
     * 页面html
     */
    @RequestMapping("/html")
    public String html(){
        return "redirect:/html/adminusermenu.html";
    }
}
