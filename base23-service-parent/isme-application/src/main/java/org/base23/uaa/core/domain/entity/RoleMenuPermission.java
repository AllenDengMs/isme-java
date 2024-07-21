package org.base23.uaa.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_role_menu_permission")
public class RoleMenuPermission {

  private Long roleId;

  private Long permissionId;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public Long getPermissionId() {
    return permissionId;
  }

  public void setPermissionId(Long permissionId) {
    this.permissionId = permissionId;
  }
}
