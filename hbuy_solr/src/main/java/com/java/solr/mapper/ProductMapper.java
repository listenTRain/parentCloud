package com.java.solr.mapper;


import com.java.model.ProductSolr;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:快乐风男
 * time:16:38
 */
@Repository
public interface ProductMapper {
    //查询所有的商品详情数据
    @Select("select id as pid ,title,price,avatorImg from web_product_detail")
    List<ProductSolr> queryAll() throws  Exception;
}
