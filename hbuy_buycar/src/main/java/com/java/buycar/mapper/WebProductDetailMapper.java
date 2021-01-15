package com.java.buycar.mapper;

import com.java.model.WebProductDetailEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author djin
 *    WebProductDetailMapper层
 * @date 2020-03-09 10:05:59
 */
@Repository
public interface WebProductDetailMapper {

    //根据主键查询单个数据
    @Select("select * from web_product_detail where id = #{id}")
    WebProductDetailEntity queryObjectById(Integer id) throws Exception;
}
