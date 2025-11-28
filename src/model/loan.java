package src.model;

import java.sql.Date;

public class loan {
    private int loanId;
    private int memberId;
    private int bookId;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private int fine;
    private book booK;

    public loan (int loanId, int memberId, int bookId, Date loanDate, Date dueDate, Date returnDate, int fine, book booK){
        this.loanId = loanId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fine = fine;
        this.booK = booK;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public book getBook() {
        return booK;
    }

    public void setBook(book booK) {
        this.booK = booK;
    }
}
