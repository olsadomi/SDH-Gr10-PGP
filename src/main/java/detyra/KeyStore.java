package detyra;

import java.io.*;
import java.security.*;
import java.util.*;

public class KeyStore {
    private static final String PUBLIC_KEYS_FILE = "public_keys.ser";
    private static final String PRIVATE_KEYS_FILE = "private_keys.ser";

    private static Map<String, PublicKey> publicKeys = new HashMap<>();
    private static Map<String, PrivateKey> privateKeys = new HashMap<>();

    static {
        loadKeys();
    }

    public static void addKeys(String username, PublicKey publicKey, PrivateKey privateKey) {
        publicKeys.put(username, publicKey);
        privateKeys.put(username, privateKey);
        saveKeys();
    }

    public static PublicKey getPublicKey(String username) {
        return publicKeys.get(username);
    }

    public static PrivateKey getPrivateKey(String username) {
        return privateKeys.get(username);
    }

    public static boolean hasKeys(String username) {
        return publicKeys.containsKey(username) && privateKeys.containsKey(username);
    }

    private static void saveKeys() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEYS_FILE))) {
            oos.writeObject(publicKeys);
        } catch (IOException e) {
            System.out.println("Failed to save public keys: " + e.getMessage());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEYS_FILE))) {
            oos.writeObject(privateKeys);
        } catch (IOException e) {
            System.out.println("Failed to save private keys: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadKeys() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEYS_FILE))) {
            publicKeys = (Map<String, PublicKey>) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load public keys: " + e.getMessage());
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEYS_FILE))) {
            privateKeys = (Map<String, PrivateKey>) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load private keys: " + e.getMessage());
        }
    }
}