package org.base23.uaa.business.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.base23.uaa.business.dao.mapper.RoleMapper;
import org.base23.uaa.core.domain.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleRepository extends ServiceImpl<RoleMapper, Role> implements IService<Role> {

  public boolean existsByCode(String code) {
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("code", code);
    return exists(queryWrapper);
  }

  public Role getByCode(String roleCode) {
    QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("code", roleCode);
    return getOneOpt(queryWrapper).orElse(null);
  }
}
