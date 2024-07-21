package org.base23.uaa.core.domain.dto;


import org.base23.commons.domain.dto.PageQueryParam;

public class UserQueryBuilder extends PageQueryParam {

  private String username;
  private Integer gender;
  private Boolean enable;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }
}
