package com.java.product.service.impl;

import com.java.model.WebProductDetailEntity;
import com.java.service.WebProductDetailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;

/**
 * author:快乐风男
 * time:20:04
 */
@Service
public class WebProductDetailServiceImpl extends BaseServiceImpl<WebProductDetailEntity> implements WebProductDetailService {
   @Autowired
   private Configuration configuration;
   @Scheduled(cron = "0/30 * * * * ?")
    @Override
    public void makeProductDetail() throws Exception {
        System.out.println(new Date()+"执行了页面自动生成的方法。。");
        //定义输入流对象
        FileWriter fw = null;
        //1.查询获取所有的商品详情及其详情图片数据
        List<WebProductDetailEntity> webProductDetailEntities = baseMapper.queryAll();
        //2.定义生成文件的文件夹
        File file = new File("E:\\Java练习\\丁练习作业\\springcloud自动生成html");
        //3.判断文件夹是否存在
        if (!file.exists()){
            //不存在则创建
            file.mkdirs();
        }
        //4.通过循环遍历商品详情数据生成商品静态页面
        for (WebProductDetailEntity wpde : webProductDetailEntities){
            //4.1.定义生成静态页面的目标文件夹路径
            String filePath = "E:\\Java练习\\丁练习作业\\springcloud自动生成html\\"+wpde.getId()+".html";
            //4.2.通过文件路径得到目标文件,此时的目标文件是空的(映射文件)
            File newFile = new File(filePath);
            //4.3.得到目标的输入流对象
            fw = new FileWriter(newFile);
            //4.4.根据ftl模板得到生成静态页面的模板对象
            Template template = configuration.getTemplate("product.ftl");
            //4.5.通过目标文件的输入流对象和数据生成静态文件
            template.process(wpde,fw);
            fw.close();
        }
    }
}
