package com.java.product.mapper;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * author:快乐风男
 * time:19:52
 */
@Repository
public interface WebProductImgMapper extends BaseMapper<WebProductImgMapper> {
    //根据商品id查询多个商品图片数据
    List<WebProductImgMapper> queryWebProductImgByProId(Integer productId) throws Exception;
}
