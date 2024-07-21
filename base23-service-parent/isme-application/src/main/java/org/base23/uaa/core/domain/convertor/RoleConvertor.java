package org.base23.uaa.core.domain.convertor;

import java.util.List;
import org.base23.uaa.core.domain.entity.Role;
import org.base23.uaa.core.domain.vo.RoleItemVO;
import org.base23.uaa.core.domain.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleConvertor {
  RoleConvertor INSTANCE = Mappers.getMapper(RoleConvertor.class);

  RoleVO toRoleVO(Role role);

  List<RoleVO> toRoleVOList(List<Role> role);

  List<RoleItemVO> toRoleItemVOList(List<Role> role);
}
