package views;

import java.util.Scanner;

public final class AdminView {
    public static final String Error = "Error: ";


    public static boolean DisplayEnableOrDisable(String paramName){
        while (true) {
            Print("Enable Or Disable" +paramName + "? [Enter 'e' or 'd']");
            Scanner scanner = new Scanner(System.in);

            String choose = scanner.nextLine();

            if (choose.equals("e")) {
                return false;
            } else if (choose.equals("d")) {
                return true;
            } else {
                PrintError("Wrong input");
            }
        }

    }



    public static void Print(Object obj) {
        System.out.println(obj);
    }

    public static void PrintError(String msg){
        Print(Error + msg);
    }
}
