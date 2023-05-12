package org.cardanofoundation.authentication.mapper;

import org.cardanofoundation.authentication.model.entity.BookMarkEntity;
import org.cardanofoundation.authentication.model.request.bookmark.BookMarkRequest;
import org.cardanofoundation.authentication.model.response.BookMarkResponse;
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
