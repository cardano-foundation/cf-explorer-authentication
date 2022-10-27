package com.sotatek.authservice.crud;

import com.sotatek.authservice.model.entity.AuthenticationHistoryEntity;
import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.enums.ERole;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.repository.AuthenticationHistoryRepository;
import com.sotatek.authservice.repository.RefreshTokenRepository;
import com.sotatek.authservice.repository.RoleRepository;
import com.sotatek.authservice.repository.UserRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CRUDTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthenticationHistoryRepository authenticationHistoryRepository;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Test
  public void whenInsertUser() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    UserEntity userTest = userRepository.save(user);
    Assertions.assertNotNull(userTest);
  }

  @Test
  public void whenFindByUsername() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    userRepository.save(user);
    Optional<UserEntity> userOpt = userRepository.findByUsername("test1");
    Assertions.assertTrue(userOpt.isPresent());
  }

  @Test
  public void whenExistsByUsername() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    userRepository.save(user);
    Boolean isExist = userRepository.existsByUsername("test1");
    Assertions.assertTrue(isExist);
  }

  @Test
  public void whenExistsByPublicAddress() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    userRepository.save(user);
    Boolean isExist = userRepository.existsByPublicAddress("123456789QWERTY");
    Assertions.assertTrue(isExist);
  }

  @Test
  public void whenInsertAuthenticationHistory() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    AuthenticationHistoryEntity authenticationHistory = new AuthenticationHistoryEntity();
    authenticationHistory.setUserAction(EUserAction.LOGIN);
    authenticationHistory.setActionTime(Instant.now());
    authenticationHistory.setIsSuccess(true);
    authenticationHistory.setUser(userInsert);
    AuthenticationHistoryEntity authenticationHistoryTest = authenticationHistoryRepository.save(
        authenticationHistory);
    Assertions.assertNotNull(authenticationHistoryTest);
  }

  @Test
  public void whenInsertRefreshToken() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .accessToken("sadqwdqvdcdft45yg45vrsfdsf4wccdc").user(userInsert).expiryDate(Instant.now())
        .build();
    RefreshTokenEntity refreshTokenTest = refreshTokenRepository.save(refreshToken);
    Assertions.assertNotNull(refreshTokenTest);
  }

  @Test
  public void whenFindRefreshTokenByToken() {
    UserEntity user = UserEntity.builder().username("test1").publicAddress("123456789QWERTY")
        .email("test@gmail.com").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .accessToken("sadqwdqvdcdft45yg45vrsfdsf4wccdc").user(userInsert).expiryDate(Instant.now())
        .build();
    RefreshTokenEntity refreshTokenTest = refreshTokenRepository.save(refreshToken);
    Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenRepository.findByToken(
        "123qsf34fwf45fwdeaf5gsfc");
    Assertions.assertTrue(refreshTokenOpt.isPresent());
  }

  @Test
  public void whenFindRoleByName() {
    Optional<RoleEntity> roleOpt = roleRepository.findByName(ERole.ROLE_USER);
    Assertions.assertTrue(roleOpt.isPresent());
  }
}
