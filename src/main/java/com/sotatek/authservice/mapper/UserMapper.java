package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.request.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserEntity requestToEntity(SignUpRequest request);
}
