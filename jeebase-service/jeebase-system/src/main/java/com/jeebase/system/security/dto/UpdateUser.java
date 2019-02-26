
package com.jeebase.system.security.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-26
 */
public class UpdateUser implements Serializable
{

    /**
      * @Fields serialVersionUID : 
      */
    
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 昵称
     */
    private String userNickName;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 1 : 男，0 : 女
     */
    private String userSex;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 电话
     */
    private String userMobile;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * '0'禁用，'1' 启用, '2' 密码过期或初次未修改
     */
    private String userStatus;

    /**
     * 
     */
    private String headImgUrl;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 街道详细地址
     */
    private String street;
    /**
     * vue级联选择
     */
    private List<String> areas;
    /**
     * 备注
     */
    private String description;
    
    private Integer roleId;

    @ApiModelProperty(value = "组织机构id")
    private Integer organizationId;

    /**
     * 角色数组
     */
    @ApiModelProperty(value = "用户角色ID数组")
    private List<Integer> roleIds;
    
    private String newPwd;
    
    private String oldPwd;
    
    private String smsCode;

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount( String userAccount )
    {
        this.userAccount = userAccount;
    }

    public String getUserNickName()
    {
        return userNickName;
    }

    public void setUserNickName( String userNickName )
    {
        this.userNickName = userNickName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getUserSex()
    {
        return userSex;
    }

    public void setUserSex( String userSex )
    {
        this.userSex = userSex;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail( String userEmail )
    {
        this.userEmail = userEmail;
    }

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

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl( String headImgUrl )
    {
        this.headImgUrl = headImgUrl;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince( String province )
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity( String city )
    {
        this.city = city;
    }

    public String getArea()
    {
        return area;
    }

    public void setArea( String area )
    {
        this.area = area;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet( String street )
    {
        this.street = street;
    }

    public String getDescription()
    {
        return description;
    }

    public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId( Integer roleId )
    {
        this.roleId = roleId;
    }


    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }


    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public List<String> getAreas() {
		return areas;
	}

	public void setAreas(List<String> areas) {
		this.areas = areas;
	}

	@Override
    public String toString()
    {
        return "User{" + "id=" + id + ", userAccount=" + userAccount + ", userNickName=" + userNickName + ", userName="
                + userName + ", userSex=" + userSex + ", userEmail=" + userEmail + ", userMobile=" + userMobile
                + ", userPassword=" + userPassword + ", userStatus=" + userStatus + ", headImgUrl=" + headImgUrl
                + ", province=" + province + ", city=" + city + ", area=" + area + ", street=" + street
                + ", description=" + description + "}";
    }
}
