package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.request.auth.WalletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WalletMapper {

  WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

  WalletEntity requestToEntity(WalletRequest request);
}
