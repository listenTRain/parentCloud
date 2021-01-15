package com.java.web.provider.service.impl;

import com.java.service.WebProviderService;
import org.springframework.stereotype.Service;

/**
 * author:快乐风男
 * time:10:58
 */
@Service
public class WebProviderServiceImpl implements WebProviderService{
    private Integer count = 0;
    @Override
    public String testRibbon(String userName) throws Exception {
        System.out.println("这里是provider1执行的请求。。"+count);
        count++;
        return userName+"--provider1"+count;
    }
}
