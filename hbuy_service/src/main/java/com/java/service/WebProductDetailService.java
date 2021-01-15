package com.java.service;

import com.java.model.WebProductDetailEntity;

/**
 * author:快乐风男
 * time:19:27
 */
public interface WebProductDetailService extends BaseService<WebProductDetailEntity> {
    //根据freemarker模板生成商品详情的静态页面
    void makeProductDetail() throws Exception;
}
