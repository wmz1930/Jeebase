package com.jeebase.system.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeebase.system.security.entity.Resource;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author jeebase
 * @since 2018-05-19
 */
public interface ResourceMapper extends BaseMapper<Resource> {

    /**
     * 查询用户权限资源
     * @param userId
     * @return
     */
    List<Resource> queryResourceByUserId(Integer userId);

    /**
     * queryResourceTreeProc
     * 
     * @Title: queryResourceTreeProc
     * @Description: 查询登陆用户的许可权限(使用存储过程递归查询所有权限树信息)
     * @param parentId
     * @return List<Resource>
     */
    List<Resource> queryResourceTreeProc(Integer parentId);
}
