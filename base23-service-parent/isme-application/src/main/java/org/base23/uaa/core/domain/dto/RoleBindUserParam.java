package org.base23.uaa.core.domain.dto;

import jakarta.validation.constraints.Size;
import java.util.Set;

public class RoleBindUserParam {

  private long roleId;
  @Size(max = 500)
  private Set<Long> userIds;

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public Set<Long> getUserIds() {
    return userIds;
  }

  public void setUserIds(Set<Long> userIds) {
    this.userIds = userIds;
  }
}
