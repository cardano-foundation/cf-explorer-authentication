package org.cardanofoundation.authentication.mapper;

import org.cardanofoundation.authentication.model.entity.PrivateNoteEntity;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {

  NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

  PrivateNoteEntity requestToEntity(PrivateNoteRequest request);

  List<PrivateNoteResponse> listEntityToResponse(List<PrivateNoteEntity> privateNotes);

  PrivateNoteResponse entityToResponse(PrivateNoteEntity privateNote);
}
