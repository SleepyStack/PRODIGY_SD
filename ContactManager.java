import java.io.*;
import java.util.*;

public class ContactManager {
    private static final String FILE_NAME = "contacts.txt";
    private static List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) {
        loadContacts();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addContact(scanner);
                    break;
                case "2":
                    viewContacts();
                    break;
                case "3":
                    editContact(scanner);
                    break;
                case "4":
                    deleteContact(scanner);
                    break;
                case "5":
                    saveContacts();
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addContact(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine().trim();
        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added.");
    }

    private static void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            int i = 1;
            for (Contact contact : contacts) {
                System.out.println(i + ". " + contact);
                i++;
            }
        }
    }

    private static void editContact(Scanner scanner) {
        viewContacts();
        if (contacts.isEmpty()) return;
        System.out.print("Enter contact number to edit: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < contacts.size()) {
                Contact contact = contacts.get(index);
                System.out.print("Enter new name (" + contact.name + "): ");
                String name = scanner.nextLine().trim();
                System.out.print("Enter new phone (" + contact.phone + "): ");
                String phone = scanner.nextLine().trim();
                System.out.print("Enter new email (" + contact.email + "): ");
                String email = scanner.nextLine().trim();
                if (!name.isEmpty()) contact.name = name;
                if (!phone.isEmpty()) contact.phone = phone;
                if (!email.isEmpty()) contact.email = email;
                System.out.println("Contact updated.");
            } else {
                System.out.println("Invalid contact number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void deleteContact(Scanner scanner) {
        viewContacts();
        if (contacts.isEmpty()) return;
        System.out.print("Enter contact number to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < contacts.size()) {
                contacts.remove(index);
                System.out.println("Contact deleted.");
            } else {
                System.out.println("Invalid contact number.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void loadContacts() {
        contacts.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length == 3) {
                    contacts.add(new Contact(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            // File may not exist on first run, that's OK
        }
    }

    private static void saveContacts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Contact contact : contacts) {
                pw.println(contact.name + "|" + contact.phone + "|" + contact.email);
            }
        } catch (IOException e) {
            System.out.println("Error saving contacts.");
        }
    }

    static class Contact {
        String name, phone, email;
        Contact(String name, String phone, String email) {
            this.name = name;
            this.phone = phone;
            this.email = email;
        }
        public String toString() {
            return name + " | " + phone + " | " + email;
        }
    }
}
