package com.jeebase.system.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.common.base.domain.ZTree;
import com.jeebase.system.security.entity.Organization;

import java.util.List;

/**
 *
 * @author jeebase
 */
public interface IOrganizationService extends IService<Organization> {

    /**
     * 查询机构列表
     * @param parentId
     * @return
     */
    List<Organization> queryOrganizationByPanentId(Integer parentId);

    /**
     * 查询机构列表，不组装父子节点
     * @param parentId
     * @return
     */
    List<Organization> queryOrgList(Integer parentId);
}
