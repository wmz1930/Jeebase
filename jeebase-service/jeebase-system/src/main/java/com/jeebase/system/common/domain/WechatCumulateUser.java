package com.jeebase.system.common.domain;

/**
 * @author jeebase
 */
public class WechatCumulateUser {

    private String user_source;

    private String ref_date;

    private String cumulate_user;

    public String getUser_source() {
        return user_source;
    }

    public void setUser_source(String user_source) {
        this.user_source = user_source;
    }

    public String getRef_date() {
        return ref_date;
    }

    public void setRef_date(String ref_date) {
        this.ref_date = ref_date;
    }

    public String getCumulate_user() {
        return cumulate_user;
    }

    public void setCumulate_user(String cumulate_user) {
        this.cumulate_user = cumulate_user;
    }
}
