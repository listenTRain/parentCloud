package com.java.mongodb.test;

import com.java.mongodb.utils.MongoDBUtils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * author:快乐风男
 * time:20:44
 */
public class MongoDBTest {
    //mongoDB集合的链接对象
    private MongoCollection<Document> collection = MongoDBUtils.getCollection();


    //测试mongdb数据库的链接
    @Test
    public void test01(){
        System.out.println(collection);
    }

    //查询所有的文档数据
    @Test
    public void findAll(){
        //1.执行集合对象的查询所有文档数据操作
        FindIterable<Document> documents = collection.find();
        //遍历
        //documents.forEach(one-> System.out.println(one));
        documents.iterator().forEachRemaining(one-> System.out.println(one));

    }

    //插入一条数据
    @Test
    public void insertOne(){
        String data1 = "{'bname':'三国演义aaA'," +
                "'price':220.5," +
                "'author':'罗贯中aaA'," +
                "'sales':500}";
        Document parse1 = Document.parse(data1);
        collection.insertOne(parse1);
    }
    //多条件查询(查询范围price小于240且price大于200并且作者名字中存在a(不区分大小写)的书籍)条件外层有and
    @Test  //Filters推荐使用此种方式进行条件查询
    public void findByCondition(){
        //1.构建左边的价格条件
        Bson ltPrice = Filters.lt("price", 240);//price<240
        Bson gtPrice = Filters.gt("price", 200);//price>200
        //条件整合为一条bson
        Bson pricec = Filters.and(ltPrice, gtPrice);
        //2.构建右边的作者模糊查询条件
        //2.1.创建模糊查询条件,Pattern.CASE_INSENSITIVE:表示查询的字符在任意位置，且不区分大小写
        Pattern pattern = Pattern.compile("中", Pattern.CASE_INSENSITIVE);
        Bson author = Filters.regex("author", pattern);
        //3.将这左右两边的条件整到一块
        Bson andBson = Filters.and(pricec, author);
        FindIterable<Document> documents = collection.find(andBson);
        documents.iterator().forEachRemaining(one-> System.out.println(one));
        
    }

}
