package com.jeebase.system.common.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.common.dto.LogInfo;
import com.jeebase.system.common.dto.QueryLog;
import com.jeebase.system.common.entity.Log;
import com.jeebase.system.common.mapper.LogMapper;
import com.jeebase.system.common.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public Page<LogInfo> selectLogList(Page<LogInfo> page, QueryLog log) {
        Page<LogInfo> pageLogInfo = logMapper.selectLogList(page, log);
        return pageLogInfo;
    }
}
