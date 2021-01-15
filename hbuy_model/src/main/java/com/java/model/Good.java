package com.java.model;

import java.io.Serializable;

/**
 *   购物车商品实体对象
 */
public class Good extends WebProductDetailEntity implements Serializable{

    private static final long serialVersionUID = -2427426994450011577L;

    //商品id
    private Integer goodId;
    //商品数量
    private Integer num;

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Good(Integer goodId, Integer num) {
        this.goodId = goodId;
        this.num = num;
    }

    public Good() {
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId=" + goodId +
                ", num=" + num +
                '}';
    }
}
