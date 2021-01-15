package com.java.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *   购物车的实体对象
 */
public class BuyCar implements Serializable {

    private static final long serialVersionUID = 4282685045524045824L;

    //商品集合
    private List<Good> goods;

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }


}
