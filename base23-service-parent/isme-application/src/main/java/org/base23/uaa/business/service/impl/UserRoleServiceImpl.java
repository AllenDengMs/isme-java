package org.base23.uaa.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.base23.uaa.business.dao.mapper.UserRoleMapper;
import org.base23.uaa.business.dao.repository.UserRoleRepository;
import org.base23.uaa.business.service.UserRoleService;
import org.base23.uaa.core.domain.dto.RoleBindUserParam;
import org.base23.uaa.core.domain.dto.UserBindRoleParam;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.User;
import org.base23.uaa.core.domain.entity.UserRole;
import org.base23.uaa.core.exception.UaaExceptions;
import org.base23.web.exception.Exceptions;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

  private final UserRoleMapper userRoleMapper;

  private final ApplicationContext applicationContext;
  private final UserRoleRepository userRoleRepository;

  public UserRoleServiceImpl(UserRoleMapper userRoleMapper, ApplicationContext applicationContext,
      UserRoleRepository userRoleRepository) {
    this.userRoleMapper = userRoleMapper;
    this.applicationContext = applicationContext;
    this.userRoleRepository = userRoleRepository;
  }

  @Transactional
  @Override
  public void saveBatch(List<UserRole> userRoles) {
    userRoleRepository.saveBatch(userRoles);
  }

  @Override
  public void roleUnBindUsers(RoleBindUserParam param) {
    if (param.getUserIds() == null || param.getUserIds().isEmpty()) {
      Exceptions.throwException("userIds is empty!");
    }

    if (param.getRoleId() == Role.ROLE_ID_SUPER && param.getUserIds()
        .contains(User.USER_ID_SUPER)) {
      throw UaaExceptions.SUPER_USER_NO_SUPER_PERMISSION.exception();
    }

    userRoleMapper.roleUnBindUsers(param);
  }

  @Transactional
  @Override
  public void roleBindUsers(RoleBindUserParam param) {
    long roleId = param.getRoleId();
    List<UserRole> userRoles = new ArrayList<>();
    for (Long userId : param.getUserIds()) {
      UserRole userRole = new UserRole();
      userRole.setRoleId(roleId);
      userRole.setUserId(userId);
      userRoles.add(userRole);
    }

    applicationContext.getBean(UserRoleServiceImpl.class).saveBatch(userRoles);
  }

  @Transactional
  @Override
  public void userBindRoles(UserBindRoleParam param) {
    long userId = param.getUserId();

    if (userId == User.USER_ID_SUPER) {
      if (param.getRoleIds() != null && !param.getRoleIds().contains(Role.ROLE_ID_SUPER)) {
        throw UaaExceptions.SUPER_USER_NO_SUPER_PERMISSION.exception();
      }
    }

    // 先解绑，再重新绑定，可以避免主键冲突
    userRoleRepository.deleteByUserId(userId);

    List<UserRole> userRoles = new ArrayList<>();
    for (Long roleId : param.getRoleIds()) {
      UserRole userRole = new UserRole();
      userRole.setRoleId(roleId);
      userRole.setUserId(userId);
      userRoles.add(userRole);
    }
    applicationContext.getBean(UserRoleServiceImpl.class).saveBatch(userRoles);
  }

  @Override
  public void userUnBindRoles(UserBindRoleParam param) {
    userRoleMapper.userUnBindRoles(param);
  }
}
