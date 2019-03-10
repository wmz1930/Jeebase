package com.jeebase.system.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@TableName("t_sys_user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账号")
    @TableField("user_account")
    private String userAccount;

    @ApiModelProperty(value = "昵称")
    @TableField("user_nick_name")
    private String userNickName;

    @ApiModelProperty(value = "姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "1 : 男，0 : 女")
    @TableField("user_sex")
    private String userSex;

    @ApiModelProperty(value = "邮箱")
    @TableField("user_email")
    private String userEmail;

    @ApiModelProperty(value = "电话")
    @TableField("user_mobile")
    private String userMobile;

    @ApiModelProperty(value = "密码")
    @TableField("user_password")
    private String userPassword;

    @ApiModelProperty(value = "'0'禁用,'1' 启用, '2' 密码过期或初次未修改")
    @TableField("user_status")
    private String userStatus;

    @TableField("head_img_url")
    private String headImgUrl;

    @ApiModelProperty(value = "省")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区")
    @TableField("area")
    private String area;

    @ApiModelProperty(value = "街道详细地址")
    @TableField("street")
    private String street;

    @ApiModelProperty(value = "备注")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "创建日期")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建者")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Integer creator;

    @ApiModelProperty(value = "更新日期")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "更新者")
    @TableField(value = "operator", fill = FieldFill.UPDATE)
    private Integer operator;

    @ApiModelProperty(value = "1:删除 0:不删除")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
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
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", userAccount=" + userAccount +
        ", userNickName=" + userNickName +
        ", userName=" + userName +
        ", userSex=" + userSex +
        ", userEmail=" + userEmail +
        ", userMobile=" + userMobile +
        ", userPassword=" + userPassword +
        ", userStatus=" + userStatus +
        ", headImgUrl=" + headImgUrl +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", street=" + street +
        ", description=" + description +
        ", createTime=" + createTime +
        ", creator=" + creator +
        ", updateTime=" + updateTime +
        ", operator=" + operator +
        ", delFlag=" + delFlag +
        "}";
    }
}
