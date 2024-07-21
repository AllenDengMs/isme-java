package org.base23.i18n.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SystemI18nMapper {

  @Select("select i18n from t_system_i18n where `key` = #{key} and `language` = #{language}")
  String getI18nValue(@Param("key") String key, @Param("language") String language);

}
