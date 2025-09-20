import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class BookManager {
    public void AddBook(String title, String author) {
        System.out.println("Add Book");

        String query = "INSERT INTO books (title, author, available) VALUES (?, ?, TRUE)";

        // Check connection with database and execute query
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title); // Switch the first ? with title
            stmt.setString(2, author); // Switch the second ? with author

            int rows = stmt.executeUpdate(); // How many rows were affected after executeUpdate
            if (rows > 0) {
                System.out.println("Book added: " + title + " by " + author);
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void BorrowBook() {System.out.println("Borrow Book");}
    public void ReturnBook() {System.out.println("Return Book");}
    public void SearchBook() {System.out.println("Search Book");}
}
