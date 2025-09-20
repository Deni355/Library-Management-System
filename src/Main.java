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
        reader.close();

        // Methods for BookManager
        BookManager manager = new BookManager();

        // Options
        switch (choice) {
            case 1:
                manager.AddBook();
                break;
            case 2:
                //AddUser();
                break;
            case 3:
                manager.BorrowBook();
                break;
            case 4:
                manager.ReturnBook();
                break;
            case 5:
                manager.SearchBook();
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
            System.out.println("✅ Connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Welcome to Library System!");
        Menu();


    }
}
