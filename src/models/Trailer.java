package models;

import java.security.InvalidParameterException;
import java.util.Scanner;

public final class Trailer extends Vehicle{

    public Trailer(String _Mark, String _Model,
                   double _MaxWeight, int _PassengerAmount, double _MaxSpeed, int _WheelsAmount)
    {
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, _WheelsAmount);
    }

    public Trailer(String _Mark, String _Model, double _MaxWeight, int _PassengerAmount, double _MaxSpeed) {
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, 2);
    }

    public Trailer(){}

    @Override
    public String toString() {
        return "Trailer class: " + super.toString();
    }

    @Override
    public String SerializeToString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Trailer:\n");
        sb.append(super.SerializeToString());
        return sb.toString();
    }

    @Override
    public void DeserializeFromString(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();

        if(!className.equals("Trailer:"))
            throw new InvalidParameterException("Class name not Trailer");

        super.DeserializeVehicleFieldsFromString(s.replace("Trailer:\n", ""));
    }
}
