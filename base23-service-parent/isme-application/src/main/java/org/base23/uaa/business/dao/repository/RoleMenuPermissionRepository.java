package org.base23.uaa.business.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.base23.uaa.business.dao.mapper.RoleMenuPermissionMapper;
import org.base23.uaa.core.domain.entity.RoleMenuPermission;
import org.springframework.stereotype.Component;

@Component
public class RoleMenuPermissionRepository
    extends ServiceImpl<RoleMenuPermissionMapper, RoleMenuPermission> implements
    IService<RoleMenuPermission> {

  public void deleteByRoleId(long roleId) {
    QueryWrapper<RoleMenuPermission> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("role_id", roleId);
    this.remove(queryWrapper);
  }
}