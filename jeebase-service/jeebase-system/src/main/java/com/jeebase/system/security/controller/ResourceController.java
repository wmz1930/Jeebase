package com.jeebase.system.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.annotation.auth.CurrentUser;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.Result;
import com.jeebase.system.security.dto.CreateResource;
import com.jeebase.system.security.dto.UpdateResource;
import com.jeebase.system.security.entity.Resource;
import com.jeebase.system.security.entity.User;
import com.jeebase.system.security.service.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
@RestController
@RequestMapping("/resource")
@Api(value = "ResourceController|权限资源相关的前端控制器")
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @GetMapping(value = "/tree")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询权限资源树", notes = "树状展示权限资源信息")
    @ApiImplicitParam(paramType = "query", name = "parentId", value = "父级ID", required = false, dataType = "Integer")
    public Result<List<Resource>> queryResourceTree(Integer parentId) {
        List<Resource> treeList = resourceService.queryResourceByParentId(parentId);
        return new Result<List<Resource>>().success().put(treeList);
    }

    /**
     * 添加资源
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加权限资源")
    @AroundLog(name = "添加权限资源")
    public Result<Resource> createResource(@RequestBody CreateResource resource) {
        Resource resEntity = new Resource();
        BeanCopier.create(CreateResource.class, Resource.class, false).copy(resource, resEntity, null);
        boolean result = resourceService.createResource(resEntity);
        if (result) {
            return new Result<Resource>().success("添加成功").put(resEntity);
        } else {
            return new Result<Resource>().error("添加失败，请重试");
        }
    }

    /**
     * 修改资源
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新权限资源")
    @AroundLog(name = "更新权限资源")
    public Result<Resource> updateResource(@RequestBody UpdateResource resource) {
        Resource resEntity = new Resource();
        BeanCopier.create(UpdateResource.class, Resource.class, false).copy(resource, resEntity, null);
        boolean result = resourceService.updateResource(resEntity);
        if (result) {
            return new Result<Resource>().success("修改成功").put(resEntity);
        } else {
            return new Result<Resource>().error("修改失败");
        }
    }

    /**
     * 删除资源
     */
    @RequiresRoles("SYSADMIN")
    @PostMapping("/delete/{resId}")
    @ApiOperation(value = "删除权限资源")
    @AroundLog(name = "删除权限资源")
    @ApiImplicitParam(paramType = "path", name = "resId", value = "权限资源ID", required = true, dataType = "Integer")
    public Result<?> deleteResource(@PathVariable("resId") Integer resId) {
        boolean result = resourceService.deleteResource(resId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    @PostMapping(value = "/key/check")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "校验ResourceKey是否存在", notes = "校验ResourceKey是否存在")
    public Result<Boolean> checkResourceKey(CreateResource resource) {
        String resourceKey = resource.getResourceKey();
        QueryWrapper<Resource> resourceQueryWrapper = new QueryWrapper<>();
        resourceQueryWrapper.eq("resource_key", resourceKey);
        if(null != resource.getId()) {
            resourceQueryWrapper.ne("id", resource.getId());
        }
        int count = resourceService.count(resourceQueryWrapper);
        if (count == 0){
            return new Result<Boolean>().success().put(true);
        } else{
            return new Result<Boolean>().success().put(false);
        }
    }

    /**
     * 获取登录后的菜单权限
     */
    @GetMapping("/menu")
    @RequiresAuthentication
    @ApiOperation(value = "登陆后获取个人权限资源")
    public Result<List<Resource>> navMenu(@ApiIgnore @CurrentUser User currentUser) {
        Integer userId = currentUser.getId();
        List<Resource> resourceList = resourceService.queryResourceByUserId(userId);
        return new Result<List<Resource>>().success().put(resourceList);
    }
}
