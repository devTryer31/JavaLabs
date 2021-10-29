package models;

import java.security.InvalidParameterException;
import java.util.Scanner;

public final class Bike extends Vehicle{

    public Bike(String _Mark, String _Model,
                   double _MaxWeight, int _PassengerAmount, double _MaxSpeed, int _WheelsAmount)
            throws InvalidParameterException
    {
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, _WheelsAmount);
        if(_WheelsAmount > 3)
            throw new InvalidParameterException("WheelsAmount must be positive less then 3 number.");
    }

    public Bike(){}

    @Override
    public String toString() {
        return "Bike class: " + super.toString();
    }

    @Override
    public String SerializeToString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Bike:\n");
        sb.append(super.SerializeToString());

        return sb.toString();
    }

    @Override
    public void DeserializeFromString(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();

        if(!className.equals("Bike:"))
            throw new InvalidParameterException("Class name not Bike");

        super.DeserializeVehicleFieldsFromString(s.replace("Bike:\n", ""));
    }
}
