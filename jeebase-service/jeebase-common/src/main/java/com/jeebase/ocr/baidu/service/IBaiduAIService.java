package com.jeebase.ocr.baidu.service;

import com.jeebase.ocr.baidu.domain.BaiduAccessToken;

/**
 * @author WANGLEI
 */
public interface IBaiduAIService {

    /**
     *
     * queryAccessToken(获取百度AI的AccessToken)
     *
     * @Title: queryAccessToken
     * @param clientId
     * @param clientSecret
     * @return
     */
    BaiduAccessToken queryAccessToken(String clientId, String clientSecret);
}
