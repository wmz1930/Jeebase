package com.jeebase.system.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.security.dto.UpdateDataPermission;
import com.jeebase.system.security.entity.DataPermission;
import com.jeebase.system.security.mapper.DataPermissionMapper;
import com.jeebase.system.security.service.IDataPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeebase
 * @since 2019-02-27
 */
@Service
public class DataPermissionServiceImpl extends ServiceImpl<DataPermissionMapper, DataPermission> implements IDataPermissionService {

    @Override
    public boolean updateUserDataPermission(UpdateDataPermission updateDataPermission){

        boolean result = false;
        Integer userId = updateDataPermission.getUserId();

        List<Integer> removeDataPermissions = updateDataPermission.getRemoveDataPermissions();
        if (!CollectionUtils.isEmpty(removeDataPermissions) && null != userId)
        {
            QueryWrapper<DataPermission> removeWrapper = new QueryWrapper<>();
            removeWrapper.eq("user_id", userId).in("organization_id", removeDataPermissions);
            result = this.remove(removeWrapper);
        }

        List<Integer> addDataPermissions = updateDataPermission.getAddDataPermissions();
        if (!CollectionUtils.isEmpty(addDataPermissions) && null != userId)
        {
            List<DataPermission> dataPermissionList = new ArrayList<>();
            for (Integer dataId: addDataPermissions)
            {
                DataPermission dataPermission = new DataPermission();
                dataPermission.setOrganizationId(dataId);
                dataPermission.setUserId(userId);
                dataPermissionList.add(dataPermission);
            }
            result = this.saveBatch(dataPermissionList);
        }
        return result;
    }

}
