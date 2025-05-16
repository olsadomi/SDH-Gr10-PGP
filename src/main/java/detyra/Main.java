package detyra;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmailServer server = new EmailServer();

        if (args.length == 0) {
            // No parameters -> Interactive mode
            runInteractiveMode(server);
        } else {
            // Parameter mode
//            runParameterMode(args, server); duhet te vazhdohet implementimi nga mehmeti
        }
    }

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
    private static void runParameterMode(String[] args, EmailServer server) {
        // Expected: <username> <command> [recipient] [message...]
        // Commands: send, inbox

        if (args.length < 2) {
            System.out.println("Invalid arguments.");
            printUsage();
            return;
        }

        String username = args[0];
        String command = args[1].toLowerCase();
        EmailClient client = new EmailClient(username, server);

        switch (command) {
            case "send":
                if (args.length < 4) {
                    System.out.println("Usage: java detyra.Main <username> send <recipient> <message>");
                    return;
                }
                String recipient = args[2];
                // The message can be multiple args joined with spaces
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    sb.append(args[i]);
                    if (i < args.length - 1) sb.append(" ");
                }
                String message = sb.toString();
                client.sendEmail(recipient, message);
                break;

            case "inbox":
                client.checkInbox();
                break;

            default:
                System.out.println("Unknown command: " + command);
                printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java detyra.Main                # interactive mode");
        System.out.println("  java detyra.Main <username> send <recipient> <message>");
        System.out.println("  java detyra.Main <username> inbox");
    }
}


