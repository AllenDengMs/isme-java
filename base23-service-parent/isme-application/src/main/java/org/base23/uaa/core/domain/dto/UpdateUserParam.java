package org.base23.uaa.core.domain.dto;

import jakarta.validation.constraints.Size;
import java.util.Set;
import org.hibernate.validator.constraints.Length;

public class UpdateUserParam {

  private long id; // userId
  @Size(min = 1, max = 64)
  private Set<Long> roleIds;
  @Length(max = 60)
  private String username;
  private Boolean enable;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Set<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Set<Long> roleIds) {
    this.roleIds = roleIds;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }
}
