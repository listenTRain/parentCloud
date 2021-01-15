package com.java.web.provider.controller;

import com.java.service.WebProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:快乐风男
 * time:11:46
 */
@Controller
@RequestMapping("/webProController")
public class WebProController {
    @Autowired
    private WebProviderService providerService;

    @RequestMapping("/testRibbon/{userName}")
    public @ResponseBody String testRibbon(@PathVariable("userName") String userName){
        try {
            return providerService.testRibbon(userName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
