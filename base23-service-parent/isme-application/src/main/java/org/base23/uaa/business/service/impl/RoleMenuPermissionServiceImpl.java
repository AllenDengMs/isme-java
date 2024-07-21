package org.base23.uaa.business.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.base23.uaa.business.dao.mapper.MenuPermissionMapper;
import org.base23.uaa.business.dao.mapper.RoleMapper;
import org.base23.uaa.business.dao.repository.MenuPermissionRepository;
import org.base23.uaa.business.dao.repository.RoleMenuPermissionRepository;
import org.base23.uaa.business.service.RoleMenuPermissionService;
import org.base23.uaa.core.domain.entity.MenuPermission;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.RoleMenuPermission;
import org.base23.web.exception.Exceptions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@CacheConfig(cacheNames = "base23:roleMenu")
public class RoleMenuPermissionServiceImpl implements RoleMenuPermissionService {

  private final MenuPermissionMapper menuPermissionMapper;
  private final RoleMapper roleMapper;
  private final ApplicationContext applicationContext;
  private final MenuPermissionRepository menuPermissionRepository;
  private final RoleMenuPermissionRepository roleMenuPermissionRepository;

  public RoleMenuPermissionServiceImpl(MenuPermissionMapper menuPermissionMapper, RoleMapper roleMapper,
      ApplicationContext applicationContext, MenuPermissionRepository menuPermissionRepository,
      RoleMenuPermissionRepository roleMenuPermissionRepository) {
    this.menuPermissionMapper = menuPermissionMapper;
    this.roleMapper = roleMapper;
    this.applicationContext = applicationContext;
    this.menuPermissionRepository = menuPermissionRepository;
    this.roleMenuPermissionRepository = roleMenuPermissionRepository;
  }

  @Cacheable(key = "'rolePermissions:' + #p0")
  @Override
  public List<MenuPermission> getRolePermissions(String roleCode) {
    if (!StringUtils.hasText(roleCode)) {
      Exceptions.throwException("roleCode is empty");
    }
    List<MenuPermission> menuPermissions;
    if (Role.ROLE_CODE_SUPER.equals(roleCode)) {
      // 超管，拥有所有权限
      menuPermissions = menuPermissionRepository.list();
    } else {
      // 普通角色
      menuPermissions = menuPermissionMapper.getPermissionsByRoleCode(roleCode);
    }
    return menuPermissions;
  }

  @Override
  public void deleteRolePermission(long roleId) {
    roleMenuPermissionRepository.deleteByRoleId(roleId);
    cleanCaches();
  }

  @Override
  public void batchSaveRoleMenuPermission(long roleId, Set<Long> permissionIdList) {
    List<RoleMenuPermission> roleMenuPermissionList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(permissionIdList)) {
      for (Long permissionId : permissionIdList) {
        RoleMenuPermission roleMenuPermission = new RoleMenuPermission();
        roleMenuPermission.setRoleId(roleId);
        roleMenuPermission.setPermissionId(permissionId);
        roleMenuPermissionList.add(roleMenuPermission);
      }
    }

    if (!roleMenuPermissionList.isEmpty()) {
      roleMapper.batchSaveRoleMenuPermission(roleMenuPermissionList);
    }

    cleanCaches();
  }

  private void cleanCaches() {
    applicationContext.getBean(RoleMenuPermissionServiceImpl.class).cleanCache();
  }

  @CacheEvict(allEntries = true)
  @Override
  public void cleanCache() {

  }

  @Cacheable(key = "'roleMenuPermissionCodes:' + #p0")
  @Override
  public Set<String> getRoleMenuPermissionCodes(String roleCode) {
    HashSet<String> codes = new HashSet<>();
    List<MenuPermission> rolePermissions = getRolePermissions(roleCode);
    for (MenuPermission rolePermission : rolePermissions) {
      codes.add(rolePermission.getCode());
    }
    return codes;
  }

}
