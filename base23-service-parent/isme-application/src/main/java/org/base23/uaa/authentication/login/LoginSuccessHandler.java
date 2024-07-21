package org.base23.uaa.authentication.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.base23.commons.utils.Times;
import org.base23.commons.utils.WebJSON;
import org.base23.uaa.business.service.JwtService;
import org.base23.uaa.business.service.RoleService;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.User;
import org.base23.uaa.core.exception.UaaExceptions;
import org.base23.web.exception.BaseException;
import org.base23.web.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 认证成功/登录成功 事件处理器
 */
@Component
public class LoginSuccessHandler extends
    AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

  private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

  @Autowired
  private RoleService roleService;

  @Autowired
  private JwtService jwtService;

  public LoginSuccessHandler() {
    this.setRedirectStrategy(new RedirectStrategy() {
      @Override
      public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
          throws IOException {
        // 更改重定向策略，前后端分离项目，后端使用RestFul风格，无需做重定向
        // Do nothing, no redirects in REST
      }
    });
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    if (!(authentication instanceof LoginAuthentication loginAuthentication)) {
      throw new BaseException("Authentication必须继承LoginAuthentication！",
          BaseException.DEFAULT_CODE, BaseException.DEFAULT_HTTP_STATUS);
    }
    User user = loginAuthentication.getUser();
    if (user == null) {
      throw new BaseException("LoginAuthentication未设置User信息！",
          BaseException.DEFAULT_CODE, BaseException.DEFAULT_HTTP_STATUS);
    }

    if (Boolean.FALSE.equals(user.getEnable())) {
      // 账号被停用
      throw UaaExceptions.USER_IS_DISABLE.exception();
    }

    // 登录成功后，获取角色、权限相关信息
    Role role = null;
    if (loginAuthentication.getRoleCode() == null) {
      // 没有指定的角色登录，随便选一个角色登录
      List<Role> roles = roleService.getRolesByUserId(user.getId());
      if (!roles.isEmpty()) {
        role = roles.getFirst();
      }
    } else {
      // 指定角色登录
      role = roleService.getRoleByCode(loginAuthentication.getRoleCode());
    }

    if (role == null) {
      // 没有权限
      throw UaaExceptions.USER_NO_ROLE.exception();
    } else if (Boolean.FALSE.equals(role.getEnable())){
      // 角色被禁用
      throw UaaExceptions.ROLE_IS_DISABLE.exception();
    }


    CurrentUser userLoginInfo = new CurrentUser();
    userLoginInfo.setSessionId(UUID.randomUUID().toString());
    userLoginInfo.setUserId(user.getId());
    userLoginInfo.setRoleCode(role.getCode());

    // 生成token和refreshToken
    Map<String, Object> responseData = new LinkedHashMap<>();
    responseData.put("accessToken", generateToken(userLoginInfo));
    responseData.put("refreshToken", generateRefreshToken(userLoginInfo));

    // 虽然APPLICATION_JSON_UTF8_VALUE过时了，但也要用。因为Postman工具不声明utf-8编码就会出现乱码
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    PrintWriter writer = response.getWriter();
    writer.print(WebJSON.stringify(Result.data(responseData, "${login.success:登录成功！}")));
    writer.flush();
    writer.close();
  }

  public String generateToken(CurrentUser currentUser) {
    long expiredTime = Times.nowMilli() + TimeUnit.DAYS.toMillis(10);
    currentUser.setExpiredTime(expiredTime);
    return jwtService.createJwt(currentUser, expiredTime);
  }

  private String generateRefreshToken(CurrentUser loginInfo) {
    return jwtService.createJwt(loginInfo, Times.nowMilli() + TimeUnit.DAYS.toMillis(30));
  }

}
