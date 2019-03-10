package com.jeebase.wechat.login.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * @author jeebase
 */
@ApiModel(value = "LoginUser对象", description = "用户登录信息")
public class WebChatLoginUser {

    @ApiModelProperty(value = "微信id", required = true, dataType = "String")
    @NotBlank(message="微信id不能为空")
    private String openId;

    @ApiModelProperty(value = "用户账号", required = true, dataType = "String", notes = "账号长度范围应该在2-32位之间。")
    @NotBlank(message="账号不能为空")
    @Size(min=2,max=32,message="账号长度不正确")
    private String mobile;

    @ApiModelProperty(value = "用户密码", required = true, dataType = "String")
    @NotBlank(message="密码不能为空")
    private String password;


    @ApiModelProperty(value = "是否记住密码", required = true, dataType = "boolean")
    private boolean remember;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
