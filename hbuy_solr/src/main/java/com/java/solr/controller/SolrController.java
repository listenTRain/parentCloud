package com.java.solr.controller;

import com.java.model.ProductSolr;
import com.java.service.ProductSolrService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author:快乐风男
 * time:16:56
 */
@Controller
@RequestMapping("/solr")
public class SolrController {
    @Autowired
    private ProductSolrService productSolrService;

  /*  @RequestMapping("/addData")
    public @ResponseBody String addData(){
        try {
            productSolrService.addDataFromMysqlToSolr();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return  "fail";
        }
    }*/
    @RequestMapping("/loadProductBySlor")
    public @ResponseBody
    List<ProductSolr> loadProductBySlor(String slorPra){
        try {
            List<ProductSolr> productSolrList = productSolrService.loadProductBySolr(slorPra);
            System.out.println(productSolrList);
            return productSolrList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
