package com.jeebase.system.security.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.common.base.domain.ZTree;
import com.jeebase.system.security.entity.Organization;

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
     * 查询机构树
     * @param parentId
     * @return
     */
    List<ZTree> queryOrgTree(Integer parentId);
}
