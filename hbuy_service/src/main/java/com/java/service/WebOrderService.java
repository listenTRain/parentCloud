package com.java.service;

import com.java.model.WebOrderEntity;

import java.util.Map;

/**
 * 
 * @author djin
 *    WebOrder业务层接口
 * @date 2020-03-09 10:05:59
 */
public interface WebOrderService extends BaseService<WebOrderEntity>{

    //监听mysql订单表
    void listenerOrder() throws Exception;
	
}
