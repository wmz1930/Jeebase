package com.jeebase.common.weixin.domain;

import java.util.List;

/**
 * @author jeebase
 */
public class CumulateUserList {

    private List<WeiXinCumulateUser> list;

    private String errcode;

    private String errmsg;

    public List<WeiXinCumulateUser> getList() {
        return list;
    }

    public void setList(List<WeiXinCumulateUser> list) {
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
