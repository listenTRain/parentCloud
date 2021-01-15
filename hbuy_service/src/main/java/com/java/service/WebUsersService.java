package com.java.service;

import com.java.model.WebUsersEntity;

import java.util.Map;

/**
 * 
 * @author djin
 *    WebUsers业务层接口
 * @date 2020-03-07 10:04:52
 */
public interface WebUsersService extends BaseService<WebUsersEntity>{
    //用户登陆
    Map<String,Object> loginUser(WebUsersEntity users) throws Exception;
	
}
