package org.base23.uaa.core.domain.vo;

import java.util.List;
import org.hibernate.validator.constraints.Length;

public class RoleItemVO {

  private int id;

  @Length(max = 50)
  private String code;

  @Length(max = 50)
  private String name;

  private Boolean enable;

  private List<Long> permissionIds;

  public int getId() {
    return id;
  }

  public void setId(int id) {
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

  public List<Long> getPermissionIds() {
    return permissionIds;
  }

  public void setPermissionIds(List<Long> permissionIds) {
    this.permissionIds = permissionIds;
  }
}
