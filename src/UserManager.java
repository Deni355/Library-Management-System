import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserManager {
    public Integer addUser(String firstName, String lastName) {
        String query = "INSERT INTO users (first_name, last_name) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName); // Switch the first ? with name
            stmt.setString(2, lastName); // Switch the first ? with name

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    System.out.println(firstName + " " + lastName + " registered successfully!");
                    return userId;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
        }

        return null;
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
