package org.base23.uaa.business.service;

import java.util.List;
import org.base23.uaa.core.domain.dto.MenuPermissionNode;
import org.base23.uaa.core.domain.entity.MenuPermission;

public interface MenuPermissionService {

  // all permission
  List<MenuPermissionNode> getPermissionTree();

  // permission of menu type
  List<MenuPermissionNode> getMenuPermissionTree();

  List<MenuPermissionNode> getRolePermissionTree(String roleCode);

  void addMenuPermission(MenuPermission menuPermission);

  void updatePermission(MenuPermission menuPermission);


  List<MenuPermission> getButtonPermissionByParentId(Long parentPermissionId);

  void cleanCache();
}
