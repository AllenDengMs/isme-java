package org.base23.uaa.authentication.login.username;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import org.base23.commons.utils.JSON;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 用户名密码登录 AbstractAuthenticationProcessingFilter 的实现类要做的工作： 1. 从HttpServletRequest提取授权凭证。假设用户使用
 * 用户名/密码 登录，就需要在这里提取username和password。
 * 然后，把提取到的授权凭证封装到的Authentication对象，并且authentication.isAuthenticated()一定返回false 2.
 * 将Authentication对象传给AuthenticationManager进行实际的授权操作
 */
public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public UsernameAuthenticationFilter(AntPathRequestMatcher pathRequestMatcher,
      AuthenticationManager authenticationManager,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      AuthenticationFailureHandler authenticationFailureHandler) {
    super(pathRequestMatcher);
    setAuthenticationManager(authenticationManager);
    setAuthenticationSuccessHandler(authenticationSuccessHandler);
    setAuthenticationFailureHandler(authenticationFailureHandler);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    // 提取请求数据
    String requestJsonData = request.getReader().lines()
        .collect(Collectors.joining(System.lineSeparator()));

    // 封装成Spring Security需要的对象
    UsernameAuthentication authentication = JSON.convert(requestJsonData,
        UsernameAuthentication.class);
    authentication.setAuthenticated(false);

    // 开始登录认证。SpringSecurity会利用 Authentication对象去寻找 AuthenticationProvider进行登录认证
    return getAuthenticationManager().authenticate(authentication);
  }

}
