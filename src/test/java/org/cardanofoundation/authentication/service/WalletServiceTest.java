package org.cardanofoundation.authentication.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.cardanofoundation.authentication.model.entity.UserEntity;
import org.cardanofoundation.authentication.model.entity.WalletEntity;
import org.cardanofoundation.authentication.repository.WalletRepository;
import org.cardanofoundation.authentication.service.impl.WalletServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class WalletServiceTest {

  @InjectMocks
  private WalletServiceImpl walletService;

  @Mock
  private WalletRepository walletRepository;

  @Mock
  private PasswordEncoder encoder;

  @BeforeEach
  public void setUp() {
    ReflectionTestUtils.setField(walletService, "nonceExpirationMs", 360000L);
  }

  private final String NONCE = "12314340975656543";

  private final String ENCODE_NONCE = "$2a$10$qbpa.SGNi0y3Chv2ENMCT.5aTVwgm8oZesdt4xsDuyp2WztqHaY8u";

  @Test
  void whenUpdateNonce_returnResponse() {
    when(encoder.encode(NONCE)).thenReturn(ENCODE_NONCE);
    WalletEntity wallet = new WalletEntity();
    wallet.setNonce(NONCE);
    wallet.setNonceEncode(ENCODE_NONCE);
    when(walletRepository.save(any())).thenReturn(wallet);
    WalletEntity response = walletService.updateNonce(wallet);
    Assertions.assertNotNull(response);
  }

  @Test
  void whenSaveWallet_returnResponse() {
    when(encoder.encode(NONCE)).thenReturn(ENCODE_NONCE);
    UserEntity user = new UserEntity();
    WalletEntity wallet = WalletEntity.builder().user(user)
        .address("stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p").nonce(NONCE)
        .walletName("NAMI").build();
    when(walletRepository.save(any())).thenReturn(wallet);
    WalletEntity response = walletService.saveWallet(
        "stake1u80n7nvm3qlss9ls0krp5xh7sqxlazp8kz6n3fp5sgnul5cnxyg4p", user, "NAMI");
    Assertions.assertNotNull(response);
  }
}
