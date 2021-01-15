package com.java.mongodb.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * author:快乐风男
 * time:20:27
 */
public class MongoDBUtils {
    //定义mongoDB客户端对象
    static private MongoClient client = null;
    //定义连接mongoDB的库的对象
    static private MongoDatabase mongoDatabase = null;
    //定义mongoDB的库中的集合对象
    static private MongoCollection<Document> collection = null;

    //采用静态代码块将获取到mongoDB数据库集合链接对象设计为单例模式
    static {
        //获取mongoDB客户端对象
        client = new MongoClient("127.0.0.1");
        //连接到数据库中具体的库
        mongoDatabase = client.getDatabase("testOne");
        //获取库的集合
        collection = mongoDatabase.getCollection("books");
    }

    //获取mongDB中集合的对象
    public static MongoCollection<Document> getCollection(){
        return collection;
    }
}
