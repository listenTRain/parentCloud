package com.java.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import com.java.web.model.WebBannerEntity;
 
/**
 * 
 * @author djin
 *   WebBanner控制器
 * @date 2020-03-09 10:06:00
 */
@Controller
@RequestMapping("/webbanner")
public class WebBannerController extends BaseController<WebBannerEntity>{
	
	/**
	 * 页面jsp
	 */
	@RequestMapping("/page")
	public String page(){
		return "webbanner";
	}

    /**
     * 页面html
     */
    @RequestMapping("/html")
    public String html(){
        return "redirect:/html/webbanner.html";
    }
}
