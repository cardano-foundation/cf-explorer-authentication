package org.cardanofoundation.authentication.mapper;

import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.request.auth.WalletRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WalletMapper {

  WalletMapper INSTANCE = Mappers.getMapper(WalletMapper.class);

  WalletEntity requestToEntity(WalletRequest request);
}
