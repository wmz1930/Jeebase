package com.jeebase.system.common.domain;

/**
 * @author jeebase
 */
public class WechatSummaryUser {

    private String ref_date;

    private int user_source;

    private int new_user;

    private int cancel_user;

    public String getRef_date() {
        return ref_date;
    }

    public void setRef_date(String ref_date) {
        this.ref_date = ref_date;
    }

    public int getUser_source() {
        return user_source;
    }

    public void setUser_source(int user_source) {
        this.user_source = user_source;
    }

    public int getNew_user() {
        return new_user;
    }

    public void setNew_user(int new_user) {
        this.new_user = new_user;
    }

    public int getCancel_user() {
        return cancel_user;
    }

    public void setCancel_user(int cancel_user) {
        this.cancel_user = cancel_user;
    }
}
