package com.java.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.java.web.model.WebSortEntity;
 
/**
 * 
 * @author djin
 *   WebSort控制器
 * @date 2020-03-09 10:05:55
 */
@Controller
@RequestMapping("/websort")
public class WebSortController extends BaseController<WebSortEntity>{
	
	/**
	 * 页面jsp
	 */
	@RequestMapping("/page")
	public String page(){
		return "websort";
	}

    /**
     * 页面html
     */
    @RequestMapping("/html")
    public String html(){
        return "redirect:/html/websort.html";
    }
}
