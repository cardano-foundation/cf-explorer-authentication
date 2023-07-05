package org.cardanofoundation.authentication.signature;

import static org.junit.jupiter.api.Assertions.assertTrue;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.Map;
import co.nstant.in.cbor.model.UnicodeString;
import co.nstant.in.cbor.model.UnsignedInteger;
import com.bloxbean.cardano.client.crypto.CryptoException;
import com.bloxbean.cardano.client.transaction.util.CborSerializationUtil;
import com.bloxbean.cardano.client.util.HexUtil;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

@Log4j2
@Profile("Test")
public class VerifySignatureTest {

  public static final String PERSONAL_MESSAGE_PREFIX = "\u0019Ethereum Signed Message:\n";

  private static final EdDSAParameterSpec spec = EdDSANamedCurveTable.getByName(
      EdDSANamedCurveTable.ED_25519);

  @Test
  public void testRecoverMessageAndAddressFromSignatureCardanoWallet() throws Exception {
    String signMsgInHex = "845869a30127045820674d11e432450118d70ea78673d5e31d5cc1aec63de0ff6284784876544be3406761646472657373583901d2eb831c6cad4aba700eb35f86966fbeff19d077954430e32ce65e8da79a3abe84f4ce817fad066acc1435be2ffc6bd7dce2ec1cc6cca6cba166686173686564f44568656c6c6f5840a3b5acd99df5f3b5e4449c5a116078e9c0fcfc126a4d4e2f6a9565f40b0c77474cafd89845e768fae3f6eec0df4575fcfe7094672c8c02169d744b415c617609";
    List<DataItem> dataItemList = CborDecoder.decode(HexUtil.decodeHexString(signMsgInHex));
    List<DataItem> topArray = ((Array) dataItemList.get(0)).getDataItems();
    ByteString protectedHeaderBs = (ByteString) topArray.get(0);
    ByteString messageToSignBS = (ByteString) topArray.get(2);
    ByteString signatureArrayBS = (ByteString) topArray.get(3);
    byte[] message = messageToSignBS.getBytes();
    byte[] signature = signatureArrayBS.getBytes();
    log.warn(new String(message));
    List<DataItem> protectedHeaderMapDIList = CborDecoder.decode(protectedHeaderBs.getBytes());
    Map protectedHeaderMap = (Map) protectedHeaderMapDIList.get(0);
    ByteString publicKeyBS = (ByteString) protectedHeaderMap.get(new UnsignedInteger(4));
    byte[] publicKeyBytes = publicKeyBS.getBytes();
    ByteString addressBS = (ByteString) protectedHeaderMap.get(new UnicodeString("address"));
    log.warn("Address >> " + HexUtil.encodeHexString(addressBS.getBytes()));
    Array sigStructArray = new Array();
    sigStructArray.add(new UnicodeString("Signature1"));
    sigStructArray.add(protectedHeaderBs);
    sigStructArray.add(new ByteString(new byte[0]));
    sigStructArray.add(messageToSignBS);
    byte[] messageToVerify = CborSerializationUtil.serialize(sigStructArray); //serialize
    boolean verified = verify(messageToVerify, signature, publicKeyBytes);
    assertTrue(verified);
  }

  public boolean verify(byte[] message, byte[] signatureBytes, byte[] publicKeyBytes) {
    try {
      net.i2p.crypto.eddsa.EdDSAPublicKey publicKey = new net.i2p.crypto.eddsa.EdDSAPublicKey(
          new EdDSAPublicKeySpec(publicKeyBytes, spec));
      Signature signature = new EdDSAEngine(MessageDigest.getInstance(spec.getHashAlgorithm()));
      signature.initVerify(publicKey);
      signature.setParameter(EdDSAEngine.ONE_SHOT_MODE);
      signature.update(message);
      return signature.verify(signatureBytes);
    } catch (Exception e) {
      throw new CryptoException("Extended signing error", e);
    }
  }
}
