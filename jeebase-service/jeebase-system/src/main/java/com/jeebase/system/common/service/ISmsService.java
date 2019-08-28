package com.jeebase.system.common.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;

/**
 *
 * @author jeebase
 */
public interface ISmsService {


    /**
     * 短信发送
     * @param request
     */
    void sendSms(SendSmsRequest request);

    /**
     * 短信查询
     * @param request
     * @return
     */
    QuerySendDetailsResponse querySendDetails(QuerySendDetailsRequest request);

    /**
     * 发送告警短信
     * @param jsonParam
     */
    void sendWarnSms(String jsonParam);

    /**
     * 发送短信验证码
     * @param phoneNumbers
     * @param smsCode
     */
    void sendVcodeSms(String phoneNumbers, String smsCode);
}
