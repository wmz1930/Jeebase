package com.jeebase.system.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeebase.common.annotation.auth.DataFilter;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.DataPermissionCondition;
import com.jeebase.common.base.DataPermissionPage;
import com.jeebase.system.security.entity.DataPermission;
import com.jeebase.system.security.entity.Organization;
import com.jeebase.system.security.service.IDataPermissionService;
import com.jeebase.system.security.service.IOrganizationService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据过滤，切面处理类
 * @author WANGLEI
 */
@Aspect
@Component
public class DataFilterAspect {

    @Autowired
    IDataPermissionService dataPermissionService;

    @Autowired
    private IOrganizationService organizationService;

    @Pointcut("@annotation(com.jeebase.common.annotation.auth.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {
        Object[] params = point.getArgs();
        if(!ArrayUtils.isEmpty(params))
        {
            try
            {
                for (Object param: params)
                {
                    if(param != null
                            && (param instanceof DataPermissionCondition || param instanceof DataPermissionPage)){
                        String principal = (String) SecurityUtils.getSubject().getPrincipal();
                        String userId = null;

                        if (null != principal) {
                            JSONObject userObj = JSON.parseObject(principal);
                            userId = userObj.getString("id");
                        }
                        if(StringUtils.isEmpty(userId))
                        {
                            throw new BusinessException("实现数据权限，获取登录用户失败");
                        }

                        //给数据权限参数赋值
                        MethodSignature signature = (MethodSignature) point.getSignature();
                        DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
                        String orgIdAlias = dataFilter.orgIdAlias();
                        String userIdAlias = dataFilter.userIdAlias();
                        boolean ownQuery = dataFilter.ownQuery();

                        //添加拥有的数据权限列表
                        QueryWrapper<DataPermission> wrapper = new QueryWrapper<>();
                        wrapper.eq("user_id", userId);
                        List<DataPermission> dataPermissionList = dataPermissionService.list(wrapper);
                        List<String> orgIdList = null;
                        if (!CollectionUtils.isEmpty(dataPermissionList))
                        {
                            orgIdList = new ArrayList<>();
                            for (DataPermission dp : dataPermissionList)
                            {
                                List<Organization> childrenList = organizationService.queryOrgList(dp.getOrganizationId());
                                if (!CollectionUtils.isEmpty(childrenList))
                                {
                                    for (Organization childOrg : childrenList)
                                    {
                                        orgIdList.add(String.valueOf(childOrg.getId()));
                                    }
                                }
                                else
                                {
                                    orgIdList.add(String.valueOf(dp.getOrganizationId()));
                                }
                            }
                        }

                        if(param instanceof DataPermissionCondition)
                        {
                            DataPermissionCondition dataPermissionCondition = (DataPermissionCondition) param;
                            dataPermissionCondition.setOrgIdAlias(orgIdAlias);
                            dataPermissionCondition.setUserIdAlias(userIdAlias);
                            dataPermissionCondition.setUserId(userIdAlias);
                            dataPermissionCondition.setOwnQuery(ownQuery);
                            dataPermissionCondition.setOrgIdList(orgIdList);
                        }
                        else if(param instanceof DataPermissionPage)
                        {
                            DataPermissionPage dataPermissionPage = (DataPermissionPage) param;
                            dataPermissionPage.setOrgIdAlias(orgIdAlias);
                            dataPermissionPage.setUserIdAlias(userIdAlias);
                            dataPermissionPage.setUserId(userId);
                            dataPermissionPage.setOwnQuery(ownQuery);
                            dataPermissionPage.setOrgIdList(orgIdList);
                        }
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                throw new BusinessException("解析数据权限参数时发生异常：" + e);
            }
        }
        else
        {
            throw new BusinessException("要实现数据权限，必须添加DataPermissionCondition或者DataPermissionPage参数");
        }

    }
}
