import java.sql.Connection;
import java.util.Scanner;

public class Main {

    static void Menu() {
        System.out.println(
                "Choose an option:\n" +
                "1. Add Book\n" +
                "2. Add User\n" +
                "3. Borrow Book\n" +
                "4. Return Book\n" +
                "5. Search Books\n" +
                "6. Exit");

        // Takes input
        Scanner reader = new Scanner(System.in);
        int choice = reader.nextInt();
        //reader.close();

        // Methods for BookManager
        BookManager manager = new BookManager();

        // Variables
        int id;

        // Options
        switch (choice) {
            case 1: // Add book
                System.out.print("Enter Book Title: ");
                String title = reader.next();
                System.out.print("Enter Book Author: ");
                String author = reader.next();
                System.out.print("Enter Book Year: ");
                int year = reader.nextInt();
                manager.addBook(title, author, year);
                break;
            case 2: // Add User
                System.out.print("Enter user's First name: ");
                String name = reader.next();
                manager.addUser(name);
                break;
            case 3: // Borrow
                manager.listAvailableBooks();
                System.out.println("Please choose a book by writing its number:");
                id = reader.nextInt();
                manager.borrowBookID(id);
                break;
            case 4: // Return book
                manager.listBorrowedBooks();
                System.out.println("Please choose a book by writing its number:");
                id = reader.nextInt();
                manager.returnBookID(id);
                break;
            case 5:
                manager.searchBook();
                break;
            case 6:
                System.exit(0);
            default:
                System.out.println("Wrong choice, try again");
                Menu();
        }
    }

    public static void main(String[] args) {
        try (Connection conn = Database.getConnection()) {
            System.out.println("Connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Welcome to Library System!");
        Menu();


    }
}
