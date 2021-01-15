package com.java.product.controller;

import com.java.service.DiscussService;
import org.bson.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author:快乐风男
 * time:10:47
 */
@Controller
@RequestMapping("/discuss")
public class DiscussController {
    @Autowired
    private DiscussService discussService;

    //分页加载商品详情数据
    @RequestMapping("/loadPageDiscuss")
    public @ResponseBody
    List<Document> loadPageDiscuss(Integer page,Integer limit){
        try {
            List<Document> pageDiscuss = discussService.findPageDiscuss(page, limit);
            return pageDiscuss;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
