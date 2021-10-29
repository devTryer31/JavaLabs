package models;

import java.security.InvalidParameterException;
import java.util.Scanner;

public final class Bus extends Car{
    public Bus(String _Mark, String _Model,
               double _MaxWeight, int _PassengerAmount, double _MaxSpeed, int _WheelsAmount)
    {
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, _WheelsAmount);
    }

    public Bus(String _Mark, String _Model, double _MaxWeight, int _PassengerAmount, double _MaxSpeed) {
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed);
    }

    public Bus(){}

    @Override
    public String toString() {
        return "Bus class: " + super.toString();
    }

    @Override
    public String SerializeToString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Bus:\n");
        sb.append(super.SerializeToString());

        return sb.toString();
    }

    @Override
    public void DeserializeFromString(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();

        if (!className.equals("Bus:"))
            throw new InvalidParameterException("Class name not Bus");

        super.DeserializeFromString(s.replace("Bus:\n", ""));
    }
}
