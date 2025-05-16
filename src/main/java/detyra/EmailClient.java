package detyra;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

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
    public void sendEmail(String recipient, String message) {
        System.out.println("Please enter your email content below:");
        System.out.println("> " + message);
        System.out.println("Encrypting and signing the email...");

        try {
            PublicKey recipientKey = KeyStore.getPublicKey(recipient);
            if (recipientKey == null) {
                System.out.println("Recipient not found or has no public key!");
                return;
            }

            String encryptedMessage = PGPUtils.encrypt(message, recipientKey, true);
            String signature = PGPUtils.sign(message, privateKey, true);

            Email email = new Email(username, recipient, encryptedMessage + "|" + signature);
            server.addEmail(recipient, email);

            System.out.println("Email successfully sent to " + recipient);
            System.out.println("Awaiting encrypted messages...");
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
    public void checkInbox() {
        System.out.println("New encrypted email received. Decrypting...");

        List<Email> inbox = server.getEmails(username);
        if (inbox.isEmpty()) {
            System.out.println("Inbox is empty.");
        } else {
            for (Email email : inbox) {
                try {
                    String[] parts = email.getMessage().split("\\|");
                    if (parts.length != 2) {
                        System.out.println("Invalid email format from " + email.getSender());
                        continue;
                    }

                    String encryptedMessage = parts[0];
                    String signature = parts[1];

                    String decryptedMessage = PGPUtils.decrypt(encryptedMessage, privateKey, true);

                    PublicKey senderKey = KeyStore.getPublicKey(email.getSender());
                    if (senderKey == null) {
                        System.out.println("Cannot verify sender - public key not found for " + email.getSender());
                        continue;
                    }

                    boolean verified = PGPUtils.verify(decryptedMessage, signature, senderKey, true);

                    if (verified) {
                        System.out.println("The email from " + email.getSender() + " has been successfully decrypted and verified.");
                        System.out.println("Message content:");
                        System.out.println(decryptedMessage);
                    } else {
                        System.out.println("WARNING: The email from " + email.getSender() + " failed verification!");
                    }
                    System.out.println("------------------");
                } catch (Exception e) {
                    System.out.println("Failed to process email from " + email.getSender() + ": " + e.getMessage());
                }
            }
        }
    }
}
