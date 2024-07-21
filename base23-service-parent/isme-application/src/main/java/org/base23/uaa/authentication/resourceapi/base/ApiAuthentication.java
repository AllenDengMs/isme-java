package org.base23.uaa.authentication.resourceapi.base;

import org.base23.uaa.core.domain.dto.CurrentUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class ApiAuthentication extends AbstractAuthenticationToken {

  private String jwtToken; // 前端传过来

  private CurrentUser currentUser;

  public ApiAuthentication() {
    super(null); // 不用这个鉴权
  }

  @Override
  public Object getCredentials() {
    // 根据SpringSecurity的设计，授权成后，Credential（比如，登录密码）信息需要被清空
    return isAuthenticated() ? null : jwtToken;
  }

  @Override
  public Object getPrincipal() {
    // 根据SpringSecurity的设计，授权成功之前，getPrincipal返回的客户端传过来的数据。授权成功后，返回当前登陆用户的信息
    return isAuthenticated() ? currentUser : jwtToken;
  }

  public String getJwtToken() {
    return jwtToken;
  }

  public void setJwtToken(String jwtToken) {
    this.jwtToken = jwtToken;
  }

  public CurrentUser getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(CurrentUser currentUser) {
    this.currentUser = currentUser;
  }
}
