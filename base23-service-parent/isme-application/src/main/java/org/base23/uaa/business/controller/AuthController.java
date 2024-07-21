package org.base23.uaa.business.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.base23.uaa.authentication.login.LoginSuccessHandler;
import org.base23.uaa.authentication.login.username.UsernameAuthentication;
import org.base23.uaa.business.service.RoleService;
import org.base23.uaa.business.service.UserService;
import org.base23.uaa.business.service.impl.CaptchaService;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.util.LoginSessionTool;
import org.base23.web.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private CaptchaService captchaService;

  @Autowired
  private LoginSuccessHandler loginSuccessHandler;

  @Autowired
  private RoleService roleService;

  @Autowired
  private UserService userService;

  /**
   * 图形验证码
   */
  @GetMapping("/captcha")
  public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
    captchaService.createImgCaptcha(request, response);
  }


  /**
   * 登录成功后，切换角色
   */
  @PostMapping("/current-role/switch/{roleCode}")
  public void switchRole(@PathVariable("roleCode") String roleCode,
      HttpServletRequest request, HttpServletResponse response) throws IOException {
    roleService.checkRoleCode(roleCode); // 防止前端乱传数据

    UsernameAuthentication authentication = new UsernameAuthentication();
    authentication.setAuthenticated(true);

    CurrentUser currentUser = LoginSessionTool.getCurrentUser();
    authentication.setUser(userService.getUserById(currentUser.getUserId()));
    authentication.setRoleCode(roleCode);

    // 重新生成jwt
    loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);
  }

  /** 退出登录 */
  @PostMapping("/logout")
  public Result logout() {
    return Result.data(true);
  }

}
