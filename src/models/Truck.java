package models;

import java.security.InvalidParameterException;
import java.util.Scanner;

public final class Truck extends Car{

    public Truck(String _Mark, String _Model,
                 double _MaxWeight, int _PassengerAmount, double _MaxSpeed, int _WheelsAmount){
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, _WheelsAmount);
    }

    public Truck(String _Mark, String _Model,
                 double _MaxWeight, int _PassengerAmount, double _MaxSpeed){
        this(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, 6);
    }

    public Truck(){}

    @Override
    public String toString() {
        return "Truck class: " + super.toString();
    }

    @Override
    public String SerializeToString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Truck:\n");
        sb.append(super.SerializeToString());

        return sb.toString();
    }

    @Override
    public void DeserializeFromString(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();

        if(!className.equals("Truck:"))
            throw new InvalidParameterException("Class name not Truck");

        super.DeserializeFromString(s.replace("Truck:\n", ""));
    }
}
