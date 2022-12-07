package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.response.ActivityLogResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserHistoryMapper {

  UserHistoryMapper INSTANCE = Mappers.getMapper(UserHistoryMapper.class);

  List<ActivityLogResponse> listEntityToResponse(List<UserHistoryEntity> historyEntityList);
}
