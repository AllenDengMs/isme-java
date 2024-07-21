package org.base23.uaa.business.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.base23.uaa.business.dao.mapper.UserRoleMapper;
import org.base23.uaa.core.domain.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleRepository extends ServiceImpl<UserRoleMapper, UserRole> implements
    IService<UserRole> {


  public boolean deleteByUserId(Long userId) {
    QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    return remove(queryWrapper);
  }
}