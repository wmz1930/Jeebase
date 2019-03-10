package com.jeebase.wechat.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
public class UpdatePassword implements Serializable {

    private static final long serialVersionUID = 1L;

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

}
