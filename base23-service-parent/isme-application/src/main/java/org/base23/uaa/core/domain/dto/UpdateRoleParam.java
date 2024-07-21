package org.base23.uaa.core.domain.dto;

import java.util.Set;
import org.hibernate.validator.constraints.Length;

public class UpdateRoleParam {

  private long id; // roleId

  @Length(max = 50)
  private String code;

  @Length(max = 50)
  private String name;

  private Boolean enable;

  private Set<Long> permissionIds;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Set<Long> getPermissionIds() {
    return permissionIds;
  }

  public void setPermissionIds(Set<Long> permissionIds) {
    this.permissionIds = permissionIds;
  }
}
