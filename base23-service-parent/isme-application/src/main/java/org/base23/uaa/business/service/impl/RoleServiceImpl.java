package org.base23.uaa.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.base23.commons.domain.dto.PageResult;
import org.base23.commons.utils.Times;
import org.base23.database.utils.Pages;
import org.base23.uaa.business.dao.mapper.RoleMapper;
import org.base23.uaa.business.dao.repository.RoleRepository;
import org.base23.uaa.business.service.RoleMenuPermissionService;
import org.base23.uaa.business.service.RoleService;
import org.base23.uaa.core.domain.convertor.RoleConvertor;
import org.base23.uaa.core.domain.dto.AddRoleParam;
import org.base23.uaa.core.domain.dto.RoleQueryBuilder;
import org.base23.uaa.core.domain.dto.UpdateRoleParam;
import org.base23.uaa.core.domain.entity.MenuPermission;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.UserRole;
import org.base23.uaa.core.domain.vo.RoleItemVO;
import org.base23.web.exception.Exceptions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@CacheConfig(cacheNames = "base23:role")
public class RoleServiceImpl implements RoleService {

  private final RoleMapper roleMapper;
  private final RoleMenuPermissionService roleMenuService;
  private final RoleRepository roleRepository;

  public RoleServiceImpl(RoleMapper roleMapper,
      RoleMenuPermissionService roleMenuService, RoleRepository roleRepository) {
    this.roleMapper = roleMapper;
    this.roleMenuService = roleMenuService;
    this.roleRepository = roleRepository;
  }

  @Override
  public List<Role> getRolesByUserId(long userId) {
    return roleMapper.getRolesByUserId(userId);
  }

  @Override
  public void checkRoleCode(String roleCode) {
    if (!roleRepository.existsByCode(roleCode)) {
      Exceptions.throwException("invalid role code");
    }
  }

  @Cacheable(key = "'allRols'")
  @Override
  public List<Role> findAllRoles() {
    return roleRepository.list();
  }

  @Override
  public List<UserRole> queryUserRolesByUserIds(List<Long> userIds) {
    if (CollectionUtils.isEmpty(userIds)) {
      return List.of();
    }
    return roleMapper.queryUserRolesByUserIds(userIds);
  }

  @Transactional
  @Override
  public Role addRole(AddRoleParam param) {
    Role role = new Role();
    role.setCode(param.getCode());
    role.setEnable(param.isEnable());
    role.setName(param.getName());
    long now = Times.nowSecond();
    role.setCreateTime(now);
    role.setUpdateTime(now);
    if (roleRepository.existsByCode(param.getCode())) {
      Exceptions.throwException("role code exists");
    }

    roleRepository.save(role);
    roleMenuService.batchSaveRoleMenuPermission(role.getId(), param.getPermissionIds());
    return role;
  }

  @Override
  public PageResult<RoleItemVO> pageQueryRoles(RoleQueryBuilder param) {
    Page<Role> rolePage = roleMapper.pageQueryRoles(Pages.of(param), param);
    // 数据结构转换
    List<RoleItemVO> roleItemVOList = RoleConvertor.INSTANCE.toRoleItemVOList(
        rolePage.getRecords());

    // 获取权限id
    for (RoleItemVO roleItemVO : roleItemVOList) {
      List<Long> permissionIds = roleMenuService.getRolePermissions(roleItemVO.getCode())
          .stream()
          .map(MenuPermission::getId)
          .toList();
      roleItemVO.setPermissionIds(permissionIds);
    }

    return Pages.result(rolePage.getTotal(), roleItemVOList);
  }

  @CacheEvict(allEntries = true)
  @Transactional
  @Override
  public int updateRole(UpdateRoleParam param) {
    long roleId = param.getId();
    Role roleFromDB = roleRepository.getById(roleId);
    roleFromDB.setUpdateTime(Times.nowSecond());

    if (param.getName() != null) {
      roleFromDB.setName(param.getName());
    }

    if (param.getEnable() != null) {
      roleFromDB.setEnable(param.getEnable());
    }

    roleRepository.updateById(roleFromDB);

    if (param.getPermissionIds() != null) {
      roleMenuService.deleteRolePermission(roleId);
      roleMenuService.batchSaveRoleMenuPermission(roleId, param.getPermissionIds());
    }
    return 1;
  }

  @Cacheable(key = "'code:' + #p0")
  @Override
  public Role getRoleByCode(String roleCode) {
    return roleRepository.getByCode(roleCode);
  }

}
