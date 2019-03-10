package com.jeebase.system.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.security.dto.UpdateRoleResource;
import com.jeebase.system.security.entity.Resource;
import com.jeebase.system.security.entity.RoleResource;

import java.util.List;

/**
 * @ClassName: IRoleService
 * @Description: 角色相关操作接口
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午3:21:24
 */
public interface IRoleResourceService extends IService<RoleResource> {

    /**
     * 根据角色查询菜单
     * 
     * @param roleId
     *            角色主键
     * @param status
     *            状态(0：禁用；1：启用)
     * @return
     */
    List<Resource> queryResourceByRoleId(Integer roleId);

    /**
     * 更新角色的权限
     * @param updateRoleResource
     * @return
     */
    boolean updateList(UpdateRoleResource updateRoleResource);
}
