package detyra;

import java.util.Scanner;

public class Main {

    private static void runInteractiveMode(EmailServer server) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== PGP Email System (Interactive Mode) ====");

        while (true) {
            System.out.print("\nEnter your username: ");
            String username = scanner.nextLine().trim();
            EmailClient client = new EmailClient(username, server);

            while (true) {
                System.out.println("\n1. Send Email");
                System.out.println("2. Check Inbox");
                System.out.println("3. Switch User");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        System.out.print("Enter recipient: ");
                        String recipient = scanner.nextLine().trim();
                        System.out.print("Enter message: ");
                        String message = scanner.nextLine();
                        client.sendEmail(recipient, message);
                        break;
                    case "2":
                        client.checkInbox();
                        break;
                    case "3":
                        System.out.println("Switching user...");
                        break;
                    case "4":
                        System.out.println("Goodbye!");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid option.");
                }

                if (option.equals("3")) break;
            }
        }
    }
}
