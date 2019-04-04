package com.jeebase.system.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.domain.ZTree;
import com.jeebase.system.security.entity.Organization;
import com.jeebase.system.security.mapper.OrganizationMapper;
import com.jeebase.system.security.service.IOrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * queryOrgList
     * 
     * @Title: queryOrgList
     * @Description: 查询所有的组织结构树
     * @param parentId
     * @return List<ZTree>
     */
    @Override
    public List<Organization> queryOrgList(Integer parentId) {
        List<Organization> orgList;
        try {
            if (null == parentId) {
                parentId = 0;
            }
            orgList = organizationMapper.queryOrganizationTreeProc(parentId);
        } catch (Exception e) {
            logger.error("查询组织树失败:", e);
            throw new BusinessException("查询组织树失败");
        }
        return orgList;
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
