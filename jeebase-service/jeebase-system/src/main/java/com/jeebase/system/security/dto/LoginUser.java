package com.jeebase.system.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @author jeebase
 */
@ApiModel(value = "LoginUser对象", description = "用户登录信息")
public class LoginUser {

    @ApiModelProperty(value = "用户账号", required = true, dataType = "String", notes = "账号长度范围应该在2-32位之间。")
    @NotBlank(message="账号不能为空")
    @Size(min=2,max=32,message="账号长度不正确")
    private String userAccount;

    @ApiModelProperty(value = "用户密码", required = true, dataType = "String")
    @NotBlank(message="密码不能为空")
    private String userPassword;

    @ApiModelProperty(value = "验证码", required = true, dataType = "String")
    @NotBlank(message="验证码不能为空")
    private String vcode;

    @ApiModelProperty(value = "验证码唯一标识", required = true, dataType = "String")
    @NotBlank(message="验证码唯一标识不能为空")
    private String verkey;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getVerkey() {
        return verkey;
    }

    public void setVerkey(String verkey) {
        this.verkey = verkey;
    }
}
