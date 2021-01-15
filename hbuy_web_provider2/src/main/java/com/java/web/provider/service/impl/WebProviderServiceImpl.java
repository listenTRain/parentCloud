package com.java.web.provider.service.impl;

import com.java.service.WebProviderService;
import org.springframework.stereotype.Service;

/**
 * author:快乐风男
 * time:11:47
 */
@Service
public class WebProviderServiceImpl implements WebProviderService{
    private Integer count = 0;

    @Override
    public String testRibbon(String userName) throws Exception {
        System.out.println("这里是provider2执行的请求!!"+count);
        count++;
        return userName+"--provider2"+count;
    }
    //测试ribbon的负载均衡的搭建

}
