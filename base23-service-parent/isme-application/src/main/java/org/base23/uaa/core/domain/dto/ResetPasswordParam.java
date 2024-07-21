package org.base23.uaa.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResetPasswordParam {
  private long id;// 这个是userId，前端随便传
  private String password;
  @JsonIgnore // 前端不能传过来
  private long updateTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(long updateTime) {
    this.updateTime = updateTime;
  }
}
