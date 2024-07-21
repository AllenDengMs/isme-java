package org.base23.uaa.core.domain.vo;

import java.util.List;
import org.base23.commons.annotation.TimestampToDate;

public class UserItemVO {

  private Long id; // userId
  @TimestampToDate
  private Long createTime;
  @TimestampToDate
  private Long updateTime;
  private String username;
  private Boolean enable;

  // 个人信息
  private String avatar;
  private String address;
  private String email;
  private String nickName;
  private Integer gender;

  private List<RoleVO> roles;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public List<RoleVO> getRoles() {
    return roles;
  }

  public void setRoles(List<RoleVO> roles) {
    this.roles = roles;
  }
}
