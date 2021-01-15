package com.java.service;

import com.java.model.Good;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *   购物车业务层接口
 */
public interface BuyCarService {

    /**
     *   未登录的情况下实现购物车添加
     * @param goodId  商品id
     * @param num  商品数量
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    Map<String, Object> addBuyCar(Integer goodId, Integer num, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     *   未登录的情况下实现购物车添加
     * @param goodId  商品id
     * @param num  商品数量
     * @param uid  登陆的用户id
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    Map<String, Object> addBuyCarAfterLogin(Integer goodId, Integer num,Integer uid, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     *   用户登录的时候对购物车数据进行合并
     * @param uid  登陆的用户id
     * @param request  请求对象
     * @param response  响应对象
     * @return  添加的结果
     * @throws Exception
     */
    Map<String, Object> appendBuyCarByLogin(Integer uid, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     *   查询购物车数据
     * @param token  查找登陆用户数据的令牌
     * @param request  请求对象
     * @return  购物车数据
     * @throws Exception
     */
    List<Good> findBuyCar(String token, HttpServletRequest request) throws Exception;

    /**
     *   删除已提交的购物车商品数据
     * @param uid  用户id
     * @param proIds  选中的商品id
     * @throws Exception
     */
    void delGoodByProIds(Integer uid, String proIds) throws Exception;

}
