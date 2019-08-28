package com.jeebase.system.common.domain;

import java.util.List;

/**
 * @author jeebase
 */
public class SummaryUserList {

    private List<WechatSummaryUser> list;

    private String errcode;

    private String errmsg;

    public List<WechatSummaryUser> getList() {
        return list;
    }

    public void setList(List<WechatSummaryUser> list) {
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
