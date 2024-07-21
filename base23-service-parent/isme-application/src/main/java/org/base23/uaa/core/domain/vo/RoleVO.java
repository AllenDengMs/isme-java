package org.base23.uaa.core.domain.vo;

import org.hibernate.validator.constraints.Length;

public class RoleVO {

  private Long id; // roleId

  @Length(max = 50)
  private String code;

  @Length(max = 50)
  private String name;

  private Boolean enable;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
}
