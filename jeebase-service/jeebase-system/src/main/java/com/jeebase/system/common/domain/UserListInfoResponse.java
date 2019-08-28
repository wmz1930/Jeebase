package com.jeebase.system.common.domain;

/**
 * @author jeebase
 */
public class UserListInfoResponse {

    private WechatUserInfo[] user_info_list;

    private String errcode;

    private String errmsg;

    public WechatUserInfo[] getUser_info_list() {
        return user_info_list;
    }

    public void setUser_info_list(WechatUserInfo[] user_info_list) {
        this.user_info_list = user_info_list;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
