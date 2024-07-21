package org.base23.uaa.core.domain.convertor;

import org.base23.uaa.core.domain.entity.Profile;
import org.base23.uaa.core.domain.vo.ProfileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvertor {

  UserConvertor INSTANCE = Mappers.getMapper(UserConvertor.class);

  ProfileVO toProfileVO(Profile profile);

}
