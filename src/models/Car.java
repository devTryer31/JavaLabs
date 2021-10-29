package models;

import java.security.InvalidParameterException;
import java.util.Scanner;

public class Car extends Vehicle {
    private Trailer _Trailer;

    public Car(String _Mark, String _Model
            , double _MaxWeight, int _PassengerAmount, double _MaxSpeed, int _WheelsAmount) {
        super(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, _WheelsAmount);
    }

    public Car(String _Mark, String _Model, double _MaxWeight, int _PassengerAmount, double _MaxSpeed) {
        this(_Mark, _Model, _MaxWeight, _PassengerAmount, _MaxSpeed, 4);
    }

    public Car() {
    }

    public Trailer get_Trailer() {
        return _Trailer;
    }

    public void set_Trailer(Trailer _Trailer) {
        this._Trailer = _Trailer;
    }

    @Override
    public double get_MaxWeight() {
        if (_Trailer != null)
            return super.get_MaxWeight() + _Trailer.get_MaxWeight();
        return super.get_MaxWeight();
    }

    @Override
    public String toString() {
        return "Car class: " + super.toString() + "\n\t\t{" +
                "_Trailer=" + _Trailer +
                '}';
    }

    @Override
    public String SerializeToString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Car:\n");
        sb.append(super.SerializeToString());
        if (_Trailer != null) {
            sb.append("\n{");
            sb.append(_Trailer.SerializeToString());
            sb.append("}");
        }
        return sb.toString();
    }

    @Override
    public void DeserializeFromString(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();

        if (!className.equals("Car:"))
            throw new InvalidParameterException("Class name not Car");

        super.DeserializeVehicleFieldsFromString(s.replace("Car:\n", ""));

        if (s.contains("{")) {
            s = s.substring(s.indexOf('{') + 1, s.indexOf('}'));

            var trailer = new Trailer();
            trailer.DeserializeFromString(s);

            set_Trailer(trailer);
        }
    }
}
