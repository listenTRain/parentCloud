package com.java.service;

import com.java.model.ProductSolr;

import java.util.List;

/**
 * author:快乐风男
 * time:16:44
 */
public interface ProductSolrService {
    //将mysql中的商品数据添加到solr中
    void addDataFromMysqlToSolr() throws Exception;

    //根据条件加载slor中的数据
    List<ProductSolr> loadProductBySolr(String slorParam) throws  Exception;
}
