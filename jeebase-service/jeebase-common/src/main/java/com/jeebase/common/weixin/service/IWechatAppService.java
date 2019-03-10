package com.jeebase.common.weixin.service;

import com.jeebase.common.weixin.domain.UserInfoWithCredentials;

/**
 *
 * @author jeebase
 */
public interface IWechatAppService {

    /**
     * 获取用户信息
     * @param code
     * @param userInfo
     * @return
     */
    String queryUserInfo(String code, UserInfoWithCredentials userInfo);
}
