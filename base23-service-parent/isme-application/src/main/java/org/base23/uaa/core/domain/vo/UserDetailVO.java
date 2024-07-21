package org.base23.uaa.core.domain.vo;

import java.util.List;
import org.base23.commons.annotation.TimestampToDate;

public class UserDetailVO {

  private Long id; // userId
  private String username;
  private boolean enable;
  @TimestampToDate
  private Long createTime;
  @TimestampToDate
  private Long updateTime;
  private ProfileVO profile;
  private List<RoleVO> roles;
  private RoleVO currentRole;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public ProfileVO getProfile() {
    return profile;
  }

  public void setProfile(ProfileVO profile) {
    this.profile = profile;
  }

  public List<RoleVO> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleVO> roles) {
    this.roles = roles;
  }

  public RoleVO getCurrentRole() {
    return currentRole;
  }

  public void setCurrentRole(RoleVO currentRole) {
    this.currentRole = currentRole;
  }
}
