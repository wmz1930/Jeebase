package com.jeebase.system.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.common.domain.*;
import com.jeebase.system.common.service.IWechatApiService;
import com.jeebase.system.common.service.IWechatTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author jeebase
 */
@Service
public class WechatTokenServiceImpl implements IWechatTokenService {

    /**
     * 日志记录
     */
    private static final Logger logger = LoggerFactory.getLogger(WechatTokenServiceImpl.class);

    private static final String WEIXIN_TOKEN_KEY = "weiXin";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取access_token的URL,有效期目前为2个小时
     */
    private String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appId}&secret={appSecret}";

    /**
     * 从缓存获取accessToken
     */
    @Override
    @Cacheable(value = WEIXIN_TOKEN_KEY, key = "'app_id_'.concat(#appId)")
    public String getAccessToken(String appId, String appSecret) {
        String tokenString = null;
        AccessToken accessToken = queryAccessToken(appId, appSecret);
        logger.info("调用获取微信accessToken返回值：" + JSONObject.toJSON(accessToken));
        if (!StringUtils.isEmpty(accessToken.getAccess_token())) {
            tokenString = accessToken.getAccess_token();
        }
        return tokenString;
    }

    /**
     * 从微信获取accessToken
     */
    @Override
    public AccessToken queryAccessToken(String appId, String appSecret) {
        AccessToken accessToken = null;
        try {
            accessToken = restTemplate.getForObject(accessTokenUrl, AccessToken.class, appId, appSecret);
        } catch (Exception e) {
            logger.error("调用获取微信AccessToken接口异常：" + e);
            throw new BusinessException("调用获取微信AccessToken接口异常。");
        }
        return accessToken;
    }
}
