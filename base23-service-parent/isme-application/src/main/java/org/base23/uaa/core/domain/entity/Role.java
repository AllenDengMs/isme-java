package org.base23.uaa.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.base23.database.entity.DBModel;
import org.hibernate.validator.constraints.Length;

@TableName("t_role")
public class Role extends DBModel {

  public static final String ROLE_CODE_SUPER = "SUPER_ADMIN"; // 超级管理员角色Code
  public static final long ROLE_ID_SUPER = 1; // 超级管理员角色

  @Length(max = 50)
  private String code;

  @Length(max = 50)
  private String name;

  private Boolean enable;

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
