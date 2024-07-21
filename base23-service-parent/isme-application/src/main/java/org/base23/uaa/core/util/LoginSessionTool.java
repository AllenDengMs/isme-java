package org.base23.uaa.core.util;

import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.web.exception.Exceptions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/** 用户登录信息解析器 */
public class LoginSessionTool {

  private LoginSessionTool() {}

  /** 获取当前登录用户信息 */
  public static CurrentUser getCurrentUser() {
    // 从SpringSecurity中获取
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // 转换类型。SpringSecurity的Authentication存的是Object对象，每次获取都要转，太麻烦了。所以在这里统一转换类型。
    if (authentication == null
        || !authentication.isAuthenticated()
        || authentication.getPrincipal() == null
        || !(authentication.getPrincipal() instanceof CurrentUser)) {
      Exceptions.throwException("{required.login:请登录后再访问！}");
    }
    return (CurrentUser) authentication.getPrincipal();
  }

}
