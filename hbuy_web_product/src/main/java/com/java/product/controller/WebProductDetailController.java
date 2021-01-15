package com.java.product.controller;

import com.java.model.WebProductDetailEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:快乐风男
 * time:21:25
 */
@Controller
@RequestMapping("/webproductdetail")
public class WebProductDetailController extends BaseController<WebProductDetailEntity> {

    @RequestMapping("/makeProductDetailHtml")
    public @ResponseBody String makeProductDetailHtml(){
        try {
            webProductDetailService.makeProductDetail();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
