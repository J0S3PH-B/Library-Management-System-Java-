package src.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import src.model.book;
import src.model.loan;
import src.util.dbConnection;

public class loanDAO {
    
    private bookDAO bookDAO;

    public loanDAO(bookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Boolean borrowBook (int memberId, int bookId) throws SQLException{
        String checkBookStatus = "Select status FROM books WHERE book_id=?";
        String updateBookStatus = "UPDATE books SET status=TRUE WHERE book_id=?";
        String addNewLoan = "INSERT INTO loans (user_id, book_id, loan_date, due_date, return_date, fine)" + 
            "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), NULL, 0)";

        try(Connection c = dbConnection.getConnection()){

            c.setAutoCommit(false);

            boolean status_book = false; //statusnya availbale

            try(PreparedStatement ckBookSts = c.prepareStatement(checkBookStatus)){
                ckBookSts.setInt(1, bookId);
                
                try(ResultSet rSet = ckBookSts.executeQuery()){
                    if(rSet.next()){
                        status_book = rSet.getBoolean("status");
                    }else {
                        c.rollback();
                        return null; // tidak ada buku yang dicari
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            // System.out.println("DEBUG - Raw DB status: " + status_book); //test nnti dicomment
            if(status_book){
                c.rollback();
                return true;
            }
            else{
                try (PreparedStatement updateStat = c.prepareStatement(updateBookStatus)){
                    (updateStat).setInt(1,bookId);
                    updateStat.executeUpdate();
                }catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }

                try (PreparedStatement newLoan = c.prepareStatement(addNewLoan)){
                    newLoan.setInt(1, memberId);
                    newLoan.setInt(2, bookId);
                    newLoan.executeUpdate();
                }catch (SQLException e) {
                    e.printStackTrace();
                    throw e;
                }
                c.commit();
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public loan returnBook (int bookId) throws SQLException{
        String findLoan = "SELECT * FROM loans WHERE book_id=? AND return_date IS NULL;";
        String updateLoan = "UPDATE loans SET return_date = CURDATE(), fine = GREATEST(DATEDIFF(CURDATE(), due_date), 0) * 3 WHERE book_id=?;";
        String selectLoan = "SELECT loan_id, user_id, book_id, loan_date, due_date, return_date, fine FROM loans WHERE loan_id=?;";

        try(Connection conn = dbConnection.getConnection()){

            conn.setAutoCommit(false);

            int loanId = -1;
            try(PreparedStatement fLoan = conn.prepareStatement(findLoan)){
                fLoan.setInt(1, bookId);

                try(ResultSet rSet = fLoan.executeQuery()){
                    if(rSet.next()){
                        loanId = rSet.getInt("loan_id");
                    }else {
                        conn.rollback();
                        return null; // tidak ada loan aktif
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

            try(PreparedStatement udLoan = conn.prepareStatement(updateLoan)){
                udLoan.setInt(1, bookId);
                udLoan.executeUpdate();
            }catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

            //balikin status buku (True jika Available / False jika dipinjam)
            bookDAO.setStatus(bookId, false);

            loan loanObj = null;

            try(PreparedStatement slcLoan = conn.prepareStatement(selectLoan)){
                slcLoan.setInt(1, loanId);

                try(ResultSet rSet = slcLoan.executeQuery()){
                    if(rSet.next()){
                        loanObj = map(rSet);    
                    }else {
                        conn.rollback();
                        return null; // tidak ada loan aktif
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            conn.commit();
            return loanObj;

        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private loan map(ResultSet rs) throws SQLException {
        book theBook = bookDAO.getById(rs.getInt("book_id"));
        return new loan(
                rs.getInt("loan_id"),
                rs.getInt("user_id"),
                rs.getInt("book_id"),
                rs.getDate("loan_date"),
                rs.getDate("due_date"),
                rs.getDate("return_date"),
                rs.getInt("fine"),
                theBook
        );
    }

    public List<loan> findByMember(int memberId) throws SQLException {
        List<loan> list = new ArrayList<>();
        String sql = "SELECT l.loan_id, l.user_id, l.book_id, b.title, l.loan_date, l.due_date, l.return_date, l.fine FROM loans l JOIN books b ON l.book_id = b.book_id WHERE user_id=?;";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }
}
