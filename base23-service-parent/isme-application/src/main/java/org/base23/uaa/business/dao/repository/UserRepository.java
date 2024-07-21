package org.base23.uaa.business.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.base23.uaa.business.dao.mapper.UserMapper;
import org.base23.uaa.core.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserRepository extends ServiceImpl<UserMapper, User> implements IService<User> {

  public boolean existsByUsername(String username) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    return exists(queryWrapper);
  }

  public User getByUsername(String username) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("username", username);
    return getOne(queryWrapper);
  }
}