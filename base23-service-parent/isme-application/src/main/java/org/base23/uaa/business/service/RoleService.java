package org.base23.uaa.business.service;

import java.util.List;
import org.base23.commons.domain.dto.PageResult;
import org.base23.uaa.core.domain.dto.AddRoleParam;
import org.base23.uaa.core.domain.dto.RoleQueryBuilder;
import org.base23.uaa.core.domain.dto.UpdateRoleParam;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.UserRole;
import org.base23.uaa.core.domain.vo.RoleItemVO;

public interface RoleService {

  List<Role> getRolesByUserId(long userId);

  void checkRoleCode(String roleCode);

  /**
   * 查询所有角色
   * @return  key: roleId
   */
  List<Role> findAllRoles();

  List<UserRole> queryUserRolesByUserIds(List<Long> userIds);

  Role addRole(AddRoleParam param);

  PageResult<RoleItemVO> pageQueryRoles(RoleQueryBuilder param);

  int updateRole(UpdateRoleParam param);

  Role getRoleByCode(String roleCode);
}
