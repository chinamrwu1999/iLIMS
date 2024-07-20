package com.amswh.iLIMS.framework.security.service;

import com.amswh.iLIMS.framework.commons.service.RedisCache;
import com.amswh.iLIMS.framework.security.model.LoginUser;
import com.amswh.iLIMS.framework.system.model.Constants;
import com.amswh.iLIMS.framework.utils.IdUtils;
import com.amswh.iLIMS.framework.utils.StringUtils;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.RsaKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

@Service
public class JoseJWTService {

    private final String header="token";
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    private static final int EXPIRE_TIME=600;
    @Resource
    SysKeyService keyService;

    @Autowired
    private RedisCache redisCache;


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = getToken(request);

        if (StringUtils.isNotEmpty(token))
        {
            try
            {
                JwtClaims claims = parseToken(token);
                String cacheId = claims.getIssuer();
                System.out.println("cacheId:"+cacheId);
                LoginUser user = redisCache.getCacheObject(cacheId);
                System.out.println("fetched cached user:"+user.getUsername());
                for(String str:user.getPermissions()){
                    System.out.println("perm>>>"+str);
                }
                System.out.println("user roles are:");
                for(String str:user.getRoles()){
                    System.out.println("role:"+str);
                }
                return user;
            }catch (Exception e){
                 e.printStackTrace();
            }
        }
        return null;
    }
    /**
     *  JWT token 作为web上浏览器与服务器交互的信使凭证，里面存有
     *  1） 如果有redis 做缓存，则 token上带有缓存的key，用户根据key从缓存读取用户信息
     *  2） 如果没有缓存，token上必须带有用户的身份Id，服务端收到返回的token，解析身份Id，从而获取用户身份信息
     * @param loginUser
     * @return
     */
    public String createToken(LoginUser loginUser){
        try {
            String cachedId=IdUtils.fastUUID(); // 作为存到Redis的key
            loginUser.setCachedId(cachedId);

            JwtClaims claims = new JwtClaims();
            claims.setIssuer(cachedId);
            claims.setAudience("love@china"); //
            claims.setExpirationTimeMinutesInTheFuture(60);
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            claims.setNotBeforeMinutesInThePast(1);
            claims.setSubject(loginUser.getUsername());//设置用户名到token
            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setKey(this.keyService.getMasterPrivateKey());
            jws.setDoKeyValidation(false);
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            refreshToken(loginUser);
            return jws.getCompactSerialization();
        }catch (Exception err){
            err.printStackTrace();
        }
        return null;
    }
    public JwtClaims parseToken(String token){
          try {

              String audience = "love@china";
              JwtConsumer consumer = new JwtConsumerBuilder()
                      .setExpectedAudience(audience)
                      .setVerificationKey(this.keyService.getMasterPublicKey())
                      .build();
              return consumer.processToClaims(token);
          }catch (Exception err){
              err.printStackTrace();
          }
          return null;
    }
    public static void writeToFile(String fileName, byte[] content) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(content);
        fos.close();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX))
        {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }


    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getCachedId()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String cacheId)
    {
        if (StringUtils.isNotEmpty(cacheId))
        {

            redisCache.deleteObject(cacheId);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        System.out.println("refreshing token");
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * MILLIS_MINUTE);
        String cachedId = loginUser.getCachedId();
        redisCache.setCacheObject(cachedId, loginUser, EXPIRE_TIME, TimeUnit.MINUTES);
        System.out.println("refreshed token for user "+loginUser.getUsername());
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) throws MalformedClaimException {
        JwtClaims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *

     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }



}
