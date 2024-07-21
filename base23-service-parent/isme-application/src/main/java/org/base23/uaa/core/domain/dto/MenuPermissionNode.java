package org.base23.uaa.core.domain.dto;

import java.util.ArrayList;
import java.util.List;
import org.base23.uaa.core.domain.entity.MenuPermission;

public class MenuPermissionNode extends MenuPermission {

  private List<MenuPermissionNode> children = new ArrayList<>();

  public List<MenuPermissionNode> getChildren() {
    return children;
  }

  public void setChildren(List<MenuPermissionNode> children) {
    this.children = children;
  }
}
