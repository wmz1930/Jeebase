package com.jeebase.system.common.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.PageResult;
import com.jeebase.system.common.dto.LogInfo;
import com.jeebase.system.common.dto.QueryLog;
import com.jeebase.system.common.service.ILogService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jeebase
 * @since 2018-10-24
 */
@RestController
@RequestMapping("/common/log")
public class LogController {
    
    @Autowired
    ILogService logService;

    /**
     * 查询所有用户
     */
    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询操作日志列表")
    public PageResult<LogInfo> list(QueryLog log, Page<LogInfo> page) {
        Page<LogInfo> pageLog = logService.selectLogList(page, log);
        PageResult<LogInfo> pageResult = new PageResult<LogInfo>(pageLog.getTotal(), pageLog.getRecords());
        return pageResult;
    }
}
