package org.base23.uaa.business.controller;

import org.base23.commons.domain.dto.PageResult;
import org.base23.uaa.business.service.UserService;
import org.base23.uaa.core.domain.dto.AddUserParam;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.domain.dto.ResetPasswordParam;
import org.base23.uaa.core.domain.dto.UpdatePasswordParam;
import org.base23.uaa.core.domain.dto.UpdateProfileParam;
import org.base23.uaa.core.domain.dto.UpdateUserParam;
import org.base23.uaa.core.domain.dto.UserQueryBuilder;
import org.base23.uaa.core.exception.UaaExceptions;
import org.base23.uaa.core.util.LoginSessionTool;
import org.base23.web.model.Result;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/user")
@Validated
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 图形验证码
   */
  @GetMapping("/detail")
  public Result getCurrentUserDetail() {
    return Result.data(userService.getCurrentUserDetail());
  }

  @GetMapping
  public Result pageQueryUser(UserQueryBuilder queryBuilder) {
    return Result.data(userService.pageQueryUsers(queryBuilder));
  }

  @PostMapping
  public Result addUser(@Validated @RequestBody AddUserParam param) {
    userService.addUser(param);
    return Result.data(true);
  }


  @DeleteMapping("/{id}")
  public Result deleteUser(@PathVariable("id") Long id) {
    throw UaaExceptions.FORBID_DELETE.exception();
  }

  @PatchMapping("/{id}")
  public Result updateUser(@PathVariable("id") int userId,
      @Validated @RequestBody UpdateUserParam param) {
    param.setId(userId);
    return Result.row(userService.updateUser(param));
  }

  @PatchMapping("/profile/{id}")
  public Result updateProfile(@PathVariable("id") Long id,
      @Validated @RequestBody UpdateProfileParam param) {
    CurrentUser currentUser = LoginSessionTool.getCurrentUser();
    param.setUserId(currentUser.getUserId()); // 不能改别人的资料
    userService.updateProfile(param);
    return Result.data(true);
  }

  /** 用户修改自己的密码 */
  @PatchMapping("/password")
  public Result updatePassword(@Validated @RequestBody UpdatePasswordParam param) {
    CurrentUser currentUser = LoginSessionTool.getCurrentUser();
    param.setUserId(currentUser.getUserId());
    userService.updatePassword(param);
    return Result.data(true);
  }

  /** 管理员修改用户的密码 */
  @PreAuthorize("@adminAuthz.hasMenuPermission('AddUser')") // AddUser是前端定义的权限编码，在t_menu_permission表的code
  @PatchMapping("/password/reset/{id}")
  public Result adminResetPassword(@PathVariable("id") int userId,
      @Validated @RequestBody ResetPasswordParam param) {
    userService.resetPassword(param);
    return Result.data(true);
  }
}
