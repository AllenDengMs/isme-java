package org.base23.uaa.core.domain.dto;

import jakarta.validation.constraints.Size;
import java.util.Set;
import org.hibernate.validator.constraints.Length;

public class AddUserParam {

  private boolean enable;
  @Length(max = 50)
  private String username;
  @Length(max = 50)
  private String password;
  @Size(min = 1, max = 5)
  private Set<Long> roleIds;

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(Set<Long> roleIds) {
    this.roleIds = roleIds;
  }
}
