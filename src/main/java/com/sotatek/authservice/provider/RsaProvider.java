package com.sotatek.authservice.provider;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class RsaProvider {

  public PrivateKey getPrivateKey(String filename) throws Exception {
    byte[] bytes = readFile(filename);
    return getPrivateKey(bytes);
  }

  public String getPrivateKeyString(String filename) throws Exception {
    byte[] bytes = readFile(filename);
    return new String(bytes);
  }

  public PrivateKey getPrivateKey(byte[] bytes)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    bytes = Base64.getDecoder().decode(bytes);
    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
    KeyFactory factory = KeyFactory.getInstance("RSA");
    return factory.generatePrivate(spec);
  }

  private byte[] readFile(String filename) throws Exception {
    InputStream resourceAsStream = this.getClass().getResourceAsStream(filename);
    assert resourceAsStream != null;
    return resourceAsStream.readAllBytes();
  }
}
