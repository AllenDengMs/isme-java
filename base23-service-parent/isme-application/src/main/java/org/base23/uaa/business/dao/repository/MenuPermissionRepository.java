package org.base23.uaa.business.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import org.base23.uaa.business.dao.mapper.MenuPermissionMapper;
import org.base23.uaa.core.domain.entity.MenuPermission;
import org.springframework.stereotype.Component;

@Component
public class MenuPermissionRepository extends
    ServiceImpl<MenuPermissionMapper, MenuPermission> implements IService<MenuPermission> {

  public boolean existsByCode(String code) {
    QueryWrapper<MenuPermission> queryWrapper = new QueryWrapper<>();
    return this.exists(queryWrapper.eq("code", code));
  }

  public List<MenuPermission> findByParentIdAndType(Long parentId, String type) {
    QueryWrapper<MenuPermission> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("parent_id", parentId);
    queryWrapper.eq("type", type);
    return this.list(queryWrapper);
  }

  public List<MenuPermission> findByType(String type) {
    QueryWrapper<MenuPermission> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("type", type);
    return this.list(queryWrapper);
  }

}
