import java.sql.Connection;
import java.util.Scanner;

public class Main {
    static final Scanner reader = new Scanner(System.in); // The reader for the input
    static final BookManager manager = new BookManager(); // BookManager

    static void welcome() {
        while (true) {
            System.out.println(
                    "Welcome to Library System!\n" +
                    "Choose an option:\n" +
                    "1. Login\n" +
                    "2. Register\n" +
                    "3. Exit");

            // Takes input
            int choice = reader.nextInt();
            reader.nextLine();

            switch (choice) {
                case 1:
                    login();
                case 2:
                    register();
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Wrong choice, try again");
            }
        }
    }

    static void login() {
        System.out.println("Please write your First name: ");

    }

    static void register() {}

    static void Menu() {

        while (true) {
            System.out.println(
                    "Choose an option:\n" +
                    "1. Add Book\n" +
                    "2. Add User\n" +
                    "3. Borrow Book\n" +
                    "4. Return Book\n" +
                    "5. Search Books\n" +
                    "6. Exit");

            // Takes input
            int choice = reader.nextInt();
            reader.nextLine();

            // Options
            switch (choice) {
                case 1: // Add book
                    System.out.print("Enter Book Title: ");
                    String title = reader.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = reader.nextLine();
                    System.out.print("Enter Book Year: ");
                    int year = reader.nextInt();
                    manager.addBook(title, author, year);
                    break;
                case 2: // Add User
                    System.out.print("Enter user's First name: ");
                    String name = reader.nextLine();
                    manager.addUser(name);
                    break;
                case 3: // Borrow
                    manager.listAvailableBooks();
                    System.out.println("Please choose a book by writing its number:");
                    int borrowId = reader.nextInt();
                    reader.nextLine();
                    manager.borrowBookID(borrowId);
                    break;
                case 4: // Return book
                    manager.listBorrowedBooks();
                    System.out.println("Please choose a book by writing its number:");
                    int returnId = reader.nextInt();
                    reader.nextLine();
                    manager.returnBookID(returnId);
                    break;
                case 5:
                    manager.searchBook(); //TODO pass reader if needed for taking input
                    break;
                case 6:
                    reader.close();
                    System.exit(0);
                default:
                    System.out.println("Wrong choice, try again");
            }
        }
    }

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            System.out.println("Connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        welcome(); // Welcome screen
    }
}
