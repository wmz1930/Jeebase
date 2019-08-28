package com.jeebase.system.common.domain;

import java.util.List;

/**
 * @author jeebase
 */
public class MsgTemplateList {

    private List<MsgTemplate> template_list;

    private String errcode;

    private String errmsg;

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

    public List<MsgTemplate> getTemplate_list() {
        return template_list;
    }

    public void setTemplate_list(List<MsgTemplate> template_list) {
        this.template_list = template_list;
    }
}
