package com.jeebase.ocr.baidu.service.impl;

import com.jeebase.common.base.BusinessException;
import com.jeebase.ocr.baidu.domain.BaiduAccessToken;
import com.jeebase.ocr.baidu.service.IBaiduAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BaiduAIServiceImpl implements IBaiduAIService {
    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(BaiduAIServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取access_token的URL,有效期目前为一个月
     */
    private String accessTokenUrl = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id={clientId}&client_secret={clientSecret}";


    /**
     * 从百度获取accessToken
     */
    @Override
    @Cacheable(value = "baidu", key = "'client_id_'.concat(#clientId)", condition = "#result != null")
    public BaiduAccessToken queryAccessToken(String clientId, String clientSecret) {
        BaiduAccessToken accessToken = null;
        try {
            accessToken = restTemplate.getForObject(accessTokenUrl, BaiduAccessToken.class, clientId, clientSecret);
        } catch (Exception e) {
            logger.error("调用获取百度OCRAccessToken接口异常：" + e);
            throw new BusinessException("调用获取百度OCRAccessToken接口异常。");
        }
        return accessToken;
    }
}
