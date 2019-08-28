package com.jeebase.system.common.domain;

/**
 * @author jeebase
 */
public class AttentionUserList {

    private String total;

    private String count;

    private AttentionUser data;

    private String next_openid;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public AttentionUser getData() {
        return data;
    }

    public void setData(AttentionUser data) {
        this.data = data;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }
}
