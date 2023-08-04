package org.cardanofoundation.authentication.util;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Transform the aws ses secret key into an SMTP password
 * Docs: https://docs.aws.amazon.com/ses/latest/dg/smtp-credentials.html
 * Java Code gist: https://gist.github.com/sysadmiral/c85c09e6534428a8bb578ab420a40207
 */
@Component
public class SesSmtpCredentialGenerator {

    private static final String MESSAGE = "SendRawEmail"; // Used to generate the HMAC signature. Do not modify.
    private static final byte VERSION = 0x02; // Version number. Do not modify.

    public String generateSMTPPassword(String key) {

        // Create an HMAC-SHA256 key from the raw bytes of the AWS secret access key.
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

        try {
            // Get an HMAC-SHA256 Mac instance and initialize it with the AWS secret access key.
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);

            // Compute the HMAC signature on the input data bytes.
            byte[] rawSignature = mac.doFinal(MESSAGE.getBytes());

            // Prepend the version number to the signature.
            byte[] rawSignatureWithVersion = new byte[rawSignature.length + 1];
            byte[] versionArray = {VERSION};
            System.arraycopy(versionArray, 0, rawSignatureWithVersion, 0, 1);
            System.arraycopy(rawSignature, 0, rawSignatureWithVersion, 1, rawSignature.length);

            // To get the final SMTP password, convert the HMAC signature to base 64.
            return Base64.getEncoder().encodeToString(rawSignatureWithVersion);
        } catch (Exception ex) {
            System.out.println("Error generating SMTP password: " + ex.getMessage());
            return null;
        }
    }
}