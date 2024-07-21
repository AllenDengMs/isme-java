package org.base23.uaa.core.domain.dto;

/**
 * 用户信息登陆后的信息，会序列化到Jwt的payload
 */
public class CurrentUser {

  private String sessionId; // 每次登录都生成一个，用于有状态登录，判断token是否过期
  private Long expiredTime; // 过期时间

  private Long userId;
  private String roleCode;

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Long getExpiredTime() {
    return expiredTime;
  }

  public void setExpiredTime(Long expiredTime) {
    this.expiredTime = expiredTime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getRoleCode() {
    return roleCode;
  }

  public void setRoleCode(String roleCode) {
    this.roleCode = roleCode;
  }
}
