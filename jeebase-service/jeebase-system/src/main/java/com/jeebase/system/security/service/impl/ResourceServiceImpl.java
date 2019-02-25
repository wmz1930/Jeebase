package com.jeebase.system.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.domain.ZTree;
import com.jeebase.system.security.entity.Resource;
import com.jeebase.system.security.mapper.ResourceMapper;
import com.jeebase.system.security.service.IResourceService;

/**
 * @author jeebase
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    /**
     * 异常日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);
    
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @CacheEvict(value = "resources", allEntries = true)
    public boolean createResource(Resource resource) {
        boolean result = this.save(resource);
        return result;
    }

    @Override
    @CacheEvict(value = "resources", allEntries = true)
    public boolean updateResource(Resource resource) {
        boolean result = this.updateById(resource);
        return result;
    }

    @Override
    @CacheEvict(value = "resources", allEntries = true)
    public boolean deleteResource(Integer resourceId) {
        QueryWrapper<Resource> wpd = new QueryWrapper<Resource>();
        wpd.and(e -> e.eq("id", resourceId).or().eq("parent_id", resourceId));
        boolean result = this.remove(wpd);
        return result;
    }

    @Override
    @Cacheable(value = "resources", key = "resources_all")
    public List<Resource> selectResourceList(QueryWrapper<Resource> wrapper) {
        List<Resource> list = this.list(wrapper);
        return list;
    }

    @Override
    @Cacheable(value = "resources", key = "'user_id_'.concat(#userId)")
    public List<Resource> queryResourceByUserId(Integer userId) {
        List<Resource> resourceList = resourceMapper.queryResourceByUserId(userId);
        List<Resource> menus = new ArrayList<Resource>();
        Map<Integer, Resource> resourceMap = new HashMap<Integer, Resource>();
        // 组装子父级目录
        for (Resource resource : resourceList) {
            resourceMap.put(resource.getId(), resource);
        }
        for (Resource resource : resourceList) {
            Integer treePId = resource.getParentId();
            Resource pTreev = resourceMap.get(treePId);
            if (null != pTreev && !resource.equals(pTreev)) {
                List<Resource> nodes = pTreev.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<Resource>();
                    pTreev.setChildren(nodes);
                }
                nodes.add(resource);
            } else {
                menus.add(resource);
            }
        }
        return menus;
    }

    @Override
    @Cacheable(value = "resources", key = "'all_user_id_'.concat(#userId)")
    public List<String> queryResourceListByUserId(Integer userId) {
        List<Resource> resourceList = resourceMapper.queryResourceByUserId(userId);
        List<String> menus = new ArrayList<String>();
        // 组装子父级目录
        for (Resource resource : resourceList) {
            menus.add(resource.getResourceKey());
        }
        return menus;
    }

    @Override
    @Cacheable(value = "resources", key = "'parent_id_'.concat(#parentId)")
    public List<Resource> queryResourceByParentId(Integer parentId) {
        List<Resource> resourceList = resourceMapper.queryResourceTreeProc(parentId);
        List<Resource> menus = new ArrayList<Resource>();
        Map<Integer, Resource> resourceMap = new HashMap<Integer, Resource>();
        // 组装子父级目录
        for (Resource resource : resourceList) {
            resourceMap.put(resource.getId(), resource);
        }
        for (Resource resource : resourceList) {
            Integer treePId = resource.getParentId();
            Resource pTreev = resourceMap.get(treePId);
            if (null != pTreev && !resource.equals(pTreev)) {
                List<Resource> nodes = pTreev.getChildren();
                if (null == nodes) {
                    nodes = new ArrayList<Resource>();
                    pTreev.setChildren(nodes);
                }
                nodes.add(resource);
            } else {
                menus.add(resource);
            }
        }
        return menus;
    }

    /**
     * queryResourceTree
     * 
     * @Title: queryResourceTree
     * @Description: 查询所有的权限树List
     * @param parentId
     * @return List<ZTree>
     */
    @Override
    public List<ZTree> queryResourceTree(Integer parentId, boolean assembleChildren) {
        List<ZTree> treeList = new ArrayList<ZTree>();
        try {
            List<Resource> perList = resourceMapper.queryResourceTreeProc(parentId);
            for (Resource per : perList) {
                ZTree tree = new ZTree();
                tree.setId(String.valueOf(per.getId()));
                tree.setpId(String.valueOf(per.getParentId()));
                tree.setName(per.getResourceName());
                tree.setOpen(true);
                tree.setExtKey(per.getResourceKey());
                tree.setExtLevel(String.valueOf(per.getResourceLevel()));
                tree.setExtType(per.getResourceType());
                tree.setExtUrl(per.getResourceUrl());
                tree.setExtIcon(per.getResourceIcon());
                tree.setDescription(per.getDescription());
                tree.setIsParent(per.getIsLeaf() == 0 ? true : false);
                treeList.add(tree);
            }
            // 是否组装子节点
            if (assembleChildren) {
                // 将children组装进去
                List<ZTree> menus = new ArrayList<ZTree>();
                Map<String, ZTree> zTreeMap = new HashMap<String, ZTree>();
                // 组装子父级目录
                for (ZTree tree : treeList) {
                    zTreeMap.put(tree.getId(), tree);
                }
                for (ZTree tree : treeList) {
                    String treePId = tree.getpId();
                    ZTree pTreev = zTreeMap.get(treePId);
                    if (null != pTreev && !tree.equals(pTreev)) {
                        List<ZTree> nodes = pTreev.getChildren();
                        if (null == nodes) {
                            nodes = new ArrayList<ZTree>();
                            pTreev.setChildren(nodes);
                        }
                        nodes.add(tree);
                    } else {
                        menus.add(tree);
                    }
                }
                treeList = menus;
            }
        } catch (Exception e) {
            logger.error("权限树查询异常:", e);
            throw new BusinessException("权限树查询异常");
        }
        return treeList;
    }
}
