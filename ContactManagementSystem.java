import java.util.ArrayList;
import java.util.Scanner;

public class ContactManagementSystem {

    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("Welcome to Contact Management System!");

        while (running) {
            printMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1: // Add a new contact
                    addContact();
                    break;
                case 2: // View all contacts
                    viewAllContacts();
                    break;
                case 3: // Search for a contact
                    searchContact();
                    break;
                case 4: // Update a contact
                    updateContact();
                    break;
                case 5: // Delete a contact
                    deleteContact();
                    break;
                case 6: // Exit
                    running = false;
                    System.out.println("Thank you for using Contact Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println(); // Add a blank line for better readability
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n==== CONTACT MANAGEMENT MENU ====");
        System.out.println("1. Add a new contact");
        System.out.println("2. View all contacts");
        System.out.println("3. Search for a contact");
        System.out.println("4. Update a contact");
        System.out.println("5. Delete a contact");
        System.out.println("6. Exit");
    }

    private static void addContact() {
        System.out.println("\n==== ADD NEW CONTACT ====");
        
        String name = getStringInput("Enter name: ");
        String phoneNumber = getStringInput("Enter phone number: ");
        String email = getStringInput("Enter email address: ");

        // Validate phone number format (simple validation)
        if (!phoneNumber.matches("\\d+")) {
            System.out.println("Warning: Phone number should contain only digits.");
        }

        // Validate email format (simple validation)
        if (!email.contains("@")) {
            System.out.println("Warning: Email address should contain '@' symbol.");
        }

        Contact newContact = new Contact(name, phoneNumber, email);
        contacts.add(newContact);
        
        System.out.println("Contact added successfully!");
    }

    private static void viewAllContacts() {
        System.out.println("\n==== ALL CONTACTS ====");
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        displayContacts(contacts);
    }

    private static void searchContact() {
        System.out.println("\n==== SEARCH CONTACT ====");
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        String searchTerm = getStringInput("Enter name or phone number to search: ").toLowerCase();
        ArrayList<Contact> searchResults = new ArrayList<>();

        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(searchTerm) || 
                contact.getPhoneNumber().contains(searchTerm)) {
                searchResults.add(contact);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("No matching contacts found.");
        } else {
            System.out.println("Search results:");
            displayContacts(searchResults);
        }
    }

    private static void updateContact() {
        System.out.println("\n==== UPDATE CONTACT ====");
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        viewAllContacts();
        int index = getIntInput("Enter the index of the contact to update (or 0 to cancel): ") - 1;

        if (index == -1) {
            System.out.println("Update canceled.");
            return;
        }

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact index.");
            return;
        }

        Contact contactToUpdate = contacts.get(index);
        System.out.println("Updating contact: " + contactToUpdate);

        System.out.println("Leave field empty to keep the current value.");
        
        String name = getStringInput("Enter new name (" + contactToUpdate.getName() + "): ");
        if (!name.isEmpty()) {
            contactToUpdate.setName(name);
        }

        String phoneNumber = getStringInput("Enter new phone number (" + contactToUpdate.getPhoneNumber() + "): ");
        if (!phoneNumber.isEmpty()) {
            // Validate phone number format (simple validation)
            if (!phoneNumber.matches("\\d+")) {
                System.out.println("Warning: Phone number should contain only digits.");
            }
            contactToUpdate.setPhoneNumber(phoneNumber);
        }

        String email = getStringInput("Enter new email address (" + contactToUpdate.getEmail() + "): ");
        if (!email.isEmpty()) {
            // Validate email format (simple validation)
            if (!email.contains("@")) {
                System.out.println("Warning: Email address should contain '@' symbol.");
            }
            contactToUpdate.setEmail(email);
        }

        System.out.println("Contact updated successfully!");
    }

    private static void deleteContact() {
        System.out.println("\n==== DELETE CONTACT ====");
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        viewAllContacts();
        int index = getIntInput("Enter the index of the contact to delete (or 0 to cancel): ") - 1;

        if (index == -1) {
            System.out.println("Deletion canceled.");
            return;
        }

        if (index < 0 || index >= contacts.size()) {
            System.out.println("Invalid contact index.");
            return;
        }

        Contact contactToDelete = contacts.get(index);
        String confirmation = getStringInput("Are you sure you want to delete " + contactToDelete.getName() + "? (y/n): ");

        if (confirmation.equalsIgnoreCase("y")) {
            contacts.remove(index);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Deletion canceled.");
        }
    }

    private static void displayContacts(ArrayList<Contact> contactList) {
        System.out.println("----------------------------------------");
        System.out.printf("%-5s %-20s %-15s %-25s\n", "Index", "Name", "Phone Number", "Email");
        System.out.println("----------------------------------------");

        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            System.out.printf("%-5d %-20s %-15s %-25s\n", 
                (i + 1), 
                contact.getName(), 
                contact.getPhoneNumber(), 
                contact.getEmail());
        }
        System.out.println("----------------------------------------");
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        int input = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                input = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }

        return input;
    }

    // Contact class to store contact information
    private static class Contact {
        private String name;
        private String phoneNumber;
        private String email;

        public Contact(String name, String phoneNumber, String email) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return name + " - Phone: " + phoneNumber + ", Email: " + email;
        }
    }
}