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

    public void borrowBook(int userId, int bookId) {
        String updateBook = "UPDATE books SET available = FALSE WHERE id = ? AND available = TRUE";
        String insertBorrowing = "INSERT INTO borrowings (user_id, book_id) VALUES (?, ?)";

        try (Connection conn = Database.getConnection()) {

            // start transaction meaning if one of the queries fails stop both
            conn.setAutoCommit(false);

            try (PreparedStatement updateStmt = conn.prepareStatement(updateBook)) {
                updateStmt.setInt(1, bookId);
                int rows = updateStmt.executeUpdate();

                if (rows == 0) {
                    System.out.println("Book not available.");
                    conn.rollback();
                    return;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertBorrowing)) {
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.executeUpdate();
            }

            // commit transaction
            conn.commit();
            System.out.println("Book borrowed successfully!");

        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
        }
    }

    public void returnBookID(int bookId) {
        String updateBook = "UPDATE books SET available = TRUE WHERE id = ? AND available = FALSE";

        String updateBorrowing = "UPDATE borrowings " +
                        "SET returned_at = NOW() " +
                        "WHERE book_id = ? AND returned_at IS NULL";

        try (Connection conn = Database.getConnection()) {

            // start transaction
            conn.setAutoCommit(false);

            int rowsUpdated;

            try (PreparedStatement stmt = conn.prepareStatement(updateBook)) {
                stmt.setInt(1, bookId);
                rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated == 0) {
                    System.out.println("No borrowed book found with that ID.");
                    conn.rollback();
                    return;
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateBorrowing)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            }

            // commit both updates together
            conn.commit();
            System.out.println("Book returned!");

        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    public void searchBook() {System.out.println("Search Book");}
}
