package com.java.orders.mapper;

import com.java.model.WebOrderEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:快乐风男
 * time:14:05
 */
@Repository
public interface WebOrderMapper extends BaseMapper<WebOrderEntity>{
    //监听超时未支付的订单
    @Select("SELECT * FROM web_order where endDate <= NOW() and orderStatus = 1 and flag = 2")
    List<WebOrderEntity> listenerOrder() throws Exception;
}
