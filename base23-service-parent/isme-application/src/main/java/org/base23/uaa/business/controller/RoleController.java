package org.base23.uaa.business.controller;


import org.base23.uaa.business.service.MenuPermissionService;
import org.base23.uaa.business.service.RoleService;
import org.base23.uaa.business.service.UserRoleService;
import org.base23.uaa.core.domain.dto.AddRoleParam;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.domain.dto.RoleBindUserParam;
import org.base23.uaa.core.domain.dto.RoleQueryBuilder;
import org.base23.uaa.core.domain.dto.UpdateRoleParam;
import org.base23.uaa.core.exception.UaaExceptions;
import org.base23.uaa.core.util.LoginSessionTool;
import org.base23.web.model.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@Validated
public class RoleController {

  private final MenuPermissionService menuService;

  private final RoleService roleService;

  private final UserRoleService userRoleService;

  public RoleController(MenuPermissionService menuService, RoleService roleService,
      UserRoleService userRoleService) {
    this.menuService = menuService;
    this.roleService = roleService;
    this.userRoleService = userRoleService;
  }

  // 获取当前用户（所选择的角色）拥有的权限信息
  @GetMapping("/permissions/tree")
  public Result getRolePermissionTree() {
    CurrentUser currentUser = LoginSessionTool.getCurrentUser();
    return Result.data(menuService.getRolePermissionTree(currentUser.getRoleCode()));
  }

  @PostMapping
  public Result addRole(@Validated @RequestBody AddRoleParam param) {
    return Result.data(roleService.addRole(param));
  }

  @GetMapping("/page")
  public Result pageQueryRoles(RoleQueryBuilder param) {
    return Result.data(roleService.pageQueryRoles(param));
  }

  @GetMapping
  public Result findAllRoles() {
    return Result.data(roleService.findAllRoles());
  }

  @DeleteMapping("/{id}")
  public Result delete(@PathVariable("id") int id) {
    throw UaaExceptions.FORBID_DELETE.exception();
  }

  @PatchMapping("/{id}")
  public Result updateRole(@PathVariable("id") int id, @RequestBody UpdateRoleParam param) {
    return Result.row(roleService.updateRole(param));
  }

  @PatchMapping("/users/remove/{roleId}")
  public Result roleUnBindUsers(@PathVariable("roleId") int roleId,
      @Validated @RequestBody RoleBindUserParam param) {
    param.setRoleId(roleId);
    userRoleService.roleUnBindUsers(param);
    return Result.data(true);
  }

  @PatchMapping("/users/add/{roleId}")
  public Result roleBindUsers(@PathVariable("roleId") int roleId,
      @Validated @RequestBody RoleBindUserParam param) {
    param.setRoleId(roleId);
    userRoleService.roleBindUsers(param);
    return Result.data(true);
  }
}
