package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.entity.BookMarkEntity;
import com.sotatek.authservice.model.request.bookmark.BookMarkRequest;
import com.sotatek.authservice.model.response.BookMarkResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMarkMapper {

  BookMarkMapper INSTANCE = Mappers.getMapper(BookMarkMapper.class);

  BookMarkEntity requestToEntity(BookMarkRequest request);

  List<BookMarkResponse> listEntityToResponse(List<BookMarkEntity> bookMarks);

  BookMarkResponse entityToResponse(BookMarkEntity bookMark);
}
