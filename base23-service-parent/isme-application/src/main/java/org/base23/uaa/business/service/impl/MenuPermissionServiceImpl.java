package org.base23.uaa.business.service.impl;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.base23.commons.utils.Times;
import org.base23.uaa.business.dao.repository.MenuPermissionRepository;
import org.base23.uaa.business.service.MenuPermissionService;
import org.base23.uaa.business.service.RoleMenuPermissionService;
import org.base23.uaa.core.domain.convertor.MenuPermissionConvertor;
import org.base23.uaa.core.domain.dto.MenuPermissionNode;
import org.base23.uaa.core.domain.entity.MenuPermission;
import org.base23.web.exception.Exceptions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames =  "base23:menu")
public class MenuPermissionServiceImpl implements MenuPermissionService {

  private final RoleMenuPermissionService roleMenuService;
  private final MenuPermissionRepository menuPermissionRepository;

  private final ApplicationContext applicationContext;

  public MenuPermissionServiceImpl(RoleMenuPermissionService roleMenuService,
      MenuPermissionRepository menuPermissionRepository, ApplicationContext applicationContext) {
    this.roleMenuService = roleMenuService;
    this.menuPermissionRepository = menuPermissionRepository;
    this.applicationContext = applicationContext;
  }

  @Cacheable(key = "'permissionTree'")
  @Override
  public List<MenuPermissionNode> getPermissionTree() {
    List<MenuPermission> menuPermissions = menuPermissionRepository.list();
    List<MenuPermissionNode> tree = buildMenuTree(menuPermissions);
    sortByOrder(tree);
    return tree;
  }

  @Cacheable(key = "'menuPermissionTree'")
  @Override
  public List<MenuPermissionNode> getMenuPermissionTree() {
    List<MenuPermission> menuPermissions = menuPermissionRepository.findByType("MENU");
    List<MenuPermissionNode> tree = buildMenuTree(menuPermissions);
    sortByOrder(tree);
    return tree;
  }

  @Cacheable(key = "'rolePermissionTree:' + #p0")
  @Override
  public List<MenuPermissionNode> getRolePermissionTree(String roleCode) {
    List<MenuPermission> menuPermissions = roleMenuService.getRolePermissions(roleCode);
    List<MenuPermissionNode> tree = buildMenuTree(menuPermissions);
    sortById(tree);
    return tree;
  }

  @Override
  public void addMenuPermission(MenuPermission menuPermission) {
    boolean exists = menuPermissionRepository.existsByCode(menuPermission.getCode());
    if (exists) {
      Exceptions.throwException("code already exists!");
    }

    long now = Times.nowSecond();
    menuPermission.setCreateTime(now);
    menuPermission.setUpdateTime(now);

    menuPermissionRepository.save(menuPermission);
    cleanCaches();
  }

  @Override
  public void updatePermission(MenuPermission menuPermission) {
    menuPermission.setUpdateTime(Times.nowSecond());
    menuPermissionRepository.updateById(menuPermission);
    cleanCaches();
  }

  @Override
  public List<MenuPermission> getButtonPermissionByParentId(Long parentPermissionId) {
    return menuPermissionRepository.findByParentIdAndType(parentPermissionId, "BUTTON");
  }

  public void cleanCaches() {
    applicationContext.getBean(MenuPermissionServiceImpl.class).cleanCache();
    applicationContext.getBean(RoleMenuPermissionService.class).cleanCache();
  }

  @CacheEvict(allEntries = true)
  @Override
  public void cleanCache() {
    //  使用@CacheEvict(allEntries = true)清缓存
  }

  public List<MenuPermissionNode> buildMenuTree(List<MenuPermission> menuPermissions) {
    Map<Long, MenuPermissionNode> nodeMap = new HashMap<>();
    List<MenuPermissionNode> rootNodes = new ArrayList<>();

    // 将 MenuPermission 转换为 MenuPermissionNode 并存入 nodeMap
    for (MenuPermission permission : menuPermissions) {
      MenuPermissionNode node = MenuPermissionConvertor.INSTANCE.toNode(permission);
      nodeMap.put(node.getId(), node);
    }

    // 构建树结构
    for (MenuPermissionNode node : nodeMap.values()) {
      if (node.getParentId() == null || node.getParentId() == 0) {
        rootNodes.add(node); // 没有父节点的为根节点
      } else {
        MenuPermissionNode parentNode = nodeMap.get(node.getParentId());
        if (parentNode != null) {
          parentNode.getChildren().add(node); // 添加子节点到父节点的 children 列表中
        }
      }
    }

    return rootNodes;
  }

  public static void sortById(List<MenuPermissionNode> nodes) {
    if (nodes == null || nodes.isEmpty()) {
      return;
    }
    nodes.sort(Comparator.comparingLong(MenuPermissionNode::getId));
    for (MenuPermissionNode node : nodes) {
      sortById(node.getChildren());
    }
  }

  public static void sortByOrder(List<MenuPermissionNode> nodes) {
    if (nodes == null || nodes.isEmpty()) {
      return;
    }
    nodes.sort(Comparator.comparingInt(MenuPermissionNode::getSortOrder));
    for (MenuPermissionNode node : nodes) {
      sortByOrder(node.getChildren());
    }
  }
}
