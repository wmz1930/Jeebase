package com.jeebase.wechat.register.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author jeebase
 */
@ApiModel(value = "SmsDto对象", description = "发送短信对象")
public class WeChatSms {

    /**
     * 电话
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][1-9][0-9]{9}$", message = "请输入正确手机号。")
    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String mobile;

    /**
     * 图片验证码
     */
    private String smsCode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}

