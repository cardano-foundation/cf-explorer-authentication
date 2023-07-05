package org.cardanofoundation.authentication.crud;

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
import org.cardanofoundation.authentication.repository.BookMarkRepository;
import org.cardanofoundation.authentication.repository.PrivateNoteRepository;
import org.cardanofoundation.authentication.repository.RefreshTokenRepository;
import org.cardanofoundation.authentication.repository.RoleRepository;
import org.cardanofoundation.authentication.repository.UserHistoryRepository;
import org.cardanofoundation.authentication.repository.UserRepository;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;

@DataJpaTest
@Disabled
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JpaTest {

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
  public void whenInsertUser() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    UserEntity userTest = userRepository.save(user);
    Assertions.assertNotNull(userTest);
  }

  @Test
  public void whenFindByEmail() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    userRepository.save(user);
    Optional<UserEntity> userOpt = userRepository.findByEmail(EMAIL);
    Assertions.assertTrue(userOpt.isPresent());
  }

  @Test
  public void whenExistsByEmail() {
    UserEntity user = UserEntity.builder().email(EMAIL)
        .avatar(null)
        .isDeleted(false).build();
    userRepository.save(user);
    Boolean isExist = userRepository.existsByEmail(EMAIL);
    Assertions.assertTrue(isExist);
  }

  @Test
  public void whenFindByEmailAndStatus() {
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
  public void whenFindUserByAddress() {
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
  public void whenInsertWallet() {
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
  public void whenFindWalletByAddress() {
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
  public void whenFindAddressByUserId() {
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
  public void whenInsertUserHistory() {
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
  public void whenInsertRefreshToken() {
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
  public void whenFindRefreshTokenByToken() {
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
  public void whenFindAllRefreshTokenByUserId() {
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
  public void whenFindRoleByName() {
    Optional<RoleEntity> roleOpt = roleRepository.findByName(ERole.ROLE_USER);
    Assertions.assertTrue(roleOpt.isPresent());
  }

  @Test
  public void whenInsertBookmark() {
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
  public void whenInsertPrivateNote() {
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
