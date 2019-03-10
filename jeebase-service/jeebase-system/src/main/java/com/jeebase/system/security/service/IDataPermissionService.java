package com.jeebase.system.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.security.dto.UpdateDataPermission;
import com.jeebase.system.security.entity.DataPermission;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeebase
 * @since 2019-02-27
 */
public interface IDataPermissionService extends IService<DataPermission> {

    /**
     * 修改用户数据权限
     * @param updateDataPermission
     * @return
     */
    boolean updateUserDataPermission(UpdateDataPermission updateDataPermission);
}
