package com.java.web.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.java.web.model.WebBannerEntity;
import com.java.web.service.WebBannerService;

/**
 * 
 * @author djin
 *    WebBanner业务层实现类
 * @date 2020-03-09 10:06:00
 */
@Service
@Transactional
public class WebBannerServiceImpl extends BaseServiceImpl<WebBannerEntity> implements WebBannerService {
	
}
