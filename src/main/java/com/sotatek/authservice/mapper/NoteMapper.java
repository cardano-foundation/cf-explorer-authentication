package com.sotatek.authservice.mapper;

import com.sotatek.authservice.model.entity.PrivateNoteEntity;
import com.sotatek.authservice.model.request.note.PrivateNoteRequest;
import com.sotatek.authservice.model.response.PrivateNoteResponse;
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
