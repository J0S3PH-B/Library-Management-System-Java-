import java.sql.SQLException;
import java.util.Scanner;

import src.app.MainPage;
import src.dao.userDAO;
import src.model.user;
import src.util.validator;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        userDAO userDAO = new userDAO();

        System.out.println("============ Welcome to JLibrary ============");
        System.out.println("Please input your data: ");

        while(true){
            System.out.println(" ");
            System.out.print("name: ");
            String name = sc.nextLine();
            System.out.print("email: ");
            String email = sc.nextLine();
            System.out.println(" ");

            if (!validator.validEmail(email)) {
                System.out.println("Error: Email must contain at least 2 characters before '@'.");
            }

            if (!validator.validUserNameString(name)) {
                System.out.println("Error: name must be 2 to 10 characters long.");
            }
            user loggedUser = userDAO.login(name, email);
            // System.out.println(loggedUser.getId()); //no user object returned, only null

            if (loggedUser != null) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("Welcome " + loggedUser.getName());
                MainPage.show(loggedUser);
                break;
            } 
            System.out.println("Invalid name or email!");
        }
        sc.close();
    }
}
