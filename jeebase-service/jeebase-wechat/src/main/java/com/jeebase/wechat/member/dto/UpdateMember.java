package com.jeebase.wechat.member.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
@ApiModel(value="User对象", description="好帮工用户表")
public class UpdateMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "系统用户表用户ID")
    @TableField("user_id")
    private Integer userId;

    @ApiModelProperty(value = "上级ID")
    @TableField("parent_id")
    private Integer parentId;

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

    @ApiModelProperty(value = "邮箱地址")
    @TableField("email")
    private  String email;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "项目名称")
    @TableField("entry_name")
    private String entryName;

    @ApiModelProperty(value = "真实姓名（联系人名称）")
    @TableField("realname")
    private String realname;

    @ApiModelProperty(value = "施工单位名称")
    @TableField("construction_company")
    private String constructionCompany;

    @ApiModelProperty(value = "公司名称/建设单位")
    @TableField("company_name")
    private String companyName;

    @ApiModelProperty(value = "法人代表名称/项目负责人")
    @TableField("legal_name")
    private String legalName;

    @ApiModelProperty(value = "固定电话")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty(value = "公司资质")
    @TableField("company_qualification")
    private String companyQualification;

    @ApiModelProperty(value = "经营范围")
    @TableField("business_scope")
    private String businessScope;

    @ApiModelProperty(value = "用户类型1、个人用户2、企业用户3、项目用户4、友人用户5、员工用户")
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

    @ApiModelProperty(value = "国家")
    @TableField("contry")
    private String contry;

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

    @ApiModelProperty(value = "专业")
    @TableField("major")
    private String major;

    @ApiModelProperty(value = "学历")
    @TableField("education")
    private String education;

    @ApiModelProperty(value = "个人语录")
    @TableField("quotation")
    private String quotation;

    @ApiModelProperty(value = "工种")
    @TableField("work_type")
    private String workType;

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

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Integer creator;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "最后修改人")
    @TableField(value = "operator", fill = FieldFill.UPDATE)
    private Integer operator;

    @ApiModelProperty(value = "是否删除")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;

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
    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }
    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
    public String getConstructionCompany() {
        return constructionCompany;
    }

    public void setConstructionCompany(String constructionCompany) {
        this.constructionCompany = constructionCompany;
    }
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getCompanyQualification() {
        return companyQualification;
    }

    public void setCompanyQualification(String companyQualification) {
        this.companyQualification = companyQualification;
    }
    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
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
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }
    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        ", userId=" + userId +
        ", parentId=" + parentId +
        ", wechatOpenId=" + wechatOpenId +
        ", wechatPlatformOpenId=" + wechatPlatformOpenId +
        ", wechatUnionId=" + wechatUnionId +
        ", mobile=" + mobile +
        ", nickname=" + nickname +
        ", entryName=" + entryName +
        ", realname=" + realname +
        ", constructionCompany=" + constructionCompany +
        ", companyName=" + companyName +
        ", legalName=" + legalName +
        ", telephone=" + telephone +
        ", companyQualification=" + companyQualification +
        ", businessScope=" + businessScope +
        ", userType=" + userType +
        ", avatarUrl=" + avatarUrl +
        ", userLevel=" + userLevel +
        ", contactAddress=" + contactAddress +
        ", contry=" + contry +
        ", province=" + province +
        ", city=" + city +
        ", area=" + area +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", major=" + major +
        ", education=" + education +
        ", quotation=" + quotation +
        ", workType=" + workType +
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
