package com.sotatek.authservice.util;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import com.bloxbean.cardano.client.util.HexUtil;
import com.sotatek.cardanocommonapi.exceptions.BusinessException;
import com.sotatek.cardanocommonapi.exceptions.enums.CommonErrorCode;
import java.security.SecureRandom;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class NonceUtils {

  private static final SecureRandom random = new SecureRandom();

  /*
   * @author: phuc.nguyen5
   * @since: 21/10/2022
   * description: gen nonce value using random function
   * @update: 04/11/2022
   */
  public static String createNonce() {
    Integer nonce1 = Math.abs(random.nextInt(Integer.MAX_VALUE));
    Integer nonce2 = Math.abs(random.nextInt(Integer.MAX_VALUE));
    Integer nonce3 = Math.abs(random.nextInt(Integer.MAX_VALUE));
    return nonce1 + nonce2 + String.valueOf(nonce3);
  }

  /*
   * @author: phuc.nguyen5
   * @since: 07/11/2022
   * description: get nonce value from signature
   * @update:
   */
  public static String getNonceFromSignature(String signature) {
    List<DataItem> itemList = null;
    try {
      itemList = CborDecoder.decode(HexUtil.decodeHexString(signature));
    } catch (CborException e) {
      log.error("Exception decode cbor: " + e.getMessage());
      throw new BusinessException(CommonErrorCode.UNKNOWN_ERROR);
    }
    List<DataItem> topArray = ((Array) itemList.get(0)).getDataItems();
    ByteString messageToSign = (ByteString) topArray.get(2);
    byte[] message = messageToSign.getBytes();
    return new String(message);
  }
}
