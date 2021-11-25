package views;

import models.*;
import views.GUI.WindowView;

import java.util.*;

public final class MainView {

    public static final String Error = "Error: ";

    public static final String Lobby =
            "0. Print lobby\n" +
            "1. Get vehicle [id]\n" +
            "2. Add vehicle \n" +
            "3. Add any vehicle [amount]\n" +
            "4. Update vehicle [id]\n" +
            "5. Remove vehicle [id]\n" +
            "6. Get all vehicles\n" +
            "7. Dump data to file [file_path]\n" +
            "8. Load data from file [file_path]\n" +
            "9. Exit\n";

    public static void PrintAutoTestTable(Map<String, HashMap<Integer, Long>> results){
        boolean isHeadersPrinted = false;

        for(var row : results.entrySet()){
                System.out.print("\t\t\t");
            var rowValKeys = new TreeSet<Integer>(row.getValue().keySet());//row.getValue().entrySet();
            if(!isHeadersPrinted) {
                for (var key : rowValKeys) {//headers
                    System.out.print(String.format(" %6d",  key));
                }
                isHeadersPrinted = true;
            }
            System.out.print("\n" + row.getKey()+ "\t");
            for(var key : rowValKeys){//time
                System.out.print(String.format(" %6d",  row.getValue().get(key)));
            }
        }
    }

    public static int GetFunctionId() {
        Print("Enter function number:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static boolean DisplayYesOrNo(String title){
        while (true) {
            Print(title + " [Enter 'y' or 'n']");
            Scanner scanner = new Scanner(System.in);

            String choose = scanner.nextLine();

            if (choose.equals("n")) {
                return false;
            } else if (choose.equals("y")) {
                return true;
            } else {
                PrintError("Wrong input");
            }
        }
    }

    public static String GetFilePath(){
        Print("Enter the file path: ");
        return new Scanner(System.in).nextLine();
    }

    public static int GetId() {
        Print("Enter the vehicle id:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static String GetVehicleType() {
        String type;
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String[] vehicle_types = new String[]{
                    "Bike",
                    "Car",
                    "Truck",
                    "Bus",
                    "Trailer"
            };

            System.out.println("Enter vehicle type: ");
            for (String ts : vehicle_types)
                System.out.print(ts + ' ');
            System.out.println();

            type = scanner.nextLine();
            if (!Arrays.asList(vehicle_types).contains(type)) {
                System.out.println(Error + "Entered vehicle type not found.");
                continue;
            }
            break;
        }

        return type;
    }

    public static Bike GetBike() {
        Print("Enter bike fields:");

        var vm = _GetVehicleViewModel();

        return new Bike(
                vm.Mark,
                vm.Model,
                vm.MaxWeight,
                vm.PassengerAmount,
                vm.MaxSpeed,
                vm.WheelsAmount
        );
    }

    public static Bus GetBus() {
        Print("Enter bus fields:");

        var vm = _GetVehicleViewModel();

        var bus = new Bus(
                vm.Mark,
                vm.Model,
                vm.MaxWeight,
                vm.PassengerAmount,
                vm.MaxSpeed,
                vm.WheelsAmount
        );

        _AddTrailer(bus);

        return bus;
    }

    public static Truck GetTruck() {
        Print("Enter truck fields:");

        var vm = _GetVehicleViewModel();

        var truck = new Truck(
                vm.Mark,
                vm.Model,
                vm.MaxWeight,
                vm.PassengerAmount,
                vm.MaxSpeed,
                vm.WheelsAmount
        );

        _AddTrailer(truck);

        return truck;
    }

    public static Car GetCar() {
        Print("Enter car fields:");

       var vm = _GetVehicleViewModel();

        var car = new Car(
                vm.Mark,
                vm.Model,
                vm.MaxWeight,
                vm.PassengerAmount,
                vm.MaxSpeed,
                vm.WheelsAmount
        );

        _AddTrailer(car);
        return car;
    }

    private static void _AddTrailer(Car car){
        while (true) {
            Print("Does it have a trailer? [Enter 'y' or 'n']");
            Scanner scanner = new Scanner(System.in);

            String choose = scanner.nextLine();

            if (choose.equals("n")) {
                return;
            } else if (choose.equals("y")) {
                var trailer = GetTrailer();
                car.set_Trailer(trailer);
                return;
            } else {
                PrintError("Wrong input");
            }
        }
    }

    public static Trailer GetTrailer() {
        Print("Enter trailer fields:");

        var vm = _GetVehicleViewModel();

        return new Trailer(
                vm.Mark,
                vm.Model,
                vm.MaxWeight,
                vm.PassengerAmount,
                vm.MaxSpeed,
                vm.WheelsAmount
        );
    }

    public static int GetVehicleAmount(){
        Print("Enter the number of vehicles:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void Print(Object obj) {
        System.out.println(obj);
    }

    public static void PrintError(String msg){
        Print(Error + msg);
    }

    private static VehicleViewModel _GetVehicleViewModel() {

        VehicleViewModel res = new VehicleViewModel();

        String Mark;
        String Model;
        double MaxWeight;
        int PassengerAmount;
        double MaxSpeed;
        int WheelsAmount;

        Scanner scanner = new Scanner(System.in);

        Print("Enter mark:");
        res.Mark = scanner.nextLine();
        Print("Enter Model:");
        res.Model = scanner.nextLine();
        Print("Enter max weight:");
        res.MaxWeight = scanner.nextDouble();
        Print("Enter passenger amount:");
        res.PassengerAmount = scanner.nextInt();
        Print("Enter max speed:");
        res.MaxSpeed = scanner.nextDouble();
        Print("Wheels amount:");
        res.WheelsAmount = scanner.nextInt();

        return res;
    }
}
