package com.jeebase.system.common.domain;

import java.util.List;

/**
 * @author jeebase
 */
public class CumulateUserList {

    private List<WechatCumulateUser> list;

    private String errcode;

    private String errmsg;

    public List<WechatCumulateUser> getList() {
        return list;
    }

    public void setList(List<WechatCumulateUser> list) {
        this.list = list;
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
