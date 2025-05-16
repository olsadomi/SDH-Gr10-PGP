package detyra;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EmailClient {
    private String username;
    private EmailServer server;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public EmailClient(String username, EmailServer server) {
        this.username = username;
        this.server = server;
        System.out.println("\nClient:");
        System.out.println("Welcome to the PGP Email Client!");
        System.out.print("Generating PGP key pair...");

        try {
            if (KeyStore.hasKeys(username)) {
                this.privateKey = KeyStore.getPrivateKey(username);
                this.publicKey = KeyStore.getPublicKey(username);
            } else {
                KeyPair keyPair = KeyGenerator.generateKeyPair();
                this.privateKey = keyPair.getPrivate();
                this.publicKey = keyPair.getPublic();
                KeyStore.addKeys(username, publicKey, privateKey);
            }
            System.out.println("Done.");
        } catch (Exception e) {
            System.out.println("Failed to generate keys: " + e.getMessage());
        }
    }
}
