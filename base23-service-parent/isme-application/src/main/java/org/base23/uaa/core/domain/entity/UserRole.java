package org.base23.uaa.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_user_role")
public class UserRole {

  private Long userId;

  private Long roleId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }
}
