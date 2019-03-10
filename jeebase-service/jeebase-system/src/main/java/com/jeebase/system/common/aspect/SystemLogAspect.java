package com.jeebase.system.common.aspect;

import com.alibaba.fastjson.JSON;
import com.jeebase.common.annotation.log.AfterLog;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.annotation.log.BeforeLog;
import com.jeebase.system.common.entity.Log;
import com.jeebase.system.common.service.ILogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 
 * @ClassName: SystemLogAspect
 * @Description:
 * @author jeebase-DN-JBKGJ72
 * @date 2016年4月27日 下午4:02:12
 *
 */
@Aspect
@Component
public class SystemLogAspect {

    /**
     * 本地异常日志记录对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    /**
     * 注入Service用于把日志保存数据库
     */
    @Autowired
    private ILogService logService;

    /**
     * Before切点
     */
    @Pointcut("@annotation(com.jeebase.common.annotation.log.BeforeLog)")
    public void beforeAspect() {
    }

    /**
     * After切点
     */
    @Pointcut("@annotation(com.jeebase.common.annotation.log.AfterLog)")
    public void afterAspect() {
    }

    /**
     * Around切点
     */
    @Pointcut("@annotation(com.jeebase.common.annotation.log.AroundLog)")
    public void aroundAspect() {
    }

    /**
     * 前置通知 记录用户的操作
     * 
     * @param joinPoint
     *            切点
     */
    @Before("beforeAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            // 处理入参
            Object[] args = joinPoint.getArgs();
            StringBuffer inParams = new StringBuffer();
            for (Object obj : args) {
                if (null != obj && !(obj instanceof ServletRequest) && !(obj instanceof ServletResponse)) {
                    String objJson = JSON.toJSONString(obj);
                    inParams.append(objJson);
                }
            }
            Method method = getMethod(joinPoint);
            String operationName = getBeforeLogName(method);
            addSysLog(joinPoint, String.valueOf(inParams), "BeforeLog", operationName);
        } catch (Exception e) {
            logger.error("doBefore日志记录异常,异常信息:{}", e.getMessage());
        }
    }

    /**
     * 后置通知 记录用户的操作
     * 
     * @param joinPoint
     *            切点
     */
    @AfterReturning(value = "afterAspect()", returning = "returnObj")
    public void doAfter(JoinPoint joinPoint, Object returnObj) {
        try {
            // 处理出参
            String outParams = JSON.toJSONString(returnObj);
            Method method = getMethod(joinPoint);
            String operationName = getAfterLogName(method);
            addSysLog(joinPoint, "AfterLog", outParams, operationName);
        } catch (Exception e) {
            logger.error("doAfter日志记录异常,异常信息:{}", e.getMessage());
        }
    }

    /**
     * 前后通知 用于拦截记录用户的操作记录
     * 
     * @param joinPoint
     *            切点
     * @throws Throwable
     */
    @Around("aroundAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 出参
        Object value = null;
        // 拦截的方法是否执行
        boolean execute = false;
        // 入参
        Object[] args = joinPoint.getArgs();
        try {
            // 处理入参
            StringBuffer inParams = new StringBuffer();
            for (Object obj : args) {
                if (null != obj && !(obj instanceof ServletRequest) && !(obj instanceof ServletResponse)) {
                    String objJson = JSON.toJSONString(obj);
                    inParams.append(objJson);
                }
            }
            execute = true;
            // 执行目标方法
            value = joinPoint.proceed(args);
            // 处理出参
            String outParams = JSON.toJSONString(value);
            Method method = getMethod(joinPoint);
            String operationName = getAroundLogName(method);
            // 记录日志
            addSysLog(joinPoint, String.valueOf(inParams), String.valueOf(outParams), operationName);
        } catch (Exception e) {
            logger.error("around日志记录异常,异常信息:{}", e.getMessage());
            // 如果未执行则继续执行，日志异常不影响操作流程继续
            if (!execute) {
                value = joinPoint.proceed(args);
            }
            throw e;
        }
        return value;
    }

    /**
     * 日志入库 addSysLog(这里用一句话描述这个方法的作用)
     *
     * @Title: addSysLog @Description: @param @param joinPoint @param @param
     *         inParams @param @param outParams @param @param operationName
     *         设定文件 @return void 返回类型 @throws
     */
    public void addSysLog(JoinPoint joinPoint, String inParams, String outParams, String operationName) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            String ip = request.getRemoteAddr();
            Log sysLog = new Log();
            sysLog.setMethodName(joinPoint.getSignature().getName());
            sysLog.setInParams(String.valueOf(inParams));
            sysLog.setOutParams(String.valueOf(outParams));
            sysLog.setOperationIp(ip);
            sysLog.setOperationName(operationName);
            logService.save(sysLog);
        } catch (Exception e) {
            logger.error("addSysLog日志记录异常,异常信息:{}", e.getMessage());
            throw e;
        }
    }

    /**
     * 获取注解中对方法的描述信息
     * 
     * @param joinPoint
     *            切点
     * @return 方法描述
     * @throws Exception
     */
    public Method getMethod(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Method methodReturn = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    methodReturn = method;
                    break;
                }
            }
        }
        return methodReturn;
    }

    /**
     * 
     * getBeforeLogName(获取before名称)
     *
     * @Title: getBeforeLogName @Description: @param @param
     *         method @param @return 设定文件 @return String 返回类型 @throws
     */
    public String getBeforeLogName(Method method) {
        String name = method.getAnnotation(BeforeLog.class).name();
        return name;
    }

    /**
     * 
     * getAfterLogName(获取after名称)
     *
     * @Title: getAfterLogName @Description: @param @param method @param @return
     *         设定文件 @return String 返回类型 @throws
     */
    public String getAfterLogName(Method method) {
        String name = method.getAnnotation(AfterLog.class).name();
        return name;
    }

    /**
     * 
     * getAroundLogName(获取around名称)
     *
     * @Title: getAroundLogName @Description: @param @param
     *         method @param @return 设定文件 @return String 返回类型 @throws
     */
    public String getAroundLogName(Method method) {
        String name = method.getAnnotation(AroundLog.class).name();
        return name;
    }
}
