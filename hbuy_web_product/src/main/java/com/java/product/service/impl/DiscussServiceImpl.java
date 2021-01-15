package com.java.product.service.impl;

import com.java.product.utils.MongoDBUtils;
import com.java.service.DiscussService;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * author:快乐风男
 * time:10:27
 */
@Service
@Transactional(readOnly = false)
public class DiscussServiceImpl implements DiscussService{

    //mongoDB集合的链接对象
    private MongoCollection<Document> collection = MongoDBUtils.getCollection();

    @Override
    public List<Document> findPageDiscuss(Integer page, Integer limit) throws Exception {
        //1.执行第n页的分页查询（从第几条数据下标开始（第1条下标为0），每一页查询的数据条数）
        FindIterable<Document> documentsPage = collection.find().skip((page - 1) * limit).limit(limit);
        //2.将document数据类型转为List集合（因为springMVC框架不能直接将 FindIterable<Document> 转为JSON数据）
        List<Document> list = new ArrayList();
        documentsPage.iterator().forEachRemaining(one->list.add(one));
        return list;
    }
}
