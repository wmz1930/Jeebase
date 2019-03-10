package com.jeebase.system.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.system.common.dto.LogInfo;
import com.jeebase.system.common.dto.QueryLog;
import com.jeebase.system.common.entity.Log;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
public interface LogMapper extends BaseMapper<Log> {
    /**
     * 分页查询操作日志
     * @param page
     * @param log
     * @return logInfo
     */
    Page<LogInfo> selectLogList(Page<LogInfo> page, @Param("log") QueryLog log);
}
