package org.base23.uaa.business.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.base23.uaa.core.domain.entity.MenuPermission;

@Mapper
public interface MenuPermissionMapper extends BaseMapper<MenuPermission> {

  List<MenuPermission> getPermissionsByRoleCode(@Param("roleCode") String roleCode);
}
