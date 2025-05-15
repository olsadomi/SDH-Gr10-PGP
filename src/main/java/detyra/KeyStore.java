package detyra;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class KeyStore {
    private static final Map<String, PublicKey> publicKeys = new HashMap<>();
    private static final Map<String, PrivateKey> privateKeys = new HashMap<>();

    public static PublicKey getPublicKey(String username) {
        return publicKeys.get(username);
    }

    public static PrivateKey getPrivateKey(String username) {
        return privateKeys.get(username);
    }

    public static void addKeys(String username, PublicKey publicKey, PrivateKey privateKey) {
        publicKeys.put(username, publicKey);
        privateKeys.put(username, privateKey);
    }




}
