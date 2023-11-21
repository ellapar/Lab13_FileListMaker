import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Main {
    private static ArrayList<String> itemList = new ArrayList<>();
    private static boolean needsToBeSaved = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            char choice = scanner.next().toUpperCase().charAt(0);

            switch (choice) {
                case 'A':
                    addItem();
                    break;
                case 'D':
                    deleteItem();
                    break;
                case 'V':
                    viewList();
                    break;
                case 'O':
                    openList();
                    break;
                case 'S':
                    saveList();
                    break;
                case 'C':
                    clearList();
                    break;
                case 'Q':
                    quitProgram();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list to disk");
        System.out.println("C - Clear the current list");
        System.out.println("Q - Quit the program");
        System.out.print("Enter your choice: ");
    }

    private static void addItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter item to add: ");
        String newItem = scanner.nextLine();
        if (!newItem.isEmpty()) {
            itemList.add(newItem);
            needsToBeSaved = true;
            System.out.println("Item added successfully.");
        } else {
            System.out.println("Item cannot be empty. Please try again.");
        }
    }

    private static void deleteItem() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }

        displayList();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the item to delete: ");
        int index = scanner.nextInt();

        if (index > 0 && index <= itemList.size()) {
            itemList.remove(index - 1);
            needsToBeSaved = true;
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Invalid index. No item deleted.");
        }
    }

    private static void viewList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            displayList();
        }
    }

    private static void openList() {
        if (needsToBeSaved) {
            System.out.println("You have unsaved changes. Please save your list before loading a new one.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to open: ");
        String fileName = scanner.nextLine();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName + ".txt"))) {
            itemList = (ArrayList<String>) ois.readObject();
            System.out.println("List loaded successfully.");
            needsToBeSaved = false;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the list: " + e.getMessage());
        }
    }

    private static void saveList() {
        if (itemList.isEmpty()) {
            System.out.println("The list is empty. Nothing to save.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the filename to save: ");
        String fileName = scanner.nextLine();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + ".txt"))) {
            oos.writeObject(itemList);
            System.out.println("List saved successfully.");
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error saving the list: " + e.getMessage());
        }
    }

    private static void clearList() {
        itemList.clear();
        needsToBeSaved = true;
        System.out.println("List cleared successfully.");
    }

    private static void quitProgram() {
        if (needsToBeSaved) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("You have unsaved changes. Do you want to save before quitting? (Y/N): ");
            char choice = scanner.next().toUpperCase().charAt(0);
            if (choice == 'Y') {
                saveList();
            }
        }
        System.out.println("Goodbye!");
    }

    private static void displayList() {
        System.out.println("Current List:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println((i + 1) + ". " + itemList.get(i));
        }
    }
}