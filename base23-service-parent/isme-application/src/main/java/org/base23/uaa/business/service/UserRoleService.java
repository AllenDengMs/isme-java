package org.base23.uaa.business.service;

import java.util.List;
import org.base23.uaa.core.domain.dto.RoleBindUserParam;
import org.base23.uaa.core.domain.dto.UserBindRoleParam;
import org.base23.uaa.core.domain.entity.UserRole;

public interface UserRoleService {

  void saveBatch(List<UserRole> userRoles);

  void roleUnBindUsers(RoleBindUserParam param);

  void roleBindUsers(RoleBindUserParam param);

  void userBindRoles(UserBindRoleParam param);
  void userUnBindRoles(UserBindRoleParam param);
}
