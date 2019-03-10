package com.jeebase.wechat.member.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 微信注册会员表
 * </p>
 *
 * @author jeebase
 * @since 2019-03-08
 */
@ApiModel(value="WechatMember对象", description="微信注册会员表")
public class CreateWechatMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "系统用户表用户ID")
    private Integer userId;

    @ApiModelProperty(value = "上级ID")
    private Integer parentId;

    @ApiModelProperty(value = "小程序用户openid")
    private String wechatOpenId;

    @ApiModelProperty(value = "公众号用户openid")
    private String wechatPlatformOpenId;

    @ApiModelProperty(value = "微信用户union id")
    private String wechatUnionId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "固定电话")
    private String telephone;

    @ApiModelProperty(value = "用户类型1、普通用户")
    private Integer userType;

    @ApiModelProperty(value = "头像url")
    private String avatarUrl;

    @ApiModelProperty(value = "会员积分")
    private Integer memberPoints;

    @ApiModelProperty(value = "国家")
    private String contry;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "联系地址")
    private String contactAddress;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "出生日期")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "是否记住密码")
    private Boolean remember;

    @ApiModelProperty(value = "注册日期")
    private LocalDateTime registerTime;

    @ApiModelProperty(value = "注册ip")
    private String registerIp;

    @ApiModelProperty(value = "最后登录日期")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "最后登录ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "备注")
    private String comments;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private Integer creator;

    @ApiModelProperty(value = "最后修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最后修改人")
    private Integer operator;

    @ApiModelProperty(value = "是否删除")
    private String delFlag;

    private Integer organizationId;

    private Integer roleId;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
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
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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
    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }
    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
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
    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
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
    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
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
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public String toString() {
        return "WechatMember{" +
                "id=" + id +
                ", userId=" + userId +
                ", parentId=" + parentId +
                ", wechatOpenId=" + wechatOpenId +
                ", wechatPlatformOpenId=" + wechatPlatformOpenId +
                ", wechatUnionId=" + wechatUnionId +
                ", nickname=" + nickname +
                ", realname=" + realname +
                ", telephone=" + telephone +
                ", userType=" + userType +
                ", avatarUrl=" + avatarUrl +
                ", memberPoints=" + memberPoints +
                ", contry=" + contry +
                ", province=" + province +
                ", city=" + city +
                ", area=" + area +
                ", contactAddress=" + contactAddress +
                ", gender=" + gender +
                ", birthday=" + birthday +
                ", remember=" + remember +
                ", registerTime=" + registerTime +
                ", registerIp=" + registerIp +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp=" + lastLoginIp +
                ", comments=" + comments +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", operator=" + operator +
                ", delFlag=" + delFlag +
                "}";
    }
}
