package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.request.admin.SignUpAdminRequest;
import com.sotatek.authservice.model.request.auth.SignUpRequest;
import com.sotatek.authservice.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserEntity requestToEntity(SignUpRequest request);

  UserResponse entityToResponse(UserEntity user);

  UserEntity requestAdminToEntity(SignUpAdminRequest request);
}
