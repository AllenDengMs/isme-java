package org.base23.uaa.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.base23.database.entity.DBModel;
import org.hibernate.validator.constraints.Length;

@TableName("t_profile")
public class Profile extends DBModel {

  private Integer gender;

  @Length(max = 255)
  private String avatar;

  @Length(max = 255)
  private String address;

  @Length(max = 255)
  private String email;

  private Long userId;

  @Length(max = 50)
  private String nickName;

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
}
