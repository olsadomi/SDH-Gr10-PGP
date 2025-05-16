package detyra;

import java.io.*;
import java.util.*;

public class EmailServer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "emails.ser";

    private Map<String, List<Email>> emails;

    public EmailServer() {
        emails = new HashMap<>();
        loadEmails();
        System.out.println("Server:");
        System.out.println("Email server started, waiting for messages…");
    }

    public void addEmail(String recipient, Email email) {
        System.out.println("New encrypted email received from " + email.getSender());
        emails.computeIfAbsent(recipient, k -> new ArrayList<>()).add(email);
        saveEmails();
        System.out.println("Email forwarded to " + recipient);
    }

    public List<Email> getEmails(String username) {
        return emails.getOrDefault(username, new ArrayList<>());
    }

    public int countEmails(String username) {
        return emails.getOrDefault(username, Collections.emptyList()).size();
    }

    private void saveEmails() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(emails);
        } catch (IOException e) {
            System.out.println("Failed to save emails: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadEmails() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // no saved data yet
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            emails = (Map<String, List<Email>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load emails: " + e.getMessage());
        }
    }
}

