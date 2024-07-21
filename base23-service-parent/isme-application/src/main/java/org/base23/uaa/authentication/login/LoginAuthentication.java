package org.base23.uaa.authentication.login;

import org.base23.uaa.core.domain.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * SpringSecurity传输登录认证的数据的载体，相当一个Dto
 * 必须是 {@link Authentication} 实现类
 * 这里选择extends{@link AbstractAuthenticationToken}，而不是直接implements Authentication,
 * 是为了少些写代码。因为{@link Authentication}定义了很多接口，我们用不上。
 */
public abstract class LoginAuthentication extends AbstractAuthenticationToken  {

  protected User user; // 认证成功后，后台从数据库获取信息
  protected String roleCode; // 指定role角色登录. 非必填

  public LoginAuthentication() {
    // 不用SpringSecurity自带的权限校验，因为权限逻辑校验每个系统的差异非常大，SpringSecurity也没怎么实现
    super(null);
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }
}
