package org.base23.uaa.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.base23.commons.domain.dto.PageResult;
import org.base23.commons.utils.Times;
import org.base23.database.utils.Pages;
import org.base23.uaa.business.dao.mapper.UserMapper;
import org.base23.uaa.business.dao.repository.ProfileRepository;
import org.base23.uaa.business.dao.repository.UserRepository;
import org.base23.uaa.business.service.RoleService;
import org.base23.uaa.business.service.UserRoleService;
import org.base23.uaa.business.service.UserService;
import org.base23.uaa.core.domain.convertor.RoleConvertor;
import org.base23.uaa.core.domain.convertor.UserConvertor;
import org.base23.uaa.core.domain.dto.AddUserParam;
import org.base23.uaa.core.domain.dto.CurrentUser;
import org.base23.uaa.core.domain.dto.ResetPasswordParam;
import org.base23.uaa.core.domain.dto.UpdatePasswordParam;
import org.base23.uaa.core.domain.dto.UpdateProfileParam;
import org.base23.uaa.core.domain.dto.UpdateUserParam;
import org.base23.uaa.core.domain.dto.UserBindRoleParam;
import org.base23.uaa.core.domain.dto.UserQueryBuilder;
import org.base23.uaa.core.domain.entity.Profile;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.entity.User;
import org.base23.uaa.core.domain.entity.UserRole;
import org.base23.uaa.core.domain.vo.RoleVO;
import org.base23.uaa.core.domain.vo.UserDetailVO;
import org.base23.uaa.core.domain.vo.UserItemVO;
import org.base23.uaa.core.exception.UaaExceptions;
import org.base23.uaa.core.util.LoginSessionTool;
import org.base23.web.exception.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;

  private final PasswordEncoder passwordEncoder;

  private final RoleService roleService;

  private final UserRoleService userRoleService;
  private final UserRepository userRepository;
  private final ProfileRepository profileRepository;

  public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder,
      RoleService roleService, UserRoleService userRoleService,
      UserRepository userRepository,
      ProfileRepository profileRepository) {
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.roleService = roleService;
    this.userRoleService = userRoleService;
    this.userRepository = userRepository;
    this.profileRepository = profileRepository;
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.getByUsername(username);
  }

  @Override
  public UserDetailVO getCurrentUserDetail() {
    CurrentUser currentUser = LoginSessionTool.getCurrentUser();
    Long userId = currentUser.getUserId();

    UserDetailVO userDetail = new UserDetailVO();

    User user = userRepository.getById(userId);
    userDetail.setId(userId);
    userDetail.setUsername(user.getUsername());
    userDetail.setEnable(user.getEnable());
    userDetail.setCreateTime(user.getCreateTime());
    userDetail.setUpdateTime(user.getUpdateTime());

    userDetail.setProfile(
        UserConvertor.INSTANCE.toProfileVO(profileRepository.getByUserId(userId)));

    List<Role> roles = roleService.getRolesByUserId(userId);
    userDetail.setRoles(RoleConvertor.INSTANCE.toRoleVOList(roles));

    for (Role role : roles) {
      if (role.getCode().equals(currentUser.getRoleCode())) {
        userDetail.setCurrentRole((RoleConvertor.INSTANCE.toRoleVO(role)));
        break;
      }
    }

    return userDetail;
  }

  @Override
  public PageResult<UserItemVO> pageQueryUsers(UserQueryBuilder queryBuilder) {
    Page<UserItemVO> page = userMapper.pageQueryUser(Pages.of(queryBuilder), queryBuilder);

    // 批量获取 user-role对应关系
    List<Long> userIds = page.getRecords().stream().map(UserItemVO::getId)
        .toList();
    List<UserRole> userRoleList = roleService.queryUserRolesByUserIds(userIds);

    // 将对应关系转成List<RoleVO>
    Map<Long, List<RoleVO>> userRoleMap = new HashMap<>();
    Map<Long, Role> roleMap = roleService.findAllRoles().stream()
        .collect(Collectors.toMap(Role::getId, r -> r));
    for (UserRole userRole : userRoleList) {
      List<RoleVO> roles = userRoleMap.computeIfAbsent(userRole.getUserId(),
          k -> new ArrayList<>());
      Role role = roleMap.get(userRole.getRoleId());
      roles.add(RoleConvertor.INSTANCE.toRoleVO(role));
    }
    page.getRecords()
        .forEach(userItemVO -> userItemVO.setRoles(userRoleMap.get(userItemVO.getId())));
    return Pages.result(page);
  }

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  @Override
  public void addUser(AddUserParam param) {
    if (userRepository.existsByUsername(param.getUsername())) {
      Exceptions.throwException("${user.exists:用户已存在！}");
    }

    User user = new User();
    long now = Times.nowSecond();
    user.setCreateTime(now);
    user.setUpdateTime(now);
    user.setUsername(param.getUsername());
    user.setPassword(passwordEncoder.encode(param.getPassword()));
    user.setEnable(param.isEnable());
    userRepository.save(user);

    Profile profile = new Profile();
    profile.setUserId(user.getId());
    profile.setCreateTime(now);
    profile.setUpdateTime(now);
    profileRepository.save(profile);

    List<UserRole> userRoles = new ArrayList<>();
    for (Long roleId : param.getRoleIds()) {
      UserRole userRole = new UserRole();
      userRole.setUserId(user.getId());
      userRole.setRoleId(roleId);
      userRoles.add(userRole);
    }
    userRoleService.saveBatch(userRoles);
  }

  @Override
  public void updateProfile(UpdateProfileParam param) {
    param.setUpdateTime(Times.nowSecond());
    userMapper.updateProfile(param);
  }

  @Override
  public void updatePassword(UpdatePasswordParam param) {
    User user = userRepository.getById(param.getUserId());
    if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
      throw UaaExceptions.PASSWORD_INCORRECT.exception();
    }

    ResetPasswordParam resetPasswordParam = new ResetPasswordParam();
    resetPasswordParam.setId(param.getUserId());
    resetPasswordParam.setPassword(param.getNewPassword());
    resetPassword(resetPasswordParam);
  }

  @Transactional
  @Override
  public int updateUser(UpdateUserParam param) {
    // 数据校验
    long userId = param.getId();
    if (userId == User.USER_ID_SUPER) {
      if (param.getEnable() != null && Boolean.FALSE.equals(param.getEnable())) {
        // 禁止停用超管账号
        throw UaaExceptions.FORBID_STOP_SUPER_USER.exception();
      }
    }

    User userFromDB = userRepository.getById(userId);
    if (param.getUsername() != null) {
      userFromDB.setUsername(param.getUsername());
    }

    if (param.getEnable() != null) {
      userFromDB.setEnable(param.getEnable());
    }
    userRepository.updateById(userFromDB);

    if (param.getRoleIds() != null) {
      UserBindRoleParam userBindRoleParam = new UserBindRoleParam();
      userBindRoleParam.setUserId(userId);
      userBindRoleParam.setRoleIds(param.getRoleIds());
      userRoleService.userBindRoles(userBindRoleParam);
    }

    return 1;
  }

  @Override
  public void resetPassword(ResetPasswordParam param) {
    param.setUpdateTime(Times.nowSecond());
    param.setPassword(passwordEncoder.encode(param.getPassword()));
    userMapper.resetPassword(param);
  }

  @Override
  public User getUserById(long userId) {
    return userRepository.getById(userId);
  }

}
