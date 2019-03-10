package com.jeebase.wechat.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * <p>
 * 手机修改对象
 * </p>
 *
 * @author jeebase
 * @since 2018-11-09
 */
@ApiModel(value="手机修改对象", description="")
public class UpdateMobile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    /**
     * 短信验证码
     */
    @NotBlank(message="验证码不能为空")
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
