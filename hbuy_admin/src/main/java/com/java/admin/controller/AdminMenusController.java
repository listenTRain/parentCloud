package com.java.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.java.admin.model.AdminMenusEntity;
 
/**
 * 
 * @author djin
 *   AdminMenus控制器
 * @date 2020-03-07 10:04:53
 */
@Controller
@RequestMapping("/adminmenus")
public class AdminMenusController extends BaseController<AdminMenusEntity>{
	
	/**
	 * 页面jsp
	 */
	@RequestMapping("/page")
	public String page(){
		return "adminmenus";
	}

    /**
     * 页面html
     */
    @RequestMapping("/html")
    public String html(){
        return "redirect:/html/adminmenus.html";
    }
}
