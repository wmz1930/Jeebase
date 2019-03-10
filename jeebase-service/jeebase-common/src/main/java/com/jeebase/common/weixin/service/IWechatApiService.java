package com.jeebase.common.weixin.service;

import com.jeebase.common.weixin.domain.*;

import java.util.Map;

/**
 *
 * @author jeebase
 */
public interface IWechatApiService {

    /**
     * 
     * getAccessToken(从缓存获取微信的AccessToken)
     *
     * @Title: getAccessToken
     * @Description: 因为微信的accessToken有效期为2个小时，所以放到缓存，2小时取一次
     * @param appId
     * @param appSecret
     * @return
     */
    String getAccessToken(String appId, String appSecret);

    /**
     * 
     * queryAccessToken(获取微信的AccessToken)
     *
     * @Title: queryAccessToken
     * @param appId
     * @param appSecret
     * @return
     */
    AccessToken queryAccessToken(String appId, String appSecret);

    /**
     * 
     * getJsapiTicket(从缓存获取ticket)
     *
     * @Title: getJsapiTicket
     * @Description: 因为微信的JsapiTicket有效期为2个小时，所以放到缓存，2小时取一次
     * @param appId
     * @param appSecret
     * @return
     */
    String getJsapiTicket(String appId, String appSecret);

    /**
     * 
     * queryJsapiTicket(获取微信的JsapiTicket)
     *
     * @Title: queryJsapiTicket
     * @Description: 因为微信的JsapiTicket有效期为2个小时，所以放到缓存，2小时取一次
     * @param appId
     * @param appSecret
     * @return
     */
    JsapiTicket queryJsapiTicket(String appId, String appSecret);

    /**
     * 
     * signTicket(调用jssdk时的签名方法)
     *
     * @Title: signTicket
     * @Description:
     * @param appId
     * @param appSecret
     * @param url
     * @return
     */
    Map<String, String> signTicket(String appId, String appSecret, String url);

    /**
     * 
     * queryTemplateList(查询配置的模版列表)
     *
     * @Title: queryTemplateList
     * @param appId
     * @param appSecret
     * @return
     */
    MsgTemplateList queryTemplateList(String appId, String appSecret);

    /**
     * 
     * queryAttentionUserList(从微信获取关注的用户列表)
     *
     * @Title: queryAttentionUserList
     * @Description:
     * @param appId
     * @param appSecret
     * @param nextOpenId
     * @return
     */
    AttentionUserList queryAttentionUserList(String appId, String appSecret, String nextOpenId);

    /**
     * 
     * sendWeiXinMsg(给指定用户发送模版消息)
     *
     * @Title: sendWeiXinMsg
     * @param appId
     * @param appSecret
     * @param msgSend
     * @return
     */
    MsgSendResponse sendWeiXinMsg(String appId, String appSecret, MsgSendRequest msgSend);

    /**
     * 
     * summaryUser(获取用户增减数据)
     *
     * @Title: summaryUser
     * @param appId
     * @param appSecret
     * @param suRequest
     * @return
     */
    SummaryUserList summaryUser(String appId, String appSecret, SummaryUserRequest suRequest);

    /**
     * 
     * cumulateUser(获取累计用户数据)
     *
     * @Title: cumulateUser
     * @param appId
     * @param appSecret
     * @param suRequest
     * @return
     */
    CumulateUserList cumulateUser(String appId, String appSecret, SummaryUserRequest suRequest);

    /**
     * 
     * queryUserInfoList(批量获取用户信息)
     *
     * @Title: queryUserInfoList
     * @Description:
     * @param appId
     * @param appSecret
     * @param userListReq
     * @return
     */
    UserListInfoResponse queryUserInfoList(String appId, String appSecret, UserListInfoRequest userListReq);

    /**
     * 
     * queryAppId(通过code获取微信openId)
     *
     * @Title: queryAppId
     * @Description:
     * @param appId
     * @param appSecret
     * @param code
     * @return
     */
    QueryOpenIdResponse queryAppId(String appId, String appSecret, String code);

    SnsUserInfo querySnsUserInfo(String accessToken, String openId);
}
