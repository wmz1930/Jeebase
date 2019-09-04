/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.jeebase.common.base.component;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.jeebase.common.base.DataPermissionCondition;
import com.jeebase.common.util.DataPermissionUtil;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 分页拦截器
 *
 * @author hubin
 * @since 2016-01-23
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class DataPermissionInterceptor extends AbstractSqlParserHandler implements Interceptor {

    /**
     * COUNT SQL 解析
     */
    private ISqlParser sqlParser;
    /**
     * 溢出总页数，设置第一页
     */
    private boolean overflow = false;
    /**
     * 方言类型
     */
    private String dialectType;
    /**
     * 方言实现类
     */
    private String dialectClazz;

    @Value("${system.noDataFilterRole}")
    private String noDataFilterRole;

    /**
     * Physical Page Interceptor for all the queries with parameter {@link RowBounds}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // SQL 解析
        this.sqlParser(metaObject);

        // 针对定义了rowBounds，做为mapper接口方法的参数
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object paramObj = boundSql.getParameterObject();

        // 判断参数里是否有DataPermission对象
        DataPermissionCondition dataPermissionCondition = null;
        if (paramObj instanceof DataPermissionCondition) {
            dataPermissionCondition = (DataPermissionCondition) paramObj;
        } else if (paramObj instanceof Map) {
            for (Object arg : ((Map) paramObj).values()) {
                if (arg instanceof DataPermissionCondition) {
                    dataPermissionCondition = (DataPermissionCondition) arg;
                    break;
                }
            }
        }

        /*
         * 不需要数据权限直接进行下一步
         */
        if (null == dataPermissionCondition || SecurityUtils.getSubject().hasRole(noDataFilterRole)) {
            return invocation.proceed();
        }

        // 如果没有配置orgId的别名，则默认给一个
        if (StringUtils.isEmpty(dataPermissionCondition.getOrgIdAlias())) {
            dataPermissionCondition.setOrgIdAlias("organiaztion_id");
        }

        String orgIdAlias = dataPermissionCondition.getOrgIdAlias();

        List<String> orgIdList = dataPermissionCondition.getOrgIdList();

        String userIdAlias = dataPermissionCondition.getUserIdAlias();

        String userId = dataPermissionCondition.getUserId();

        boolean ownQuery = dataPermissionCondition.isOwnQuery();

        // 如果没有配置userId的别名，则默认给一个
        if (ownQuery && StringUtils.isEmpty(dataPermissionCondition.getUserIdAlias())) {
            dataPermissionCondition.setUserIdAlias("user_id");
        }

        // 先判断是什么操作，只支持SELECT，DELETE，UPDATE
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        String originalSql = boundSql.getSql();
        if (SqlCommandType.SELECT.equals(sqlCommandType) || SqlCommandType.DELETE.equals(sqlCommandType)
                || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            String newSql = DataPermissionUtil.convertDataPermission(originalSql, orgIdAlias, orgIdList, ownQuery, userIdAlias, userId);
            metaObject.setValue("delegate.boundSql.sql", newSql);
        }else{
            return invocation.proceed();
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties prop) {
        String dialectType = prop.getProperty("dialectType");
        String dialectClazz = prop.getProperty("dialectClazz");
        if (StringUtils.isNotEmpty(dialectType)) {
            this.dialectType = dialectType;
        }
        if (StringUtils.isNotEmpty(dialectClazz)) {
            this.dialectClazz = dialectClazz;
        }
    }


    public DataPermissionInterceptor setSqlParser(ISqlParser sqlParser) {
        this.sqlParser = sqlParser;
        return this;
    }

    public DataPermissionInterceptor setOverflow(boolean overflow) {
        this.overflow = overflow;
        return this;
    }

    public DataPermissionInterceptor setDialectType(String dialectType) {
        this.dialectType = dialectType;
        return this;
    }

    public DataPermissionInterceptor setDialectClazz(String dialectClazz) {
        this.dialectClazz = dialectClazz;
        return this;
    }
}
