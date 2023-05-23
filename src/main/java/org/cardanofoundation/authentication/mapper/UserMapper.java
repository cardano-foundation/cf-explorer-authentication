package org.cardanofoundation.authentication.mapper;

import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserEntity requestToEntity(SignUpRequest request);

  UserResponse entityToResponse(UserEntity user);
}
