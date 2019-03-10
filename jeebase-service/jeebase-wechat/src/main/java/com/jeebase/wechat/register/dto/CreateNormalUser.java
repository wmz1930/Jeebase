package com.jeebase.wechat.register.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 好帮工用户表
 * </p>
 *
 * @author jeebase
 * @since 2018-11-09
 */
@ApiModel(value="CreateNormalUser", description="好帮工普通用户")
public class CreateNormalUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "系统用户表用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "小程序用户openid")
    @TableField("wechat_open_id")
    private String wechatOpenId;

    @ApiModelProperty(value = "公众号用户openid")
    @TableField("wechat_platform_open_id")
    private String wechatPlatformOpenId;

    @ApiModelProperty(value = "微信用户union id")
    @TableField("wechat_union_id")
    private String wechatUnionId;

    @ApiModelProperty(value = "手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    @TableField("realname")
    private String realname;

    @ApiModelProperty(value = "用户类型1、个人用户")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty(value = "头像url")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "会员积分")
    @TableField("user_level")
    private Integer userLevel;

    @ApiModelProperty(value = "联系地址")
    @TableField("contact_address")
    private String contactAddress;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("area")
    private String area;

    @ApiModelProperty(value = "性别")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "出生日期")
    @TableField("birthday")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "注册日期")
    @TableField("register_time")
    private LocalDateTime registerTime;

    @ApiModelProperty(value = "注册ip")
    @TableField("register_ip")
    private String registerIp;

    @ApiModelProperty(value = "最后登录日期")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最后登录ip")
    @TableField("last_login_ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "备注")
    @TableField("comments")
    private String comments;

    @ApiModelProperty(value = "用户账号", required = true, dataType = "String", notes = "账号长度范围应该在2-32位之间。")
    @NotBlank(message="账号不能为空")
    @Size(min=2,max=32,message="账号长度不正确")
    private String userAccount;
    
    @ApiModelProperty(value = "密码")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,18}$", message="密码格式不正确")
    private String userPassword;

    /**
     * 短信验证码
     */
    @NotBlank(message = "短信验证码不能为空")
    @Length(max = 6, min = 6, message = "请输入6位数短信验证码。")
    @ApiModelProperty(value = "短信验证码", required = true, dataType = "String", notes = "输入6位数短信验证码。")
    private String smsCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }
    public String getWechatPlatformOpenId() {
        return wechatPlatformOpenId;
    }

    public void setWechatPlatformOpenId(String wechatPlatformOpenId) {
        this.wechatPlatformOpenId = wechatPlatformOpenId;
    }
    public String getWechatUnionId() {
        return wechatUnionId;
    }

    public void setWechatUnionId(String wechatUnionId) {
        this.wechatUnionId = wechatUnionId;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }
    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public String getUserPassword() {
        return userPassword;
    }

    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }


    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", userId=" + userId +
        ", wechatOpenId=" + wechatOpenId +
        ", wechatPlatformOpenId=" + wechatPlatformOpenId +
        ", wechatUnionId=" + wechatUnionId +
        ", mobile=" + mobile +
        ", nickname=" + nickname +
        ", realname=" + realname +
        ", userType=" + userType +
        ", avatarUrl=" + avatarUrl +
        ", userLevel=" + userLevel +
        ", contactAddress=" + contactAddress +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", registerTime=" + registerTime +
        ", registerIp=" + registerIp +
        ", lastLoginTime=" + lastLoginTime +
        ", lastLoginIp=" + lastLoginIp +
        ", comments=" + comments +
        "}";
    }
}
