package org.base23.uaa.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.base23.database.entity.DBModel;
import org.hibernate.validator.constraints.Length;

@TableName("t_user")
public class User extends DBModel {

  public static final long USER_ID_SUPER = 1; // 超管userId

  @Length(max = 50)
  private String username;

  @Length(max = 255)
  private String password;

  private Boolean enable;

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

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }
}
