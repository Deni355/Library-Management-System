import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManager {
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

    //TODO update database for users
    public Integer findUserId(String firstName, String lastName) {
        String query = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) { // true if found
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }

        return null;
    }
}
