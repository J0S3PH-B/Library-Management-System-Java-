package src.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.model.book;
import src.util.dbConnection;

public class bookDAO{

    public List<book> findAll() throws SQLException {
        List<book> bookList = new ArrayList<>();
        String searchInSQL = "SELECT * FROM books;";

        try (Connection c = dbConnection.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(searchInSQL)) {

            while (rs.next()) {
                bookList.add(map(rs));
             }
        }catch (SQLException e) {
            System.out.println("DEBUG MAP ERROR: " + e.getMessage());
            throw e;
        }
        return bookList;
    }

    private book map(ResultSet rs) throws SQLException {
        boolean st = rs.getBoolean("status");
        // System.out.println("DEBUG mapping book_id=" + rs.getInt("book_id") + " status=" + st); //untuk debug
        try {
            return new book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("category"),
                rs.getBoolean("status")
            );
        } catch (SQLException e) {
            System.out.println("DEBUG MAP ERROR: " + e.getMessage());
            throw e;
        }
    }

    public List<book> searchByTitle(String title) throws SQLException {
        List<book> searchResult = new ArrayList<>();
        String searchByTitleInSql = "SELECT * FROM books WHERE title LIKE ?;";

        try (Connection c = dbConnection.getConnection();
            PreparedStatement p = c.prepareStatement(searchByTitleInSql);){

            p.setString(1, "%" + title + "%");

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    searchResult.add(map(rs));
                }
            }
        }catch (SQLException e) {
            System.out.println("DEBUG MAP ERROR: " + e.getMessage());
            throw e;
        }
        return searchResult;
    }

    public void setStatus (int bookId, boolean status) throws SQLException{
        String setStatusInSql = "UPDATE books SET status=? WHERE book_id=?;";

        try (Connection c = dbConnection.getConnection();
            PreparedStatement p = c.prepareStatement(setStatusInSql)){
                
            p.setBoolean(1, status);
            p.setInt(2, bookId);
            p.executeUpdate();
        }catch (SQLException e) {
            System.out.println("DEBUG MAP ERROR: " + e.getMessage());
            throw e;
        }
    }

    public book getById(int int1) throws SQLException {
        String sql = "SELECT * FROM books WHERE book_id LIKE ?;";
        int id = int1;

        try (Connection c = dbConnection.getConnection();
            PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }catch (SQLException e) {
            System.out.println("DEBUG MAP ERROR: " + e.getMessage());
            throw e;
        }
        return null;
    }
}
