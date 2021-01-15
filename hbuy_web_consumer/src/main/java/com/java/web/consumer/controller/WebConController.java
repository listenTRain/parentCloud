package com.java.web.consumer.controller;

import com.java.model.WebBannerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * author:快乐风男
 * time:10:36
 */
@Controller
@RequestMapping("/webConController")
public class WebConController {

    @Autowired
    private RestTemplate restTemplate;

    //通过 @PathVariable 可以将URL中占位符参数{xxx}绑定到处理器类的方法形参中@PathVariable(“xxx“)
    @RequestMapping("/testConRibbon/{userName}")
    public @ResponseBody String testConRibbon(@PathVariable("userName") String userName){
        return restTemplate.getForObject("http://web-provider/webProController/testRibbon/"+userName,String.class);
    }

    @RequestMapping("/loadAllBanner")
    public @ResponseBody
    List<WebBannerEntity> loadAllBanner(){
        return restTemplate.getForObject("http://web-provider/webbanner/loadAll",List.class);
    }

}
