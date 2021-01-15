package com.java.web.controller;

import com.java.model.WebMenuEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

 
/**
 * 
 * @author djin
 *   WebMenu控制器
 * @date 2020-03-09 10:06:00
 */
@Controller
@RequestMapping("/webmenu")
public class WebMenuController extends BaseController<WebMenuEntity>{
	
	/**
	 * 页面jsp
	 */
	@RequestMapping("/page")
	public String page(){
		return "webmenu";
	}

    /**
     * 页面html
     */
    @RequestMapping("/html")
    public String html(){
        return "redirect:/html/webmenu.html";
    }

	/*public static void main(String[] args) {
		new Thread(()->{ for (int i = 0; i < 5; i++) { System.out.println(Thread.currentThread().getName()+"执行第一次"+i); } },"A线程").start();

		new Thread(new Runnable() {
			@Override
			public void run() {

			}
		}).start();
	}*/
}
