package com.jeebase.wechat.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 * 修改密码对象
 * </p>
 *
 * @author jeebase
 * @since 2018-11-09
 */
@ApiModel(value="修改密码", description="")
public class RetrievePassword implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    /**
     * 短信验证码
     */
    @NotBlank(message="验证码不能为空")
    private String smsCode;

    @ApiModelProperty(value = "密码")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message="密码格式不正确")
    private String oldPassword;

    @ApiModelProperty(value = "密码")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message="密码格式不正确")
    private String password;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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
