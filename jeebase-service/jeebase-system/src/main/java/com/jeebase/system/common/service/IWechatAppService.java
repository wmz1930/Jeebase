package com.jeebase.system.common.service;

import com.jeebase.system.common.domain.UserInfoWithCredentials;

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
