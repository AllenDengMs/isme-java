package org.base23.uaa.core.domain.convertor;

import org.base23.uaa.core.domain.dto.MenuPermissionNode;
import org.base23.uaa.core.domain.entity.MenuPermission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuPermissionConvertor {

  MenuPermissionConvertor INSTANCE = Mappers.getMapper(MenuPermissionConvertor.class);

  MenuPermissionNode toNode(MenuPermission menuPermission);
}
