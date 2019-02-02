package com.jeebase.system.common.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeebase.common.base.Result;
import com.jeebase.system.config.QiNiuProperties;
import com.qiniu.util.Auth;

/**
 * @author jeebase
 */
@RestController
@RequestMapping("/qiniu")
public class QiNiuYunController {

    /**
     * 七牛配置
     */
    @Autowired
    private QiNiuProperties qiniu;

    /**
     * 查询上传token
     */
    @GetMapping("/token")
    @RequiresAuthentication
    public Result<Map<String, String>> tokenInfo() {
        Auth auth = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey());
        String upToken = auth.uploadToken(qiniu.getBucket());
        Map<String, String> qiniuMap = new HashMap<String, String>();
        qiniuMap.put("token", upToken);
        qiniuMap.put("bucket", qiniu.getBucket());
        qiniuMap.put("baseUrl", qiniu.getBaseUrl());
        return new Result<Map<String, String>>().success().put(qiniuMap);
    }
}
