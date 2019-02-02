
package com.jeebase.system.security.dto;

import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
public class CreateRole
{

    private Integer id;
    
    private Integer parentId;
    
    private String roleName;
    
    private String roleKey;
    
    private Integer roleLevel;
    
    private String roleStatus;
    
    private String description;
    
    private Date createTime;
    
    private Integer creator;
    
    private Date updateTime;
    
    private Integer operator;
    
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getParentId()
    {
        return parentId;
    }

    public void setParentId( Integer parentId )
    {
        this.parentId = parentId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName( String roleName )
    {
        this.roleName = roleName;
    }

    public String getRoleKey()
    {
        return roleKey;
    }

    public void setRoleKey( String roleKey )
    {
        this.roleKey = roleKey;
    }

    public Integer getRoleLevel()
    {
        return roleLevel;
    }

    public void setRoleLevel( Integer roleLevel )
    {
        this.roleLevel = roleLevel;
    }

    public String getRoleStatus() {
		return roleStatus;
	}

	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}

	public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime( Date createTime )
    {
        this.createTime = createTime;
    }

    public Integer getCreator()
    {
        return creator;
    }

    public void setCreator( Integer creator )
    {
        this.creator = creator;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime( Date updateTime )
    {
        this.updateTime = updateTime;
    }

    public Integer getOperator()
    {
        return operator;
    }

    public void setOperator( Integer operator )
    {
        this.operator = operator;
    }

    @Override
    public String toString()
    {
        return "Role{" + "id=" + id + ", parentId=" + parentId + ", roleName=" + roleName + ", roleKey=" + roleKey
                + ", roleStatus=" + roleStatus + ", description=" + description + ", createTime=" + createTime + ", creator="
                + creator + ", updateTime=" + updateTime + ", operator=" + operator + "}";
    }
}
