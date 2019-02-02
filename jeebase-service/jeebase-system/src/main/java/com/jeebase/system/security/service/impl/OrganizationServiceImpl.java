package com.jeebase.system.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.domain.ZTree;
import com.jeebase.system.security.entity.Organization;
import com.jeebase.system.security.mapper.OrganizationMapper;
import com.jeebase.system.security.service.IOrganizationService;

/**
 * @author jeebase
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization>
        implements IOrganizationService {

    /**
     * 异常日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    OrganizationMapper organizationMapper;

    /**
     * queryOrgTree
     * 
     * @Title: queryOrgTree
     * @Description: 查询所有的组织结构树
     * @param parentId
     * @return List<ZTree>
     */
    @Override
    public List<ZTree> queryOrgTree(Integer parentId) {
        List<ZTree> treeList = new ArrayList<ZTree>();
        try {
            if (null == parentId) {
                parentId = 0;
            }
            List<Organization> orgList = organizationMapper.queryOrganizationTreeProc(parentId);
            for (Organization org : orgList) {
                ZTree tree = new ZTree();
                tree.setId(String.valueOf(org.getId()));
                tree.setpId(String.valueOf(org.getParentId()));
                tree.setExtType(org.getOrganizationType());
                tree.setName(org.getOrganizationName());
                tree.setExtKey(org.getOrganizationKey());
                tree.setExtIcon(org.getOrganizationIcon());
                tree.setExtLevel(String.valueOf(org.getOrganizationLevel()));
                tree.setDescription(org.getDescription());
                tree.setCreateTime(org.getCreateTime());
                tree.setModifyTime(org.getUpdateTime());
                tree.setIsParent(org.getIsLeaf() == 0 ? true : false);
                treeList.add(tree);
            }
        } catch (Exception e) {
            logger.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return treeList;
    }

    @Override
    public List<Organization> queryOrganizationByPanentId(Integer parentId) {
        if (null == parentId) {
            parentId = 0;
        }
        List<Organization> orgs = new ArrayList<Organization>();
        try {
            List<Organization> orgList = organizationMapper.queryOrganizationTreeProc(parentId);
            Map<Integer, Organization> organizationMap = new HashMap<Integer, Organization>();
            // 组装子父级目录
            for (Organization organization : orgList) {
                organizationMap.put(organization.getId(), organization);
            }
            for (Organization organization : orgList) {
                Integer treePId = organization.getParentId();
                Organization pTreev = organizationMap.get(treePId);
                if (null != pTreev && !organization.equals(pTreev)) {
                    List<Organization> nodes = pTreev.getChildren();
                    if (null == nodes) {
                        nodes = new ArrayList<Organization>();
                        pTreev.setChildren(nodes);
                    }
                    nodes.add(organization);
                } else {
                    orgs.add(organization);
                }
            }
        } catch (Exception e) {
            logger.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return orgs;
    }
}
