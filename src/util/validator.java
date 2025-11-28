package src.util;

public class validator {
    public static boolean validEmail (String email){
        return email.matches("^.{2,}@.+$");
    }

    public static boolean validUserNameString (String nameString){
        return nameString != null && nameString.length() > 1 && nameString.length() <=10;
    }
}