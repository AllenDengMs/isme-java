package org.base23.uaa.business.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.base23.uaa.business.dao.mapper.ProfileMapper;
import org.base23.uaa.core.domain.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileRepository extends ServiceImpl<ProfileMapper, Profile> implements
    IService<Profile> {

  public Profile getByUserId(Long userId) {
    QueryWrapper<Profile> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    return getOne(queryWrapper);
  }
}