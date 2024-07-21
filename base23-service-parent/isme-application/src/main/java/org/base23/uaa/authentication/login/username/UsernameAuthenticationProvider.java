package org.base23.uaa.authentication.login.username;

import org.base23.uaa.business.service.UserService;
import org.base23.uaa.core.domain.entity.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 帐号密码登录认证
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

  private final PasswordEncoder passwordEncoder;

  private final UserService userService;

  public UsernameAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
    super();
    this.passwordEncoder = passwordEncoder;
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // 用户提交的用户名 + 密码：
    UsernameAuthentication reqParam = (UsernameAuthentication) authentication;

    // 查数据库，匹配用户信息
    User user = userService.getUserByUsername(reqParam.getUsername());
    if (user == null || !passwordEncoder.matches(reqParam.getPassword(), user.getPassword())) {
      // 密码错误，直接抛异常。
      // 根据SpringSecurity框架的代码逻辑，认证失败时，应该抛这个异常：org.springframework.security.core.AuthenticationException
      // BadCredentialsException就是这个异常的子类
      // 抛出异常后后，AuthenticationFailureHandler的实现类会处理这个异常。
      throw new BadCredentialsException("${invalid.username.or.pwd:用户名或密码不正确}");
    }

    reqParam.setUser(user);
    reqParam.setAuthenticated(true); // 认证通过，这里一定要设成true
    return reqParam;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(UsernameAuthentication.class);
  }
}

