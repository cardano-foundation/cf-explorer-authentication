package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.enums.ERole;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.cardanofoundation.authentication.model.request.EditUserRequest;
import org.cardanofoundation.authentication.model.request.auth.SignUpRequest;
import org.cardanofoundation.authentication.model.response.UserInfoResponse;
import org.cardanofoundation.authentication.model.response.UserResponse;
import org.cardanofoundation.authentication.provider.JwtProvider;
import org.cardanofoundation.authentication.repository.BookMarkRepository;
import org.cardanofoundation.authentication.repository.PrivateNoteRepository;
import org.cardanofoundation.authentication.repository.RoleRepository;
import org.cardanofoundation.authentication.repository.UserHistoryRepository;
import org.cardanofoundation.authentication.repository.UserRepository;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.impl.UserServiceImpl;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private WalletRepository walletRepository;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private BookMarkRepository bookMarkRepository;

  @Mock
  private PrivateNoteRepository noteRepository;

  @Mock
  private UserHistoryRepository userHistoryRepository;

  @Mock
  private RoleRepository roleRepository;

  private final String ADDRESS_WALLET = "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p";

  private final String NONCE = "54280243712699685362011534972";

  private final String NONCE_ENCODE = "$2a$10$4egM5wLBPz7zpVlxFL8CguVLQacZ8I6yRhxW1iqUkMVEFUHK2s7Oa";

  @Test
  void whenLoadUserByUsername_userIsNotExist_throwException() {
    when(userRepository.findByEmailAndStatus(ADDRESS_WALLET, EStatus.ACTIVE)).thenReturn(
        Optional.empty());
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET)).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      userService.loadUserByUsername(ADDRESS_WALLET);
    });
    String expectedCode = CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenLoadUserByUsername_userIsExist_returnResponse() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    WalletEntity wallet = WalletEntity.builder().address(ADDRESS_WALLET).nonce(NONCE)
        .nonceEncode(NONCE_ENCODE).user(user).walletName("NAMI").build();
    when(userRepository.findByEmailAndStatus(ADDRESS_WALLET, EStatus.ACTIVE)).thenReturn(
        Optional.empty());
    when(walletRepository.findWalletByAddress(ADDRESS_WALLET)).thenReturn(Optional.of(wallet));
    UserDetails response = userService.loadUserByUsername(ADDRESS_WALLET);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenEditAvatar_returnResponse() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    MockMultipartFile avatar = new MockMultipartFile("file", "hello.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    when(userRepository.findUserByAddress(ADDRESS_WALLET)).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);
    UserResponse response = userService.editAvatar(avatar, httpServletRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenInfoUser_returnResponse() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    user.setStakeKey(ADDRESS_WALLET);
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    when(userRepository.findUserByAddress(ADDRESS_WALLET)).thenReturn(Optional.of(user));
    when(bookMarkRepository.getCountBookMarkByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(
        1000);
    when(noteRepository.getCountNoteByUser(1L, ENetworkType.MAIN_NET.name())).thenReturn(1000);
    UserHistoryEntity userHistory = UserHistoryEntity.builder().userAction(EUserAction.CREATED)
        .actionTime(
            Instant.now()).build();
    when(userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(user,
        EUserAction.LOGIN)).thenReturn(null);
    when(userHistoryRepository.findFirstByUserAndUserActionOrderByActionTimeDesc(user,
        EUserAction.CREATED)).thenReturn(userHistory);
    when(userRepository.save(any())).thenReturn(user);
    when(walletRepository.findAddressByUserId(1L)).thenReturn(null);
    UserInfoResponse response = userService.infoUser(httpServletRequest,
        ENetworkType.MAIN_NET.name());
    Assertions.assertNotNull(response);
  }

  @Test
  void whenSaveUser_returnResponse() {
    SignUpRequest signUpRequest = new SignUpRequest();
    signUpRequest.setEmail("test@gmail.com");
    signUpRequest.setPassword("@nhTest12");
    RoleEntity role = new RoleEntity();
    role.setName(ERole.ROLE_USER);
    role.setId(1L);
    Set<RoleEntity> roles = new HashSet<>();
    roles.add(role);
    UserEntity user = UserEntity.builder().email("test@gmail.com").password("@nhTest12")
        .roles(roles).status(EStatus.PENDING).build();
    when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
    when(userRepository.save(any())).thenReturn(user);
    UserEntity response = userService.saveUser(signUpRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenFindByAccountId_byEmail_returnResponse() {
    UserEntity user = UserEntity.builder().email("test@gmail.com").password("@nhTest12")
        .status(EStatus.ACTIVE).build();
    when(userRepository.findUserByAddress("test@gmail.com")).thenReturn(Optional.empty());
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    UserEntity response = userService.findByAccountId("test@gmail.com");
    Assertions.assertNotNull(response);
  }

  @Test
  void whenCheckExistEmail_returnResponse() {
    when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);
    boolean response = userService.checkExistEmail("test@gmail.com");
    Assertions.assertTrue(response);
  }

  @Test
  void whenActiveUser_userIsNotExist_throwException() {
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      userService.activeUser("test@gmail.com");
    });
    String expectedCode = CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenActiveUser_userIsExist_returnResponse() {
    UserEntity user = UserEntity.builder().email("test@gmail.com").password("@nhTest12")
        .status(EStatus.PENDING).build();
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    userService.activeUser("test@gmail.com");
    verify(userRepository).save(any());
  }

  @Test
  void whenFindByEmailAndStatus_userIsNotExist_throwException() {
    when(userRepository.findByEmailAndStatus("test@gmail.com", EStatus.ACTIVE)).thenReturn(
        Optional.empty());
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      userService.findByEmailAndStatus("test@gmail.com", EStatus.ACTIVE);
    });
    String expectedCode = CommonErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenFindByEmailAndStatus_userIsExist_returnResponse() {
    UserEntity user = UserEntity.builder().email("test@gmail.com").password("@nhTest12")
        .status(EStatus.ACTIVE).build();
    when(userRepository.findByEmailAndStatus("test@gmail.com", EStatus.ACTIVE)).thenReturn(
        Optional.of(user));
    UserEntity response = userService.findByEmailAndStatus("test@gmail.com", EStatus.ACTIVE);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenEditUser_emailIsExist_throwException() {
    EditUserRequest editUserRequest = new EditUserRequest();
    editUserRequest.setEmail("test@gmail.com");
    UserEntity user = UserEntity.builder().status(EStatus.ACTIVE).build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    when(userRepository.findUserByAddress(ADDRESS_WALLET)).thenReturn(Optional.of(user));
    when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);
    BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> {
      userService.editUser(editUserRequest, httpServletRequest);
    });
    String expectedCode = CommonErrorCode.EMAIL_IS_ALREADY_EXIST.getServiceErrorCode();
    String actualCode = exception.getErrorCode();
    Assertions.assertEquals(expectedCode, actualCode);
  }

  @Test
  void whenEditUser_emailIsNotExist_returnResponse() {
    EditUserRequest editUserRequest = new EditUserRequest();
    editUserRequest.setEmail("test@gmail.com");
    UserEntity user = UserEntity.builder().status(EStatus.ACTIVE).build();
    user.setId(1L);
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn(ADDRESS_WALLET);
    when(userRepository.findUserByAddress(ADDRESS_WALLET)).thenReturn(Optional.of(user));
    when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
    when(walletRepository.findAddressByUserId(1L)).thenReturn(ADDRESS_WALLET);
    when(userRepository.save(any())).thenReturn(user);
    UserResponse response = userService.editUser(editUserRequest, httpServletRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenEditUser_addStakeKey_throwException() {
    EditUserRequest editUserRequest = new EditUserRequest();
    editUserRequest.setAddress(ADDRESS_WALLET);
    UserEntity user = UserEntity.builder().status(EStatus.ACTIVE).build();
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    when(jwtProvider.getAccountIdFromJwtToken(httpServletRequest)).thenReturn("test@gmail.com");
    when(userRepository.findUserByAddress("test@gmail.com")).thenReturn(Optional.empty());
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);
    UserResponse response = userService.editUser(editUserRequest, httpServletRequest);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenSaveUser_Wallet_returnResponse() {
    RoleEntity role = new RoleEntity();
    role.setName(ERole.ROLE_USER);
    role.setId(1L);
    Set<RoleEntity> roles = new HashSet<>();
    roles.add(role);
    UserEntity user = UserEntity.builder().roles(roles).status(EStatus.ACTIVE).build();
    when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
    when(userRepository.save(any())).thenReturn(user);
    UserEntity response = userService.saveUser(ADDRESS_WALLET);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenCheckExistEmailAndStatus_returnResponse() {
    when(userRepository.existsByEmailAndStatus("test@gmail.com", EStatus.ACTIVE)).thenReturn(true);
    boolean response = userService.checkExistEmailAndStatus("test@gmail.com", EStatus.ACTIVE);
    Assertions.assertTrue(response);
  }
}
