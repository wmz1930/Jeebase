package com.jeebase.system.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.common.dto.LogInfo;
import com.jeebase.system.common.dto.QueryLog;
import com.jeebase.system.common.entity.Log;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
public interface ILogService extends IService<Log> {

    /**
     * 查询日志列表
     * @param page
     * @param log
     * @return
     */
    Page<LogInfo> selectLogList(Page<LogInfo> page, QueryLog log);

}
