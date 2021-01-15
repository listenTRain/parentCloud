package com.java.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.java.web.model.WebSeckillEntity;
 
/**
 * 
 * @author djin
 *   WebSeckill控制器
 * @date 2020-03-09 10:05:58
 */
@Controller
@RequestMapping("/webseckill")
public class WebSeckillController extends BaseController<WebSeckillEntity>{
	
	/**
	 * 页面jsp
	 */
	@RequestMapping("/page")
	public String page(){
		return "webseckill";
	}

    /**
     * 页面html
     */
    @RequestMapping("/html")
    public String html(){
        return "redirect:/html/webseckill.html";
    }
}
