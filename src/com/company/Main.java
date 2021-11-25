package com.company;

import Services.CollectionTester;
import Services.InMemoryVehiclesDataService;
import Services.Logger;
import controllers.LoginController;
import controllers.MainController;
import models.*;
import models.domain.VehicleItem;
import views.GUI.Components.GraphComponent.*;
import views.GUI.WindowView;
import views.MainView;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        HashMap<Integer, Long> xmaps = new HashMap<>();
        xmaps.put(1, 1L);
        xmaps.put(2, 2L);

        GraphComponentParams params = new GraphComponentParams();
        params.title = "TestTitle";
        params.graphColor = Color.CYAN;
        params.xAxisName = "count";
        params.yAxisName = "time";

        WindowView window = new WindowView(xmaps, xmaps, params);
        window.ShowWindowView();

        //        Locale.setDefault(new Locale("en"));
//
//        String appConfigPath = "app.properties";
//
//        Properties appProps = new Properties();
//        Logger logger = null;
//        try {
//            appProps.load(new FileInputStream(appConfigPath));
//            if (appProps.getProperty("ISDEBUGMODE").equals("true"))
//                logger = new Logger(new FileOutputStream("logs.txt", true));
//            if (appProps.getProperty("ENABLEAUTOTESTS").equals("true")) {
//                if(logger == null)
//                    CollectionTester.StartTesting(new Logger(System.out), false);
//                else
//                    CollectionTester.StartTesting(logger, false);
//            }
//        } catch (Exception e) {
//            MainView.PrintError(e.getMessage());
//            return;
//        }
//
//        var repository = new InMemoryVehiclesDataService(logger, new ArrayList<VehicleItem>());
//        var mainController = new MainController(repository, appProps, logger);
//        var loginController = new LoginController(appProps, logger);
//
//        var role = loginController.Execute();
//
//        MainView.Print("Enter '0'.");
//        while (mainController.Execute(role)) ;
//        if (logger != null) {
//            logger.LogTotalErrorsCount();
//            logger.LogInfo("Good program termination");
//        }
//        MainView.Print("Termination...");
    }

    private static void TestsLab3() {
        Trailer simpleTrailer = new Trailer(
                "Report",
                "ax4",
                500,
                0,
                5
        );

        Bus simpleBus = new Bus(
                "Audi",
                "bs754",
                3500,
                35,
                200,
                8
        );

        System.out.println(simpleTrailer.SerializeToString());
        System.out.println(simpleBus.SerializeToString());
        System.out.println("=================");
        simpleBus.set_Trailer(simpleTrailer);
        System.out.println(simpleBus.SerializeToString());

        var s = simpleBus.SerializeToString();

        var bus = new Bus();
        bus.DeserializeFromString(s);

        System.out.println(simpleBus);

        System.out.println("=====================");

        System.out.println(bus);
    }


    private static void Lab2Execution() {
        Bike bike = new Bike(
                "Ferrari",
                "AX2093",
                200,
                2,
                300,
                2
        );
        //Bike test.
        try {
            Bike bike4wheels = new Bike(
                    "BMV",
                    "FAT3000",
                    323,
                    4,
                    304,
                    4
            );
        } catch (Exception ex) {
            System.out.println(ex);
        }

        Car simpleCar = new Car(
                "Lada",
                "Six",
                750,
                4,
                100
        );
        //Car test.
        try {
            Car errorCar = new Car(
                    "Lada",
                    "Six",
                    -750,
                    4,
                    100
            );
        } catch (Exception ex) {
            System.out.println(ex);
        }


        Truck simpleTruck = new Truck(
                "Mercedes",
                "Tr520",
                7500,
                2,
                250,
                8
        );
        //Truck test.
        try {
            Truck errorTruck = new Truck(
                    "Mercedes",
                    "Tr520",
                    -7500,
                    2,
                    250,
                    8
            );
        } catch (Exception ex) {
            System.out.println(ex);
        }

        Bus simpleBus = new Bus(
                "Audi",
                "bs754",
                3500,
                35,
                200,
                8
        );
        //Bus test.
        try {
            Bus errorBus = new Bus(
                    "Audi",
                    "bs754",
                    3500,
                    -35,
                    200,
                    8
            );
        } catch (Exception ex) {
            System.out.println(ex);
        }


        Trailer simpleTrailer = new Trailer(
                "Report",
                "ax4",
                500,
                0,
                5
        );
        //Trailer test.
        try {
            Trailer errorTrailer = new Trailer(
                    "Report",
                    "ax4",
                    500,
                    1,
                    -250
            );
        } catch (Exception ex) {
            System.out.println(ex);
        }

        System.out.println("================================");
        System.out.println("List of vehicles:");
        List<Vehicle> vehicles = List.of(bike, simpleCar, simpleTruck, simpleBus, simpleTrailer);
        for (Vehicle veh : vehicles) {
            System.out.println(veh);
        }

        simpleCar.set_Trailer(simpleTrailer);
        simpleTruck.set_Trailer(new Trailer(
                "NewBalance",
                "SyqJo004",
                50000,
                0,
                400
        ));

        System.out.println("================================");
        System.out.println("List of vehicles after trailers connected:");
        for (Vehicle veh : vehicles) {
            System.out.println(veh);
        }
    }
}
