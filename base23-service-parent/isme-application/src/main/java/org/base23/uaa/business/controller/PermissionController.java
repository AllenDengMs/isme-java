package org.base23.uaa.business.controller;


import org.base23.uaa.business.service.MenuPermissionService;
import org.base23.uaa.core.domain.entity.MenuPermission;
import org.base23.uaa.core.exception.UaaExceptions;
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
@RequestMapping("/permission")
@Validated
public class PermissionController {

  private final MenuPermissionService menuService;

  public PermissionController(MenuPermissionService menuService) {
    this.menuService = menuService;
  }

  @GetMapping("/tree")
  public Result getPermissionTrees() {
    return Result.data(menuService.getPermissionTree());
  }

  @GetMapping("/menu/tree")
  public Result getMenuPermissionTrees() {
    return Result.data(menuService.getMenuPermissionTree());
  }

  @GetMapping("/button/{permissionId}")
  public Result getButtonPermission(@PathVariable("permissionId") Long permissionId) {
    return Result.data(menuService.getButtonPermissionByParentId(permissionId));
  }

  @PostMapping
  public Result addPermission(@RequestBody MenuPermission menuPermission) {
    menuService.addMenuPermission(menuPermission);
    return Result.data(menuPermission);
  }

  @DeleteMapping("/{id}")
  public Result deletePermission(@PathVariable("id") long id) {
    throw UaaExceptions.FORBID_DELETE.exception();
  }

  @PatchMapping("/{id}")
  public Result updatePermission(@PathVariable("id") long id,
      @RequestBody MenuPermission menuPermission) {
    menuPermission.setId(id);
    menuService.updatePermission(menuPermission);
    return Result.data(true);
  }
}
