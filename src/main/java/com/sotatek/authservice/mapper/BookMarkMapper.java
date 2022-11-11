package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.dto.BookMarkDto;
import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMarkMapper {

  BookMarkMapper INSTANCE = Mappers.getMapper(BookMarkMapper.class);

  BookMarkEntity requestToEntity(BookMarkRequest request);

  List<BookMarkDto> listEntityToDto(List<BookMarkEntity> bookMarks);
}
