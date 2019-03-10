
package com.jeebase.system.security.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
public class PwdUser implements Serializable
{

    /**
     * @Fields serialVersionUID :
     */

    private static final long serialVersionUID = 1L;

    /**
     * 电话
     */
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp="^[1][1-9][0-9]{9}$", message="请输入正确手机号。")
    private String userMobile;
    
    /**
     * 密码
     */
    @NotBlank(message="密码不能为空")
    @Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$", message="请输入6-18位，不含特殊字符密码。")
    private String userPassword;
    
    /**
     * 图片验证码
     */
//    @NotBlank(message="图片验证码不能为空")
//    private String vCode;
    
    @NotBlank(message="不能为空")
    private String verkey;
    
    /**
     * 短信验证码
     */
    @NotBlank(message="短信验证码不能为空")
    @Length(max=6, min=6, message="请输入6位数短信验证码。")
    private String smsCode;

    /**
     * '0'禁用，'1' 启用, '2' 密码过期或初次未修改
     */
    private String userStatus;

    private Integer roleId;

    public String getUserMobile()
    {
        return userMobile;
    }

    public void setUserMobile( String userMobile )
    {
        this.userMobile = userMobile;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword( String userPassword )
    {
        this.userPassword = userPassword;
    }

    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus( String userStatus )
    {
        this.userStatus = userStatus;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer roleId )
    {
        this.roleId = roleId;
    }

//    public String getvCode()
//    {
//        return vCode;
//    }
//
//    public void setvCode( String vCode )
//    {
//        this.vCode = vCode;
//    }

    public String getSmsCode()
    {
        return smsCode;
    }

    public void setSmsCode( String smsCode )
    {
        this.smsCode = smsCode;
    }

    public String getVerkey()
    {
        return verkey;
    }

    public void setVerkey( String verkey )
    {
        this.verkey = verkey;
    }

    @Override
    public String toString()
    {
        return "User{" + " userMobile=" + userMobile
                + ", userPassword=" + userPassword + ", userStatus="
                + userStatus;
    }
}
