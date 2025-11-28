package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.model.user;
import src.util.dbConnection;

public class userDAO {
    public user login(String uName, String uEmail) throws SQLException{
        String searchUser = "SELECT user_id, name, email FROM users WHERE name LIKE ? AND email LIKE ?;";
        
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchUser)) {

            stmt.setString(1, uName);
            stmt.setString(2, uEmail);

            try (ResultSet rs = stmt.executeQuery()) {
                // System.out.println("DEBUG query returned = " + rs.next());
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    private user map(ResultSet rs) throws SQLException {
        try{
            return new user(
                rs.getInt("user_id"),
                rs.getString("name"),
                rs.getString("email")
            );
        }catch (SQLException e) {
            System.out.println("MAP ERROR: " + e.getMessage());
            throw e;
        }
    }
}
