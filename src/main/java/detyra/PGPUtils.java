package detyra;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class PGPUtils {


    public static String decrypt(String encryptedMessage, PrivateKey privateKey, boolean formalMode) {
        try {
            if (!formalMode) {
                System.out.println("Encrypted message: " + encryptedMessage);
            }
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            String decryptedMessage = new String(decryptedBytes);
            if (!formalMode) {
                System.out.println("Decrypted message: " + decryptedMessage);
            }
            return decryptedMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sign(String message, PrivateKey privateKey, boolean formalMode) throws Exception {
        if (!formalMode) {
            System.out.println("Signing message: " + message);
        }
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(message.getBytes(StandardCharsets.UTF_8));
        byte[] signedBytes = signature.sign();
        String signedMessage = Base64.getEncoder().encodeToString(signedBytes);
        if (!formalMode) {
            System.out.println("Generated signature: " + signedMessage);
        }
        return signedMessage;
    }


}