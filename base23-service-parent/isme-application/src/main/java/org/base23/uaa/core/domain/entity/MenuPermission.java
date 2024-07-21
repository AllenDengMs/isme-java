package org.base23.uaa.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.base23.database.entity.DBModel;
import org.hibernate.validator.constraints.Length;

/**
 * 前端UI组件权限
 */
@TableName("t_menu_permission")
public class MenuPermission extends DBModel {

  @Length(max = 255)
  private String name;

  @Length(max = 50)
  private String code;

  @Length(max = 255)
  private String type;

  private Long parentId;

  @Length(max = 255)
  private String path;

  @Length(max = 255)
  private String redirect;

  @Length(max = 255)
  private String icon;

  @Length(max = 255)
  private String component;

  @Length(max = 255)
  private String layout;

  private Boolean keepAlive;

  @Length(max = 255)
  private String method;

  @Length(max = 255)
  private String description;

  @JsonProperty("show") // 返回给前端的json时，变成show字段，后台不要用show这个数据库的关键字
  private Boolean canShow;

  private Boolean enable;

  @JsonProperty("order") // 返回给前端的json时，变成order字段，后台不要用这个数据库的关键字
  private Integer sortOrder;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getRedirect() {
    return redirect;
  }

  public void setRedirect(String redirect) {
    this.redirect = redirect;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getComponent() {
    return component;
  }

  public void setComponent(String component) {
    this.component = component;
  }

  public String getLayout() {
    return layout;
  }

  public void setLayout(String layout) {
    this.layout = layout;
  }

  public Boolean getKeepAlive() {
    return keepAlive;
  }

  public void setKeepAlive(Boolean keepAlive) {
    this.keepAlive = keepAlive;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }

  public Boolean getCanShow() {
    return canShow;
  }

  public void setCanShow(Boolean canShow) {
    this.canShow = canShow;
  }

  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }
}
