package com.jeebase.system.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.security.entity.OrganizationRole;
import com.jeebase.system.security.mapper.OrganizationRoleMapper;
import com.jeebase.system.security.service.IOrganizationRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 可以给组织权限，在该组织下的所有用户都有此权限 服务实现类
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
@Service
public class OrganizationRoleServiceImpl extends ServiceImpl<OrganizationRoleMapper, OrganizationRole>
        implements IOrganizationRoleService {
}
