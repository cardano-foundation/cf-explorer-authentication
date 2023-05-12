package org.cardanofoundation.authentication.provider;

import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class RsaProvider {

  public PrivateKey getPrivateKey(String filename) {
    byte[] bytes = readFile(filename);
    return getPrivateKey(bytes);
  }

  public PrivateKey getPrivateKey(byte[] bytes) {
    bytes = Base64.getDecoder().decode(bytes);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
    try {
      KeyFactory factory = KeyFactory.getInstance("RSA");
      return factory.generatePrivate(spec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new BusinessException(CommonErrorCode.UNKNOWN_ERROR);
    }
  }

  public PublicKey getPublicKey(String filename) {
    byte[] bytes = readFile(filename);
    return getPublicKey(bytes);
  }

  public PublicKey getPublicKey(byte[] bytes) {
    bytes = Base64.getDecoder().decode(bytes);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
    try {
      KeyFactory factory = KeyFactory.getInstance("RSA");
      return factory.generatePublic(spec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new BusinessException(CommonErrorCode.UNKNOWN_ERROR);
    }
  }

  private byte[] readFile(String filename) {
    try {
      InputStream resourceAsStream = this.getClass().getResourceAsStream(filename);
      assert resourceAsStream != null;
      return resourceAsStream.readAllBytes();
    } catch (IOException e) {
      throw new BusinessException(CommonErrorCode.UNKNOWN_ERROR);
    }
  }
}
