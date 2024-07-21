package org.base23.uaa.business.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.base23.uaa.core.domain.dto.ResetPasswordParam;
import org.base23.uaa.core.domain.dto.UpdateProfileParam;
import org.base23.uaa.core.domain.dto.UserQueryBuilder;
import org.base23.uaa.core.domain.entity.User;
import org.base23.uaa.core.domain.vo.UserItemVO;

@Mapper
public interface UserMapper extends BaseMapper<User> {

  Page<UserItemVO> pageQueryUser(Page<UserItemVO> page, @Param("param") UserQueryBuilder param);

  void updateProfile(UpdateProfileParam param);

  void resetPassword(ResetPasswordParam param);
}
