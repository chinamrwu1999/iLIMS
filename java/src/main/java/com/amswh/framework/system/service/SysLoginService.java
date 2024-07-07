package com.amswh.framework.system.service;


import com.amswh.framework.commons.ServiceException;
import com.amswh.framework.commons.service.RedisCache;
import com.amswh.framework.security.TokenService;
import com.amswh.framework.system.model.LoginUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Resource
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService userService;
//
//    @Autowired
//    private ISysConfigService configService;
//
//    @Autowired
//    private SysUserMapper sysUserMapper;
//
//    @Autowired
//    private SysUserPwdLogMapper sysUserPwdLogMapper;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {

        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            // authenticationManager.
        }catch (Exception e)
        {
                System.out.println(username+" login failed:"+e.getMessage());
                throw new ServiceException("用户名密码不匹配");

        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();


        recordLoginInfo(loginUser.getU);

        LambdaQueryWrapper<SysUserPwdLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserPwdLog::getUserId,loginUser.getUserId()).orderByDesc(SysUserPwdLog::getId).last("limit 1");
        SysUserPwdLog pwdLog = sysUserPwdLogMapper.selectOne(queryWrapper);
        if(pwdLog!=null){
            loginUser.setInitPwd(false);
            loginUser.setLastUpdatePwdDate(pwdLog.getCreateTime());
        }
        // 生成token
        return tokenService.createToken(loginUser);
    }


    /**
     * 校验其他系统输入的账号密码
     * @param username
     * @param password
     * @return
     */
    public AuthUser loginAuth(String username, String password)
    {
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            return null;
        }
        AuthUser authUser =  sysUserMapper.queryByEmpNo(username);
        return authUser;
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    public String getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername(); // Assuming userId is stored in username field
        return userId;


    }

}

