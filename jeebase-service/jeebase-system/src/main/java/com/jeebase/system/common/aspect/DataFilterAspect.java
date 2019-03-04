package com.jeebase.system.common.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.jeebase.common.annotation.auth.DataFilter;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.DataPermissionCondition;
import com.jeebase.system.security.entity.DataPermission;
import com.jeebase.system.security.service.IDataPermissionService;
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

    @Pointcut("@annotation(com.jeebase.common.annotation.auth.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) throws Throwable {

        Object[] params = point.getArgs();

        if(!ArrayUtils.isEmpty(params))
        {
            for (Object param: params)
            {
                if(param != null && param instanceof DataPermissionCondition){
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
                    DataPermissionCondition dataPermissionCondition = (DataPermissionCondition) param;
                    MethodSignature signature = (MethodSignature) point.getSignature();
                    DataFilter dataFilter = signature.getMethod().getAnnotation(DataFilter.class);
                    String orgIdAlias = dataFilter.orgIdAlias();
                    String userIdAlias = dataFilter.userIdAlias();
                    boolean ownQuery = dataFilter.ownQuery();
                    dataPermissionCondition.setOrgIdAlias(orgIdAlias);
                    dataPermissionCondition.setUserId(userIdAlias);
                    dataPermissionCondition.setOwnQuery(ownQuery);

                    //添加拥有的数据权限列表
                    QueryWrapper<DataPermission> wrapper = new QueryWrapper<>();
                    wrapper.eq("user_id", userId);
                    List<DataPermission> dataPermissionList = dataPermissionService.list(wrapper);
                    if (!CollectionUtils.isEmpty(dataPermissionList))
                    {
                        List<String> orgIdList = new ArrayList<>();
                        for (DataPermission dp : dataPermissionList)
                        {
                            orgIdList.add(String.valueOf(dp.getOrganizationId()));
                        }

                        dataPermissionCondition.setOrgIdList(orgIdList);
                    }
                    break;
                }
            }
        }
        else
        {
            throw new BusinessException("要实现数据权限，必须添加DataPermissionCondition参数");
        }

    }
}
