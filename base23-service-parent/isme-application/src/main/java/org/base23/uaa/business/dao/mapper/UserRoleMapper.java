package org.base23.uaa.business.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.base23.uaa.core.domain.dto.RoleBindUserParam;
import org.base23.uaa.core.domain.dto.UserBindRoleParam;
import org.base23.uaa.core.domain.entity.UserRole;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

  void roleUnBindUsers(RoleBindUserParam param);

  void userUnBindRoles(UserBindRoleParam param);
}
