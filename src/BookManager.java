import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookManager {
    public void addBook(String title, String author, int year) {
        String query = "INSERT INTO books (title, author, year, available) VALUES (?, ?, ?, TRUE)";

        // Check connection with database and execute query
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title); // Switch the first ? with title
            stmt.setString(2, author); // Switch the second ? with author
            stmt.setInt(3, year); // third ? with year

            int rows = stmt.executeUpdate(); // How many rows were affected after executeUpdate
            if (rows > 0) {
                System.out.println("Book added: " + title + " by " + author + " (" + year + ")");
            }
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    // TODO add relation between user and book borrowing and returning
    public void listBook(boolean available) {
        String query = "";
        if (available) {
            System.out.println("Available Books:");
            query = "SELECT * FROM books WHERE available = TRUE";
        } else {
            System.out.println("Borrowed books:");
            query = "SELECT * FROM books WHERE available = FALSE";
        }

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) { // ResultSet is used for executeQuery

            // To print every book that is available
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year");
                System.out.println(id + ". " + title + " by " + author + " (" + year + ")");
            }

        }  catch (SQLException e) {
            System.out.println("Error with listing books: " + e.getMessage());
        }
    }

    // Wrapper methods for better look and readability
    public void listAvailableBooks() { listBook(true);}
    public void listBorrowedBooks() { listBook(false);}

    public void borrowBookID(int id) {
        String query = "UPDATE books SET available = FALSE WHERE id = ? AND available = TRUE";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id); // Switch the first ? with id

            int rows = stmt.executeUpdate(); // How many rows were affected after executeUpdate
            if (rows > 0) {
                System.out.println("Book borrowed!");
            } else  {
                System.out.println("No book found or already borrowed!");
            }
        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    public void returnBookID(int id) {
        String query = "UPDATE books SET available = TRUE WHERE id = ? AND available = FALSE";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id); // Switch the first ? with id

            int rows = stmt.executeUpdate(); // How many rows were affected after executeUpdate
            if (rows > 0) {
                System.out.println("Book returned!");
            } else  {
                System.out.println("No book found or already returned!");
            }
        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }

    }

    public void addUser(String name) {
        String query = "INSERT INTO users (name) VALUES (?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name); // Switch the first ? with name

            int rows = stmt.executeUpdate(); // How many rows were affected after executeUpdate
            if (rows > 0) {
                System.out.println("User " + name + " added!");
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }

    }
    public void searchBook() {System.out.println("Search Book");}
}
