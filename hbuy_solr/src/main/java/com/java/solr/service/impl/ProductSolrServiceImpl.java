package com.java.solr.service.impl;

import com.java.model.ProductSolr;
import com.java.service.ProductSolrService;
import com.java.solr.mapper.ProductMapper;

import com.java.solr.utils.SolrUtil;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * author:快乐风男
 * time:16:44
 */
@Service
@Transactional(readOnly = false)
public class ProductSolrServiceImpl implements ProductSolrService {

    //依赖Mapper代理对象
    @Autowired
    private ProductMapper productMapper;

    //获取solr链接对象
    private HttpSolrClient solr = SolrUtil.getSolr();

    @Scheduled(cron = "0/10 * * * * ? ") // 间隔10秒执行
    @Override
    public void addDataFromMysqlToSolr() throws Exception {
        //每次添加前先删除之前的内容
        solr.deleteByQuery("*:*");
        //查询mysql中的商品
        List<ProductSolr> productSolrs = productMapper.queryAll();
        //将数据批量添加到solr中
        UpdateResponse updateResponse = solr.addBeans(productSolrs);
        //手动提交solr事物
        solr.commit();
    }

    @Override
    public List<ProductSolr> loadProductBySolr(String slorParam) throws Exception {
        //新建查询的条件对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.set("q","title:"+slorParam);
        //设置分页
        solrQuery.setStart(0);
        solrQuery.setRows(4);
        QueryResponse query = solr.query(solrQuery);
        //将查询结果转为商品对象集合
        List<ProductSolr> productSolrList = query.getBeans(ProductSolr.class);
        return productSolrList;
    }

}
