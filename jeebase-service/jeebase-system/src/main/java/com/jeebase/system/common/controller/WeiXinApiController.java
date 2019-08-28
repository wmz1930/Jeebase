package com.jeebase.system.common.controller;

import com.jeebase.common.annotation.auth.NoAuthentication;
import com.jeebase.common.base.Result;
import com.jeebase.system.common.dto.WeiXinJsUrl;
import com.jeebase.system.common.service.IWechatApiService;
import com.jeebase.system.common.util.WeiXinSign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author WANGLEI
 */
@RestController
@RequestMapping("/weixin")
public class WeiXinApiController
{
    @Value("${weixin.api.appId}")
    private String appId;

    @Value("${weixin.api.secret}")
    private String appSecret;

    @Resource
    private IWechatApiService weiXinApiService;
    
    
    @PostMapping("/js/config")
    @NoAuthentication
    public Result<Map<String, String>> jsConfig(@RequestBody WeiXinJsUrl jsUrl) {
        String ticket = weiXinApiService.getJsapiTicket(appId, appSecret);
        Map<String, String> signMap = WeiXinSign.sign(ticket, jsUrl.getUrl());
        signMap.put("appid", appId);
        return new Result<Map<String, String>>().success().put(signMap);
    }
}
