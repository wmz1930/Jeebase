package com.jeebase.system.common.component;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * @ClassName: MyMetaObjectHandler
 * @Description: 自定义填充公共字段
 * @author jeebase-WANGLEI
 * @date 2018年5月19日 上午10:32:29
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object creator = getFieldValByName("creator", metaObject);
        if (null == creator) {
            String principal = (String) SecurityUtils.getSubject().getPrincipal();
            if (null != principal) {
                JSONObject userObj = JSON.parseObject(principal);
                setFieldValByName("creator", userObj.getInteger("id"), metaObject);
            }
        }
        Object createTime = getFieldValByName("createTime", metaObject);
        if (null == createTime) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object operator = getFieldValByName("operator", metaObject);
        if (null == operator) {
            String principal = (String) SecurityUtils.getSubject().getPrincipal();
            if (null != principal) {
                JSONObject userObj = JSON.parseObject(principal);
                setFieldValByName("operator", userObj.getInteger("id"), metaObject);
            }
        }
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
