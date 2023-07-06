package org.cardanofoundation.authentication.service.impl;

import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.mapper.NoteMapper;
import org.cardanofoundation.authentication.model.entity.PrivateNoteEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.repository.PrivateNoteRepository;
import org.cardanofoundation.authentication.service.PrivateNoteService;
import org.cardanofoundation.authentication.service.UserService;
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

  private static final NoteMapper noteMapper = NoteMapper.INSTANCE;

  @Override
  public MessageResponse addPrivateNote(PrivateNoteRequest privateNoteRequest,
      HttpServletRequest httpServletRequest) {
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByAccountId(accountId);
    if (Objects.nonNull(
        noteRepository.checkExistNote(user.getId(), privateNoteRequest.getTxHash(),
            privateNoteRequest.getNetwork()))) {
      throw new BusinessException(CommonErrorCode.PRIVATE_NOTE_IS_EXIST);
    }
    Integer countCurrent = noteRepository.getCountNoteByUser(user.getId(),
        privateNoteRequest.getNetwork());
    if (countCurrent >= CommonConstant.LIMIT_NOTE) {
      throw new BusinessException(CommonErrorCode.LIMIT_NOTE_IS_2000);
    }
    PrivateNoteEntity privateNote = noteMapper.requestToEntity(privateNoteRequest);
    privateNote.setUser(user);
    noteRepository.save(privateNote);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public BasePageResponse<PrivateNoteResponse> findAllNote(HttpServletRequest httpServletRequest,
      String network,
      Pageable pageable) {
    BasePageResponse<PrivateNoteResponse> response = new BasePageResponse<>();
    String accountId = jwtProvider.getAccountIdFromJwtToken(httpServletRequest);
    UserEntity user = userService.findByAccountId(accountId);
    Page<PrivateNoteEntity> notePage = noteRepository.findAllNote(user.getId(), network, pageable);
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
    noteRepository.delete(privateNote);
    return new MessageResponse(CommonConstant.CODE_SUCCESS, CommonConstant.RESPONSE_SUCCESS);
  }

  @Override
  public PrivateNoteResponse editById(Long noteId, String note) {
    PrivateNoteEntity privateNote = noteRepository.findById(noteId)
        .orElseThrow(() -> new BusinessException(CommonErrorCode.UNKNOWN_ERROR));
    privateNote.setNote(note);
    PrivateNoteEntity privateNoteEdit = noteRepository.save(privateNote);
    return noteMapper.entityToResponse(privateNoteEdit);
  }
}
