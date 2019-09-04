package com.jeebase.system.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.Constant;
import com.jeebase.common.base.Result;
import com.jeebase.system.security.dto.CreateOrganization;
import com.jeebase.system.security.dto.UpdateOrganization;
import com.jeebase.system.security.entity.Organization;
import com.jeebase.system.security.service.IOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 组织表 前端控制器
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
@RestController
@RequestMapping("/organization")
@Api(value = "OrganizationController|组织机构相关的前端控制器")
public class OrganizationController {

    @Autowired
    private IOrganizationService organizationService;

    /**
     * 查询组织树
     * 
     * @param parentId
     * @return
     */
    @GetMapping(value = "/tree")
    @RequiresAuthentication
    @ApiOperation(value = "查询组织机构树", notes = "树状展示组织机构信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public Result<List<Organization>> queryOrganizationTree(Integer parentId) {
        List<Organization> treeList = organizationService.queryOrganizationByPanentId(parentId);
        return new Result<List<Organization>>().success().put(treeList);
    }

    /**
     * 添加组织
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加组织机构")
    @AroundLog(name = "添加组织机构")
    public Result<Organization> create(@RequestBody CreateOrganization org) {
        Organization orgEntity = new Organization();
        BeanCopier.create(CreateOrganization.class, Organization.class, false).copy(org, orgEntity, null);
        List<String> areas = org.getAreas();
        if (!CollectionUtils.isEmpty(areas)) {
            orgEntity.setProvince(areas.get(Constant.Address.PROVINCE));
            orgEntity.setCity(areas.get(Constant.Address.CITY));
            orgEntity.setArea(areas.get(Constant.Address.AREA));
        }
        boolean result = organizationService.createOrganization(orgEntity);
        if (result) {
            return new Result<Organization>().success("添加成功").put(orgEntity);
        } else {
            return new Result<Organization>().error("添加失败，请重试");
        }
    }

    /**
     * 修改组织
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新组织机构")
    @AroundLog(name = "更新组织机构")
    public Result<Organization> update(@RequestBody UpdateOrganization org) {
        Organization orgEntity = new Organization();
        BeanCopier.create(UpdateOrganization.class, Organization.class, false).copy(org, orgEntity, null);
        List<String> areas = org.getAreas();
        if (!CollectionUtils.isEmpty(areas)) {
            orgEntity.setProvince(areas.get(Constant.Address.PROVINCE));
            orgEntity.setCity(areas.get(Constant.Address.CITY));
            orgEntity.setArea(areas.get(Constant.Address.AREA));
        }
        boolean result = organizationService.updateOrganization(orgEntity);
        if (result) {
            return new Result<Organization>().success("修改成功").put(orgEntity);
        } else {
            return new Result<Organization>().error("修改失败");
        }
    }

    /**
     * 删除组织
     */
    @RequiresRoles("SYSADMIN")
    @PostMapping("/delete/{orgId}")
    @ApiOperation(value = "删除组织机构")
    @AroundLog(name = "删除组织机构")
    @ApiImplicitParam(paramType = "path", name = "orgId", value = "组织机构ID", required = true, dataType = "Integer")
    public Result<?> delete(@PathVariable("orgId") Integer orgId) {
        boolean result = organizationService.deleteOrganization(orgId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    @PostMapping(value = "/name/check")
    @RequiresAuthentication
    @ApiOperation(value = "校验组织名称是否存在", notes = "校验组织名称是否存在")
    public Result<Boolean> checkRoleName(CreateOrganization organization) {
        String organizationName = organization.getOrganizationName();
        QueryWrapper<Organization> organizationQueryWrapper = new QueryWrapper<>();
        organizationQueryWrapper.eq("organization_name", organizationName);
        if(null != organization.getId()) {
            organizationQueryWrapper.ne("id", organization.getId());
        }
        int count = organizationService.count(organizationQueryWrapper);
        if (count == 0){
            return new Result<Boolean>().success().put(true);
        } else{
            return new Result<Boolean>().success().put(false);
        }
    }

    @PostMapping(value = "/key/check")
    @RequiresAuthentication
    @ApiOperation(value = "校验组织标识是否存在", notes = "校验组织标识是否存在")
    public Result<Boolean> checkRoleKey(CreateOrganization organization) {
        String organizationKey = organization.getOrganizationKey();
        QueryWrapper<Organization> organizationQueryWrapper = new QueryWrapper<>();
        organizationQueryWrapper.eq("organization_key", organizationKey);
        if(null != organization.getId()) {
            organizationQueryWrapper.ne("id", organization.getId());
        }
        int count = organizationService.count(organizationQueryWrapper);
        if (count == 0){
            return new Result<Boolean>().success().put(true);
        } else{
            return new Result<Boolean>().success().put(false);
        }
    }
}
