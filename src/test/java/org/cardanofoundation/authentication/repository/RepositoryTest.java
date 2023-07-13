package org.cardanofoundation.authentication.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.cardanofoundation.authentication.model.entity.BookMarkEntity;
import org.cardanofoundation.authentication.model.entity.PrivateNoteEntity;
import org.cardanofoundation.authentication.model.entity.RefreshTokenEntity;
import org.cardanofoundation.authentication.model.entity.RoleEntity;
import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.UserHistoryEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.model.enums.EBookMarkType;
import org.cardanofoundation.authentication.model.enums.ENetworkType;
import org.cardanofoundation.authentication.model.enums.ERole;
import org.cardanofoundation.authentication.model.enums.EStatus;
import org.cardanofoundation.authentication.model.enums.EUserAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@ActiveProfiles("dev")
class RepositoryTest {

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

  @Autowired
  private BookMarkRepository bookMarkRepository;

  @Autowired
  private PrivateNoteRepository privateNoteRepository;

  private final String EMAIL = "test.30.04@gmail.com";

  private final String ADDRESS = "123456789QWERTY";

  @Test
  void whenInsertUser() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userTest = userRepository.save(user);
    Assertions.assertNotNull(userTest);
  }

  @Test
  void whenFindByEmail() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    userRepository.save(user);
    Optional<UserEntity> userOpt = userRepository.findByEmail(EMAIL);
    Assertions.assertTrue(userOpt.isPresent());
  }

  @Test
  void whenExistsByEmail() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    userRepository.save(user);
    Boolean isExist = userRepository.existsByEmail(EMAIL);
    Assertions.assertTrue(isExist);
  }

  @Test
  void whenFindByEmailAndStatus() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .status(
            EStatus.ACTIVE)
        .isDeleted(false).build();
    userRepository.save(user);
    Optional<UserEntity> userOpt = userRepository.findByEmailAndStatus(EMAIL,
        EStatus.ACTIVE);
    Assertions.assertTrue(userOpt.isPresent());
  }

  @Test
  void whenFindUserByAddress() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName("NAMI")
        .address(ADDRESS).nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .user(user1).build();
    walletRepository.save(wallet);
    UserEntity userTest = userRepository.findUserByAddress(ADDRESS).orElse(null);
    Assertions.assertNotNull(userTest);
  }

  @Test
  void whenInsertWallet() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName("NAMI")
        .address(ADDRESS).nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .user(user1).build();
    WalletEntity walletTest = walletRepository.save(wallet);
    Assertions.assertNotNull(walletTest);
  }

  @Test
  void whenFindWalletByAddress() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName("NAMI")
        .address(ADDRESS).nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .user(user1).build();
    walletRepository.save(wallet);
    Optional<WalletEntity> walletOpt = walletRepository.findWalletByAddress(ADDRESS);
    Assertions.assertTrue(walletOpt.isPresent());
  }

  @Test
  void whenFindAddressByUserId() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity user1 = userRepository.save(user);
    WalletEntity wallet = WalletEntity.builder().walletName("NAMI")
        .address(ADDRESS).nonce("8890825581941064700")
        .nonceEncode("$2a$10$lPoc5.JX3s78BbK14Fams.Nqz0hQIDmFDFSsAI4.zR3Nhy0alCPMq")
        .expiryDateNonce(Instant.now()).networkId("1").networkType(ENetworkType.MAIN_NET)
        .user(user1).build();
    walletRepository.save(wallet);
    String address = walletRepository.findAddressByUserId(user1.getId());
    Assertions.assertEquals(ADDRESS, address);
  }

  @Test
  void whenInsertUserHistory() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    UserHistoryEntity userHistory = new UserHistoryEntity();
    userHistory.setUserAction(EUserAction.LOGIN);
    userHistory.setActionTime(Instant.now());
    userHistory.setUser(userInsert);
    UserHistoryEntity userHistoryTest = userHistoryRepository.save(userHistory);
    Assertions.assertNotNull(userHistoryTest);
  }

  @Test
  void whenInsertRefreshToken() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .user(userInsert).expiryDate(Instant.now()).build();
    RefreshTokenEntity refreshTokenTest = refreshTokenRepository.save(refreshToken);
    Assertions.assertNotNull(refreshTokenTest);
  }

  @Test
  void whenFindRefreshTokenByToken() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .user(userInsert).expiryDate(Instant.now()).build();
    refreshTokenRepository.save(refreshToken);
    Optional<RefreshTokenEntity> refreshTokenOpt = refreshTokenRepository.findByToken(
        "123qsf34fwf45fwdeaf5gsfc");
    Assertions.assertTrue(refreshTokenOpt.isPresent());
  }

  @Test
  void whenFindAllRefreshTokenByUserId() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userInsert = userRepository.save(user);
    RefreshTokenEntity refreshToken = RefreshTokenEntity.builder().token("123qsf34fwf45fwdeaf5gsfc")
        .user(userInsert).expiryDate(Instant.now()).build();
    refreshTokenRepository.save(refreshToken);
    List<RefreshTokenEntity> refreshTokens = refreshTokenRepository.findALlByUserId(
        userInsert.getId());
    Assertions.assertEquals(1, refreshTokens.size());
  }

  @Test
  void whenFindRoleByName() {
    RoleEntity role = new RoleEntity();
    role.setName(ERole.ROLE_USER);
    role.setDescription("insert role");
    roleRepository.save(role);
    Optional<RoleEntity> roleOpt = roleRepository.findByName(ERole.ROLE_USER);
    Assertions.assertTrue(roleOpt.isPresent());
  }

  @Test
  void whenInsertBookmark() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userTest = userRepository.save(user);
    BookMarkEntity bookMark = BookMarkEntity.builder().type(EBookMarkType.POOL).user(userTest)
        .keyword("test.30.04").network(ENetworkType.MAIN_NET).build();
    BookMarkEntity bookMarkTest = bookMarkRepository.save(bookMark);
    Assertions.assertNotNull(bookMarkTest);
  }

  @Test
  void whenInsertPrivateNote() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userTest = userRepository.save(user);
    PrivateNoteEntity privateNote = PrivateNoteEntity.builder().user(userTest).txHash("TestTxHash")
        .note("Test").network(ENetworkType.MAIN_NET).build();
    PrivateNoteEntity privateNoteTest = privateNoteRepository.save(privateNote);
    Assertions.assertNotNull(privateNoteTest);
  }
}
