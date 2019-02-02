package com.jeebase.system.security.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.common.base.domain.ZTree;
import com.jeebase.system.security.entity.Resource;

/**
 * @author jeebase
 */
public interface IResourceService extends IService<Resource> {

    /**
     * 查询用户资源角色
     * @param userId
     * @return
     */
    List<Resource> queryResourceByUserId(Integer userId);

    /**
     * 查询资源权限列表
     * @param parentId
     * @return
     */
    List<Resource> queryResourceByParentId(Integer parentId);

    /**
     * 查询资源权限列表
     * @param parentId
     * @param assembleChildren
     * @return
     */
    List<ZTree> queryResourceTree(Integer parentId, boolean assembleChildren);

    /**
     * 查询资源权限列表
     * @param wrapper
     * @return
     */
    List<Resource> selectResourceList(QueryWrapper<Resource> wrapper);

    /**
     * 创建资源权限
     * @param resource
     * @return
     */
    boolean createResource(Resource resource);

    /**
     * 更新资源权限
     * @param resource
     * @return
     */
    boolean updateResource(Resource resource);

    /**
     * 删除资源权限
     * @param resourceId
     * @return
     */
    boolean deleteResource(Integer resourceId);
}
