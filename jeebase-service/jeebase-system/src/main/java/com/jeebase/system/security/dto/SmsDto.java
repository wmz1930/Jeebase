package com.jeebase.system.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @author jeebase
 */
@ApiModel(value = "SmsDto对象", description = "发送短信对象")
public class SmsDto {

    /**
     * 姓名
     */
    @Length(max = 8, min = 2, message = "姓名长度范围应该在2-8位之间。")
    @ApiModelProperty(value = "姓名", required = false, dataType = "String", notes = "姓名长度范围应该在2-8位之间。")
    private String userName;

    /**
     * 电话
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][1-9][0-9]{9}$", message = "请输入正确手机号。")
    @ApiModelProperty(value = "手机号", required = true, dataType = "String")
    private String userMobile;

    /**
     * 图片验证码
     */
    private String verkey;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    // public String getvCode()
    // {
    // return vCode;
    // }
    //
    // public void setvCode( String vCode )
    // {
    // this.vCode = vCode;
    // }

    /**
     * 验证码key
     * @return
     */
    public String getVerkey() {
        return verkey;
    }

    public void setVerkey(String verkey) {
        this.verkey = verkey;
    }
}
