package org.base23.uaa.core.domain.dto;

import java.util.Set;

public class AddRoleParam {

  private String code;

  private String name;

  private boolean enable;

  private Set<Long> permissionIds;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public Set<Long> getPermissionIds() {
    return permissionIds;
  }

  public void setPermissionIds(Set<Long> permissionIds) {
    this.permissionIds = permissionIds;
  }
}
