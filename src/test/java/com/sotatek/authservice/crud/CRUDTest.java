package com.sotatek.authservice.crud;

import com.sotatek.authservice.model.entity.RefreshTokenEntity;
import com.sotatek.authservice.model.entity.RoleEntity;
import com.sotatek.authservice.model.entity.UserEntity;
import com.sotatek.authservice.model.entity.UserHistoryEntity;
import com.sotatek.authservice.model.entity.WalletEntity;
import com.sotatek.authservice.model.enums.ENetworkType;
import com.sotatek.authservice.model.enums.ERole;
import com.sotatek.authservice.model.enums.EUserAction;
import com.sotatek.authservice.model.enums.EWalletName;
import com.sotatek.authservice.repository.RefreshTokenRepository;
import com.sotatek.authservice.repository.RoleRepository;
import com.sotatek.authservice.repository.UserHistoryRepository;
import com.sotatek.authservice.repository.UserRepository;
import com.sotatek.authservice.repository.WalletRepository;
import java.math.BigDecimal;
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
  private UserHistoryRepository userHistoryRepository;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private WalletRepository walletRepository;

  @Test
  public void whenInsertUser() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity userTest = userRepository.save(user);
    Assertions.assertNotNull(userTest);
  }

  @Test
  public void whenFindByUsername() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    userRepository.save(user);
    Optional<UserEntity> userOpt = userRepository.findByUsernameAndIsDeletedFalse("test1");
    Assertions.assertTrue(userOpt.isPresent());
  }

  @Test
  public void whenExistsByUsername() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    userRepository.save(user);
    Boolean isExist = userRepository.existsByUsernameAndIsDeletedFalse("test1");
    Assertions.assertTrue(isExist);
  }

  @Test
  public void whenInsertWallet() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName(EWalletName.NAMI)
        .stakeAddress("123456789QWERTY").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .balanceAtLogin(BigDecimal.ONE).user(user1).build();
    WalletEntity walletTest = walletRepository.save(wallet);
    Assertions.assertNotNull(walletTest);
  }

  @Test
  public void whenFindByStakeAddress() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName(EWalletName.NAMI)
        .stakeAddress("123456789QWERTY").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .balanceAtLogin(BigDecimal.ONE).user(user1).build();
    walletRepository.save(wallet);
    Optional<WalletEntity> walletOpt = walletRepository.findByStakeAddressAndIsDeletedFalse("123456789QWERTY");
    Assertions.assertTrue(walletOpt.isPresent());
  }

  @Test
  public void whenExistsByStakeAddress() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName(EWalletName.NAMI)
        .stakeAddress("123456789QWERTY").nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .balanceAtLogin(BigDecimal.ONE).user(user1).build();
    walletRepository.save(wallet);
    Boolean isExist = walletRepository.existsByStakeAddressAndIsDeletedFalse("123456789QWERTY");
    Assertions.assertTrue(isExist);
  }

  @Test
  public void whenInsertUserHistory() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    UserHistoryEntity userHistory = new UserHistoryEntity();
    userHistory.setUserAction(EUserAction.LOGIN);
    userHistory.setActionTime(Instant.now());
    userHistory.setIsSuccess(true);
    userHistory.setUser(userInsert);
    UserHistoryEntity userHistoryTest = userHistoryRepository.save(userHistory);
    Assertions.assertNotNull(userHistoryTest);
  }

  @Test
  public void whenInsertRefreshToken() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .user(userInsert).expiryDate(Instant.now())
        .stakeAddress("123456789QWERTY").build();
    RefreshTokenEntity refreshTokenTest = refreshTokenRepository.save(refreshToken);
    Assertions.assertNotNull(refreshTokenTest);
  }

  @Test
  public void whenFindRefreshTokenByToken() {
    UserEntity user = UserEntity.builder().username("test1").email("test@gmail.com")
        .phone("0123456789").avatar(null).isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .user(userInsert).expiryDate(Instant.now())
        .stakeAddress("123456789QWERTY").build();
    refreshTokenRepository.save(refreshToken);
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
