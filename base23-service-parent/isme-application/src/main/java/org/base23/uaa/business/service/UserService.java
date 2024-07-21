package org.base23.uaa.business.service;

import org.base23.commons.domain.dto.PageResult;
import org.base23.uaa.core.domain.dto.AddUserParam;
import org.base23.uaa.core.domain.dto.ResetPasswordParam;
import org.base23.uaa.core.domain.dto.UpdatePasswordParam;
import org.base23.uaa.core.domain.dto.UpdateProfileParam;
import org.base23.uaa.core.domain.dto.UpdateUserParam;
import org.base23.uaa.core.domain.dto.UserQueryBuilder;
import org.base23.uaa.core.domain.entity.User;
import org.base23.uaa.core.domain.vo.UserDetailVO;
import org.base23.uaa.core.domain.vo.UserItemVO;

public interface UserService {

  User getUserByUsername(String username);

  UserDetailVO getCurrentUserDetail();

  PageResult<UserItemVO> pageQueryUsers(UserQueryBuilder queryBuilder);

  void addUser(AddUserParam param);

  void updateProfile(UpdateProfileParam param);

  void updatePassword(UpdatePasswordParam param);

  int updateUser(UpdateUserParam param);

  void resetPassword(ResetPasswordParam param);

  User getUserById(long userId);
}
