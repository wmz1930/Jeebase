package com.jeebase.system.config;

import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.Constant;
import com.jeebase.common.base.ResponseConstant;
import com.jeebase.common.base.Result;
import com.jeebase.common.exception.ParamJsonException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;

/**
 * @ClassName: AllControllerAdvice
 * @Description: Controller统一异常处理
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午5:13:32
 */
@ControllerAdvice
public class BootControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(BootControllerAdvice.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<?> errorHandler(Exception e) {
        logger.error("接口出现严重异常：", e);
        return new Result<String>().error(ResponseConstant.ERROR);
    }

    /**
     * 捕捉UnauthorizedException
     * 
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Result<?> handle401() {
        return new Result<String>().error(ResponseConstant.UNAUTHORIZED);
    }

    /**
     * 捕捉shiro的异常
     * 
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public Result<?> handle401(ShiroException e) {
        return new Result<String>().error(ResponseConstant.USER_NO_PERMITION);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TemplateInputException.class)
    @ResponseBody
    public Result<?> handle401(TemplateInputException e) {
        return new Result<String>().error(ResponseConstant.USER_NO_PERMITION);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = ParamJsonException.class)
    @ResponseBody
    public Result<?> handleParam(Exception e) {
        if (e instanceof ParamJsonException) {
            logger.error("参数错误：" + e.getMessage());
            return new Result<String>().error(ResponseConstant.PARAM_ERROR);
        }
        return new Result<String>().error();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Result<?> handleBuiness(BusinessException e) {
        logger.error("业务逻辑错误：" + e.getExceptionMsg());
        return new Result<String>().error(e.getExceptionMsg());
    }

    /**
     * 处理实体字段校验不通过异常
     * 
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<String> validationError(MethodArgumentNotValidException e) {
        logger.info("参数校验不通过 : " + e.getMessage());
        BindingResult result = e.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        int i = Constant.Number.ZERO;
        for (FieldError error : fieldErrors) {
            i++;
            builder.append(i).append(") ").append(error.getDefaultMessage()).append("\n ");
        }
        return new Result<String>().error(builder.toString());
    }
}