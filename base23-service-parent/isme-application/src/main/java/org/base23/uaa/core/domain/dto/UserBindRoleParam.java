package org.base23.uaa.core.domain.dto;

import jakarta.validation.constraints.Size;
import java.util.Set;

public class UserBindRoleParam {

  private long userId;
  @Size(max = 10)
  private Set<Long> roleIds;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Set<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Set<Long> roleIds) {
    this.roleIds = roleIds;
  }
}
