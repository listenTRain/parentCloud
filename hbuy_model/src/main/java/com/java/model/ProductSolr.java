package com.java.model;

import org.apache.solr.client.solrj.beans.Field;

/**
 *   结合solr搜索引擎的实体封装类
 */
public class ProductSolr {

    //商品id
    @Field
    private Long pid;
    //标题
    @Field
    private String title;
    //价格
    @Field
    private Float price;
    //封面图路径
    @Field
    private String avatorImg;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getAvatorImg() {
        return avatorImg;
    }

    public void setAvatorImg(String avatorImg) {
        this.avatorImg = avatorImg;
    }

    @Override
    public String toString() {
        return "ProductSolr{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", avatorImg='" + avatorImg + '\'' +
                '}';
    }
}
