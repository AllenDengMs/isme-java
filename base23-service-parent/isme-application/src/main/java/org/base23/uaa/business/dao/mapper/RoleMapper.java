package org.base23.uaa.business.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.base23.uaa.core.domain.dto.RoleQueryBuilder;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.RoleMenuPermission;
import org.base23.uaa.core.domain.entity.UserRole;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

  List<Role> getRolesByUserId(@Param("userId") long userId);

  List<UserRole> queryUserRolesByUserIds(@Param("userIds") List<Long> userIds);

  void batchSaveRoleMenuPermission(@Param("list") List<RoleMenuPermission> list);

  Page<Role> pageQueryRoles(Page<Role> page, RoleQueryBuilder param);
}
