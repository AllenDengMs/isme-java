package org.base23.uaa.authorization.service.impl;

import java.util.Set;
import org.base23.uaa.business.service.RoleMenuPermissionService;
import org.base23.uaa.business.service.RoleService;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.exception.UaaExceptions;
import org.base23.uaa.core.util.LoginSessionTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义鉴权
 */
@Component("adminAuthz") // beanName尽量短一些，方便写代码
public class AdminAuthorizationLogic {

  @Autowired
  private RoleService roleService;

  @Autowired
  private RoleMenuPermissionService roleMenuPermissionService;

  /**
   * 登录成功后，最后会到这里来检查是否有权限
   * @param menuPermissionCodes 前端定义的UI组件权限码
   * @return 是否有权限
   */
  public boolean hasMenuPermission(String... menuPermissionCodes) {
    CurrentUser currentUser = LoginSessionTool.getCurrentUser();
    String roleCode = currentUser.getRoleCode();

    Role role = roleService.getRoleByCode(roleCode);

    // 角色已经禁用
    if (role == null || !role.getEnable()) {
      throw UaaExceptions.ROLE_IS_DISABLE.exception();
    }

    // 检查当前用户拥有的权限包含api所需要的
    Set<String> rolePermissions = roleMenuPermissionService.getRoleMenuPermissionCodes(roleCode);
    for (String menuPermissionCode : menuPermissionCodes) {
      if (rolePermissions.contains(menuPermissionCode)) {
        return true;
      }
    }

    return false;
  }
}