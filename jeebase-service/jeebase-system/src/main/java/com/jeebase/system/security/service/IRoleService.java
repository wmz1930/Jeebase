package com.jeebase.system.security.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.security.dto.CreateRole;
import com.jeebase.system.security.dto.UpdateRole;
import com.jeebase.system.security.entity.Role;

/**
 * @ClassName: IRoleService
 * @Description: 角色相关操作接口
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:21:24
 */
public interface IRoleService extends IService<Role> {

    /**
     * 分页查询角色列表
     * @param page
     * @param role
     * @return
     */
    Page<Role> selectRoleList(Page<Role> page, Role role);

    /**
     * 创建角色
     * @param role
     * @return
     */
    boolean createRole(CreateRole role);

    /**
     * 更新角色
     * @param role
     * @return
     */
    boolean updateRole(UpdateRole role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    boolean deleteRole(Integer roleId);

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    boolean batchDeleteRole(List<Integer> roleIds);
}
