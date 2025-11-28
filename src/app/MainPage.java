package src.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import src.dao.bookDAO;
import src.dao.loanDAO;
import src.model.book;
import src.model.loan;
import src.model.user;

public class MainPage {
    public static void show(user loggedUser) throws SQLException{
        Scanner sc = new Scanner(System.in);
        bookDAO bookDAO = new bookDAO();
        loanDAO loanDAO = new loanDAO(bookDAO);

        while(true){
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. View Available Books");
            System.out.println("2. Search a Book");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return Book");
            System.out.println("5. Borrow History");            
            System.out.println("6. Exit");

            System.out.print("Choose a menu: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    List<book> listBooks = bookDAO.findAll();

                    System.out.println(" ");
                    System.out.printf("%-2s | %-62s | %-30s | %-10s%n", "No", "Book ID", "Title", "Author", "Category");
                    for(book Book : listBooks){
                        if(Book.getStatus() == false) {
                            System.out.printf("%2d | %-62s | %-30s | %-10s%n", Book.getId(), Book.getTitle(), Book.getAuthor(), Book.getCategory());
                        }
                    }
                    break;

                case 2: 
                    sc.nextLine();
                    System.out.print("Enter title here: ");
                    String searchedTitle = sc.nextLine();

                    List<book> listBook = bookDAO.searchByTitle(searchedTitle);

                    System.out.println(" ");
                    System.out.println("List books with the title: "+searchedTitle);

                    System.out.println(" ");
                    System.out.printf("%-2s | %-62s | %-30s | %-10s%n", "No", "Book ID", "Title", "Author", "Category");
                    for(book Book:listBook){
                        System.out.printf("%2d | %-62s | %-30s | %-10s%n", Book.getId(), Book.getTitle(), Book.getAuthor(), Book.getCategory());
                    }
                    System.out.println(" ");
                    break;

                case 3:
                    System.out.println(" ");
                    boolean availability = true;
                    do{
                        System.out.print("Enter Book ID: ");
                        int bookId = sc.nextInt();
                        availability = loanDAO.borrowBook(loggedUser.getId(), bookId);

                        if(availability){
                            System.out.println("Book not available");
                            System.out.println(" ");
                        }
                    }while(availability);

                    System.out.println("Book successfully borrowed");
                    System.out.println(" ");
                    break;

                case 4:
                    System.out.println(" ");
                    loan result = null;
                    do{
                        System.out.print("Enter Book ID: ");
                        int returnedBookId = sc.nextInt();
                        result = loanDAO.returnBook(returnedBookId);

                        if (result == null) {
                            System.out.println("Book not Found");
                        }
                    }while(result == null);

                    System.out.println("Book successfully returned");

                    int fine = result.getFine();

                    if(fine > 0){
                        System.out.println("You have fine: "+fine);
                        System.out.print("Press ENTER to continue...");

                        String wspace = sc.nextLine();
                    }
                    System.out.println(" ");
                    break;

                case 5:
                    System.out.println(" ");
                    int memberId = loggedUser.getId();
                    int no = 1;
                    List<loan> userLoans = loanDAO.findByMember(memberId);
                    System.out.println("Your Borrowing History: ");
                    System.out.println("No | Loan ID | Book Title | Borrow Date | Due Date | Return Date | Fine ");
                    for(loan Loan:userLoans){
                        System.out.println(no+" "+Loan.getLoanId()+" "+Loan.getBook().getTitle()+" "+Loan.getLoanDate()+" "+Loan.getDueDate()+" "+Loan.getReturnDate()+" "+Loan.getFine());
                        no+=1;
                    }

                    break;
                case 6:
                    System.out.println("Goodbye...");
                    sc.close();
                    return;
            }
        }      
    }
}
