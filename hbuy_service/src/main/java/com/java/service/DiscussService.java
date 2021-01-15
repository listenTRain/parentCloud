package com.java.service;

import org.bson.Document;

import java.util.List;

/**
 * author:快乐风男
 * time:10:29
 */
public interface DiscussService {
    //评论的分页查询
    List<Document> findPageDiscuss(Integer page, Integer limit) throws Exception;
}
