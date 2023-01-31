package com.sotatek.authservice.service.impl;

import com.sotatek.authservice.constant.CommonConstant;
import com.sotatek.authservice.mapper.NoteMapper;
import com.sotatek.authservice.model.entity.PrivateNoteEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.request.note.PrivateNoteRequest;
import com.sotatek.authservice.model.response.MessageResponse;
import com.sotatek.authservice.model.response.PrivateNoteResponse;
import com.sotatek.authservice.model.response.base.BasePageResponse;
import com.sotatek.authservice.provider.JwtProvider;
import com.sotatek.authservice.repository.PrivateNoteRepository;
import com.sotatek.authservice.service.PrivateNoteService;
import com.sotatek.authservice.service.UserHistoryService;
import com.sotatek.authservice.service.UserService;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.time.Instant;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PrivateNoteServiceImpl implements PrivateNoteService {

  private final PrivateNoteRepository noteRepository;

  private final UserService userService;

  private final JwtProvider jwtProvider;

  private final UserHistoryService userHistoryService;

  private static final NoteMapper noteMapper = NoteMapper.INSTANCE;

  @Override
  public MessageResponse addPrivateNote(PrivateNoteRequest privateNoteRequest,
      HttpServletRequest httpServletRequest) {
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    UserEntity user = userService.findByUsername(username);
    if (Objects.nonNull(
        noteRepository.checkExistNote(user.getId(), privateNoteRequest.getTxHash()))) {
      throw new BusinessException(CommonErrorCode.PRIVATE_NOTE_IS_EXIST);
    }
    Integer countCurrent = noteRepository.getCountNoteByUser(user.getId());
    if (countCurrent >= CommonConstant.LIMIT_NOTE) {
      throw new BusinessException(CommonErrorCode.LIMIT_NOTE_IS_2000);
    }
    PrivateNoteEntity privateNote = noteMapper.requestToEntity(privateNoteRequest);
    privateNote.setUser(user);
    noteRepository.save(privateNote);
    userHistoryService.saveUserHistory(EUserAction.ADD_PRIVATE_NOTE, null, Instant.now(),
        privateNoteRequest.getTxHash(), user);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public BasePageResponse<PrivateNoteResponse> findAllNote(HttpServletRequest httpServletRequest,
      Pageable pageable) {
    BasePageResponse<PrivateNoteResponse> response = new BasePageResponse<>();
    String token = jwtProvider.parseJwt(httpServletRequest);
    String username = jwtProvider.getUserNameFromJwtToken(token);
    Page<PrivateNoteEntity> notePage = noteRepository.findAllNote(username, pageable);
    if (!notePage.isEmpty()) {
      response.setData(noteMapper.listEntityToResponse(notePage.getContent()));
    }
    response.setTotalItems(notePage.getTotalElements());
    return response;
  }

  @Override
  public MessageResponse deleteById(Long noteId) {
    PrivateNoteEntity privateNote = noteRepository.findById(noteId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    userHistoryService.saveUserHistory(EUserAction.REMOVE_PRIVATE_NOTE, null, Instant.now(),
        privateNote.getTxHash(), privateNote.getUser());
    noteRepository.delete(privateNote);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public PrivateNoteResponse editById(Long noteId, String note) {
    PrivateNoteEntity privateNote = noteRepository.findById(noteId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    privateNote.setNote(note);
    PrivateNoteEntity privateNoteEdit = noteRepository.save(privateNote);
    userHistoryService.saveUserHistory(EUserAction.EDIT_PRIVATE_NOTE, null, Instant.now(),
        privateNote.getTxHash(), privateNote.getUser());
    return noteMapper.entityToResponse(privateNoteEdit);
  }
}
