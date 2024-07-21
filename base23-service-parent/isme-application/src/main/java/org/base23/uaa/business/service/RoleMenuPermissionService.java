package org.base23.uaa.business.service;

import java.util.List;
import java.util.Set;
import org.base23.uaa.core.domain.entity.MenuPermission;

public interface RoleMenuPermissionService {

  List<MenuPermission> getRolePermissions(String roleCode);

  void deleteRolePermission(long roleId);

  void batchSaveRoleMenuPermission(long roleId, Set<Long> permissionIds);

  void cleanCache();

  Set<String> getRoleMenuPermissionCodes(String roleCode);
}
