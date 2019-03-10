package com.jeebase.system.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.system.security.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询角色列表
     * @param page
     * @param role
     * @return
     */
    Page<Role> selectRoleList(Page<Role> page, @Param("role") Role role);
}
