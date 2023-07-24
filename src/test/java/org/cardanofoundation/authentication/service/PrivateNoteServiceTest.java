package org.cardanofoundation.authentication.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.cardanofoundation.authentication.constant.CommonConstant;
import org.cardanofoundation.authentication.model.entity.PrivateNoteEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.request.note.PrivateNoteRequest;
import org.cardanofoundation.authentication.model.response.MessageResponse;
import org.cardanofoundation.authentication.model.response.PrivateNoteResponse;
import org.cardanofoundation.authentication.model.response.base.BasePageResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.repository.PrivateNoteRepository;
import org.cardanofoundation.authentication.service.impl.PrivateNoteServiceImpl;
import org.cardanofoundation.explorer.common.exceptions.BusinessException;
import org.cardanofoundation.explorer.common.exceptions.enums.CommonErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PrivateNoteServiceTest {

  @InjectMocks
  private PrivateNoteServiceImpl privateNoteService;

  @Mock
  private PrivateNoteRepository noteRepository;

  @Mock
  private UserService userService;

  @Mock
  private JwtProvider jwtProvider;

  private final String ADDRESS_WALLET = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  private final String TX_HASH = "a3d6f2627a56fe7921eeda546abfe164321881d41549b7f2fbf09ea0b718d758";

  @Test
  void whenAddPrivateNote_IsExist_throwException() {
    PrivateNoteRequest privateNoteRequest = new PrivateNoteRequest();
    privateNoteRequest.setTxHash(TX_HASH);
    privateNoteRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(noteRepository.checkExistNote(1L, TX_HASH, ENetworkType.MAIN_NET.name())).thenReturn(1L);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      privateNoteService.addPrivateNote(privateNoteRequest, httpServletRequest);
    });
    String expectedCode = CommonErrorCode.PRIVATE_NOTE_IS_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenAddPrivateNote_IsNotExistAndExceedLimit_throwException() {
    PrivateNoteRequest privateNoteRequest = new PrivateNoteRequest();
    privateNoteRequest.setTxHash(TX_HASH);
    privateNoteRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(noteRepository.checkExistNote(1L, TX_HASH, ENetworkType.MAIN_NET.name())).thenReturn(null);
    when(noteRepository.getCountNoteByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(2500);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      privateNoteService.addPrivateNote(privateNoteRequest, httpServletRequest);
    });
    String expectedCode = CommonErrorCode.LIMIT_NOTE_IS_2000.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenAddPrivateNote_returnResponseSuccess() {
    PrivateNoteRequest privateNoteRequest = new PrivateNoteRequest();
    privateNoteRequest.setTxHash(TX_HASH);
    privateNoteRequest.setNetwork(ENetworkType.MAIN_NET.name());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(noteRepository.checkExistNote(1L, TX_HASH, ENetworkType.MAIN_NET.name())).thenReturn(null);
    when(noteRepository.getCountNoteByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(1500);
    PrivateNoteEntity privateNote = PrivateNoteEntity.builder().txHash(TX_HASH)
        .network(ENetworkType.MAIN_NET.name()).build();
    privateNote.setUser(user);
    when(noteRepository.save(privateNote)).thenReturn(privateNote);
    MessageResponse response = privateNoteService.addPrivateNote(privateNoteRequest,
        httpServletRequest);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenFindAllNote_IsNotExist_returnResponseEmpty() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    when(noteRepository.findAllNote(1L, ENetworkType.MAIN_NET.name(),
        PageRequest.of(0, 10))).thenReturn(
        Page.empty());
    BasePageResponse<PrivateNoteResponse> response = privateNoteService.findAllNote(
        httpServletRequest, ENetworkType.MAIN_NET.name(), PageRequest.of(0, 10));
    Assertions.assertEquals(0, response.getTotalItems());
  }

  @Test
  void whenFindAllNote_IsExist_returnResponse() {
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().build();
    user.setId(1L);
    when(userService.findByAccountId(ADDRESS_WALLET)).thenReturn(user);
    PrivateNoteEntity privateNote = PrivateNoteEntity.builder().txHash(TX_HASH)
        .network(ENetworkType.MAIN_NET.name()).build();
    privateNote.setUser(user);
    when(noteRepository.findAllNote(1L, ENetworkType.MAIN_NET.name(),
        PageRequest.of(0, 10))).thenReturn(
        new PageImpl<>(List.of(privateNote)));
    BasePageResponse<PrivateNoteResponse> response = privateNoteService.findAllNote(
        httpServletRequest, ENetworkType.MAIN_NET.name(), PageRequest.of(0, 10));
    Assertions.assertEquals(1, response.getTotalItems());
  }

  @Test
  void whenDeleteById_IsNotExist_throwException() {
    when(noteRepository.findById(1L)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      privateNoteService.deleteById(1L);
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenDeleteById_IsExist_returnResponse() {
    PrivateNoteEntity privateNote = PrivateNoteEntity.builder().txHash(TX_HASH)
        .network(ENetworkType.MAIN_NET.name()).build();
    privateNote.setId(1L);
    when(noteRepository.findById(1L)).thenReturn(Optional.of(privateNote));
    doNothing().when(noteRepository).delete(privateNote);
    MessageResponse response = privateNoteService.deleteById(1L);
    String expectedCode = CommonConstant.CODE_SUCCESS;
    Assertions.assertEquals(expectedCode, response.getCode());
  }

  @Test
  void whenEditById_IsNotExist_throwException() {
    when(noteRepository.findById(1L)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      privateNoteService.editById(1L, "note");
    });
    String expectedCode = CommonErrorCode.UNKNOWN_ERROR.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }
}
