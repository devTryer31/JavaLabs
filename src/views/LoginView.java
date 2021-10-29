package views;

import java.util.Scanner;

public final class LoginView {

    public static final String LoginError = "Login Error: ";

    public static void DisplayGreeting(String userName, String role) {
        Print("Hello, " + userName + "\t(" + role + ')');
    }

    public static String GetPassword() {
        Print("Enter your password:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void Print(Object obj) {
        System.out.println(obj);
    }

    public static void PrintLoginError(String msg) {
        Print(LoginError + msg);
    }
}
