package com.java.orders.utils;

import java.security.MessageDigest;
import java.util.UUID;

/**
 *  订单编号生成的工具类
 */
public class OrderUtils {

    private static final String[] digital = {"0","1","2","3","4","5","6","7","8","9"};

    /**
     * 生成唯一的订单编号
     * @param uid  用户的编号
     * @return
     * @throws Exception
     */
    public static String generateOrderNo(Integer uid) throws Exception {
        String orderStr = UUID.randomUUID().toString() +Math.random()+ uid;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(orderStr.getBytes("UTF-8"));
        StringBuffer orderNo = new StringBuffer();
        for (byte b : bytes){
            int temp = b;
            if(temp<0){
                temp=-temp;
            }
            int i = temp/16;//计算出范围在0-15之间的下标
            orderNo.append(digital[i]);
        }
        return orderNo.toString();
    }

    public static void main(String[] args) throws Exception {
        String orderNo = OrderUtils.generateOrderNo(2);//1741720451772012
        System.out.println("orderNo="+orderNo);
    }

}
