package com.jeebase.system.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.annotation.auth.CurrentUser;
import com.jeebase.common.annotation.auth.NoAuthentication;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.Constant;
import com.jeebase.common.base.ResponseConstant;
import com.jeebase.common.base.Result;
import com.jeebase.common.base.component.JwtComponent;
import com.jeebase.common.captcha.GifCaptcha;
import com.jeebase.common.captcha.util.CaptchaUtil;
import com.jeebase.system.common.service.ISmsService;
import com.jeebase.system.security.dto.*;
import com.jeebase.system.security.entity.Resource;
import com.jeebase.system.security.entity.Role;
import com.jeebase.system.security.entity.User;
import com.jeebase.system.security.service.IResourceService;
import com.jeebase.system.security.service.IUserRoleService;
import com.jeebase.system.security.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: LoginController
 * @Description: 登录相关前端控制器
 * @author jeebase-WANGLEI
 * @date 2018年5月18日 下午4:08:26
 */
@RestController
@RequestMapping("/auth")
@Api(value = "LoginController|登录鉴权相关的前端控制器")
public class LoginController {
    
    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    ISmsService iSmsService;

    @Value("${system.smsTimes}")
    private int smsTimes;

    @Autowired
    private CacheChannel cacheChannel;

    @GetMapping("/vcode")
    @NoAuthentication
    @ApiOperation(value = "获取登录时的验证码")
    @ApiImplicitParam(paramType = "query", name = "codeKey", value = "验证码唯一标识", required = true, dataType = "String")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codeKey = request.getParameter("codeKey");
        if (codeKey != null && !codeKey.trim().isEmpty()) {
            response.setContentType("image/gif");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            GifCaptcha captcha = new GifCaptcha(130, 38, 5);
            ServletContext servletContext = request.getSession().getServletContext();
            servletContext.setAttribute("code_" + codeKey, captcha.text().toLowerCase());
            captcha.out(response.getOutputStream());
        }
    }

    @PostMapping("/login")
    @NoAuthentication
    @ApiOperation(value = "执行登录", notes = "返回token")
    public Result<String> login(@RequestBody LoginUser loginUser, HttpServletRequest request) throws Exception {
        String userAccount = loginUser.getUserAccount();
        String userPassword = loginUser.getUserPassword();
        String vcode = loginUser.getVcode();
        String verkey = loginUser.getVerkey();
        if (!CaptchaUtil.isVerified(verkey, vcode, request)) {
            return new Result<String>().error(ResponseConstant.INVALID_RE_VCODE);
        }
        if (StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(userPassword)) {
            return new Result<String>().error(ResponseConstant.PARAM_ERROR);
        }
        // 查询用户是否存在
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("user_account", userAccount).or().eq("user_mobile", userAccount).or().eq("user_email", userAccount);
        User user = userService.getOne(ew);
        if (StringUtils.isEmpty(user) || !BCrypt.checkpw(userPassword, user.getUserPassword())) {
            return new Result<String>().error(ResponseConstant.INVALID_USERNAME_PASSWORD);
        }
        String token = jwtComponent.sign(user.getUserAccount(), user.getUserPassword(), Constant.ExpTimeType.WEB);
        return new Result<String>().success().put(token);
    }

    /**
     * 获取登录后的用户信息
     */
    @GetMapping("/user/info")
    @RequiresAuthentication
    @ApiOperation(value = "登录后获取用户个人信息")
    public Result<UserInfo> userInfo(HttpServletRequest request, @ApiIgnore @CurrentUser User currentUser) {
        Integer userId = currentUser.getId();
        UserInfo userInfo = new UserInfo();
        BeanCopier.create(User.class, UserInfo.class, false).copy(currentUser, userInfo, null);
        List<Role> userRole = userRoleService.queryRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(userRole)) {
            List<String> roles = new ArrayList<String>();
            StringBuffer roleNames = new StringBuffer();
            for (Role role : userRole) {
                roles.add(role.getRoleKey());
                roleNames.append(role.getRoleName());
            }
            userInfo.setRoles(roles);
            userInfo.setRoleName(roleNames.toString());
        }
        List<Resource> resourceList = resourceService.queryResourceByUserId(userId);
        userInfo.setResources(resourceList);

        List<String> resourceStringList = resourceService.queryResourceListByUserId(userId);
        userInfo.setStringResources(resourceStringList);
        return new Result<UserInfo>().success().put(userInfo);
    }
    
    /**
     * 刷新token
     */
    @GetMapping("/token/refresh")
    @RequiresAuthentication
    @ApiOperation(value = "刷新token")
    public Result<String> refreshToken( @ApiIgnore @CurrentUser User currentUser) {
        Integer userId = currentUser.getId();
        User user = userService.getById(userId);
        String token = jwtComponent.sign(user.getUserAccount(), user.getUserPassword(), Constant.ExpTimeType.WEB);
        return new Result<String>().success().put(token);
    }

    /**
     * 发送短信验证码
     */
    @PostMapping("/sms/reg")
    @NoAuthentication
    @ApiOperation(value = "注册用户时，发送短信验证码")
    public Result<?> sendSmsReg(@Valid @RequestBody SmsDto smsDto) {
        String phoneNumber = smsDto.getUserMobile();
        QueryWrapper<User> ew = new QueryWrapper<>();
        ew.eq("user_account", phoneNumber).or().eq("user_nick_name", phoneNumber).or().eq("user_email", phoneNumber)
                .or().eq("user_mobile", phoneNumber);
        List<User> userList = userService.list(ew);
        if (!CollectionUtils.isEmpty(userList)) {
            throw new BusinessException("账号已经存在");
        }
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("验证码发送超过最大次数");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_times", 1);
        }
        String smsCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("注册短信：" + smsCode);
        iSmsService.sendVcodeSms(phoneNumber, smsCode);
        cacheChannel.set("smsCode", phoneNumber + "_sms_reg", smsCode);
        return new Result<>().success("验证码发送成功");
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    @NoAuthentication
    @ApiOperation(value = "注册用户")
    public Result<?> create(@Valid @RequestBody RegisterUser user) {
        String phoneNumber = user.getUserMobile();
        String userSmsCode = user.getSmsCode();
        CacheObject smsCodeCache = cacheChannel.get("smsCode", phoneNumber + "_sms_reg");
        String smsCode = (String) smsCodeCache.getValue();
        if (StringUtils.isEmpty(smsCode)) {
            return new Result<>().error("短信验证码已失效，请重新获取");
        }
        if (!smsCode.equals(userSmsCode)) {
            return new Result<>().error("短信验证码错误，请重新输入");
        }
        CreateUser createUser = new CreateUser();
        BeanCopier.create(RegisterUser.class, CreateUser.class, false).copy(user, createUser, null);
        createUser.setRoleId(8);
        createUser.setUserStatus("1");
        createUser.setUserAccount(phoneNumber);
        boolean result = userService.createUser(createUser);
        if (result) {
            return new Result<>().success("注册成功");
        } else {
            return new Result<>().error("添加失败，请重试");
        }
    }

    /**
     * 发送修改密码的短信验证码
     */
    @PostMapping("/sms/pwd")
    @NoAuthentication
    @ApiOperation(value = "未登录用户找回密码，发送修改密码的短信验证码")
    public Result<?> sendSmsPwd(@Valid @RequestBody SmsDto smsDto) {
        String phoneNumber = smsDto.getUserMobile();
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("USER_MOBILE", smsDto.getUserMobile());
        User user = userService.getOne(wrapper);
        if (null == user) {
            return new Result<>().error("该手机尚未注册");
        }
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("验证码发送超过最大次数");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_times", 1);
        }
        String smsCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("注册短信：" + smsCode);
        iSmsService.sendVcodeSms(phoneNumber, smsCode);
        cacheChannel.set("smsCode", phoneNumber + "_sms_pwd", smsCode);
        return new Result<>().success("验证码发送成功");
    }

    /**
     * 验证找回密码
     */
    @PostMapping("/pwd/code/check")
    @NoAuthentication
    @ApiOperation(value = "未登录用户找回密码，判断验证码是否正确")
    public Result<?> pwdCodeCheck(@Valid @RequestBody RegisterUser user) {
        String phoneNumber = user.getUserMobile();
        String userSmsCode = user.getSmsCode();
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("超过最大验证次数，请明天再试");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pwd_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pwd_times", 1);
        }
        CacheObject smsCodeCache = cacheChannel.get("smsCode", phoneNumber + "_sms_pwd");
        String smsCode = (String) smsCodeCache.getValue();
        if (StringUtils.isEmpty(smsCode)) {
            return new Result<>().error("短信验证码已失效，请重新获取");
        }
        if (!smsCode.equals(userSmsCode)) {
            return new Result<>().error("短信验证码错误，请重新输入");
        }
        return new Result<>().success("验证码");
    }

    /**
     * 更新密码
     */
    @PostMapping("/pwd/update")
    @NoAuthentication
    @ApiOperation(value = "未登录用户找回密码，更新密码")
    public Result<?> changePwd(@Valid @RequestBody PwdUser user) {
        String phoneNumber = user.getUserMobile();
        String userSmsCode = user.getSmsCode();
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_pwd_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("超过最大验证次数，请明天再试");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pwd_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pwd_times", 1);
        }
        CacheObject smsCodeCache = cacheChannel.get("smsCode", phoneNumber + "_sms_pwd");
        String smsCode = (String) smsCodeCache.getValue();
        if (StringUtils.isEmpty(smsCode)) {
            return new Result<>().error("短信验证码已失效，请重新获取");
        }
        if (!smsCode.equals(userSmsCode)) {
            return new Result<>().error("短信验证码错误，请重新输入");
        }
        User userEntity = new User();
        BeanCopier.create(PwdUser.class, User.class, false).copy(user, userEntity, null);
        String pwd = userEntity.getUserPassword();
        if (!StringUtils.isEmpty(pwd)) {
            String cryptPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            userEntity.setUserPassword(cryptPwd);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_mobile", userEntity.getUserMobile());
        boolean result = userService.update(userEntity, wrapper);
        if (result) {
            return new Result<>().success("密码修改成功,请登录");
        } else {
            return new Result<>().error("密码修改失败，请重试");
        }
    }

    /**
     * 发送修改密码的短信验证码
     */
    @PostMapping("/sms/personal/pwd")
    @RequiresAuthentication
    @ApiOperation(value = "登录用户，发送修改密码的短信验证码")
    public Result<?> sendPersonalSmsPwd(@ApiIgnore @CurrentUser User userc) {
        if (null == userc) {
            return new Result<>().error("当前用户未登陆");
        }
        String phoneNumber = userc.getUserMobile();
        if (StringUtils.isEmpty(phoneNumber)) {
            return new Result<>().error("未获取到当前登录用户手机号");
        }
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_pw_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("验证码发送超过最大次数");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pw_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pw_times", 1);
        }
        String smsCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("注册短信：" + smsCode);
        iSmsService.sendVcodeSms(phoneNumber, smsCode);
        cacheChannel.set("smsCode", phoneNumber + "_sms_pwd_pw", smsCode);
        return new Result<>().success("验证码发送成功");
    }

    /**
     * 验证找回密码
     */
    @GetMapping("/pwd/personal/code/check/{scode}")
    @RequiresAuthentication
    @ApiOperation(value = "登录用户，判断验证码是否正确")
    public Result<?> pwdPersonalCodeCheck(@PathVariable("scode") String scode, @ApiIgnore @CurrentUser User userc) {
        if (null == userc) {
            return new Result<>().error("当前用户未登陆");
        }
        String phoneNumber = userc.getUserMobile();
        if (StringUtils.isEmpty(phoneNumber)) {
            return new Result<>().error("未获取到当前登录用户手机号");
        }
        String userSmsCode = scode;
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_pw_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("超过最大验证次数，请明天再试");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pw_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pw_times", 1);
        }
        CacheObject smsCodeCache = cacheChannel.get("smsCode", phoneNumber + "_sms_pwd_pw");
        String smsCode = (String) smsCodeCache.getValue();
        if (StringUtils.isEmpty(smsCode)) {
            return new Result<>().error("短信验证码已失效，请重新获取");
        }
        if (!smsCode.equals(userSmsCode)) {
            return new Result<>().error("短信验证码错误，请重新输入");
        }
        return new Result<>().success("验证码");
    }

    /**
     * 更新密码
     */
    @PostMapping("/pwd/personal/update")
    @RequiresAuthentication
    @ApiOperation(value = "登录用户，更新密码")
    public Result<?> changePwdPersonal(@Valid @RequestBody UpdateUser user, @ApiIgnore @CurrentUser User userc) {
        String phoneNumber = userc.getUserMobile();
        String userSmsCode = user.getSmsCode();
        CacheObject smsTimesCache = cacheChannel.get("smsTimes", phoneNumber + "_sms_pwd_times");
        Integer vcodeNumbers = (Integer) smsTimesCache.getValue();
        if (null != vcodeNumbers) {
            int num = vcodeNumbers.intValue();
            if (num >= smsTimes) {
                return new Result<>().error("超过最大验证次数，请明天再试");
            }
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pwd_times", num + 1);
        } else {
            cacheChannel.set("smsTimes", phoneNumber + "_sms_pwd_times", 1);
        }
        CacheObject smsCodeCache = cacheChannel.get("smsCode", phoneNumber + "_sms_pwd_pw");
        String smsCode = (String) smsCodeCache.getValue();
        if (StringUtils.isEmpty(smsCode)) {
            return new Result<>().error("短信验证码已失效，请重新获取");
        }
        if (!smsCode.equals(userSmsCode)) {
            return new Result<>().error("短信验证码错误，请重新输入");
        }
        User userEntity = new User();
        userEntity.setUserMobile(userc.getUserMobile());
        String pwd = user.getUserPassword();
        if (!StringUtils.isEmpty(pwd)) {
            String cryptPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
            userEntity.setUserPassword(cryptPwd);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_mobile", userEntity.getUserMobile());
        boolean result = userService.update(userEntity, wrapper);
        if (result) {
            return new Result<>().success("密码修改成功,请登录");
        } else {
            return new Result<>().error("密码修改失败，请重试");
        }
    }

    @PostMapping("/logout")
    @NoAuthentication
    @ApiOperation(value = "退出登录")
    public Result<?> logOut(HttpServletRequest request) throws Exception {
        return new Result<>().success();
    }

    @RequestMapping("401")
    @NoAuthentication
    @ApiIgnore
    public Result<?> unauthorized() {
        return new Result<>().error(ResponseConstant.USER_NO_PERMITION);
    }

    @RequestMapping("timeout")
    @NoAuthentication
    @ApiIgnore
    public Result<?> timeOut() {
        return new Result<>().error(ResponseConstant.UNAUTHORIZED);
    }
}
