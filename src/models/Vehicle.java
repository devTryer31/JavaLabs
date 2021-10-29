package models;

import java.security.InvalidParameterException;
import java.util.Scanner;

//Наземное транспортное средство.
public abstract class Vehicle {
    //У объектов – ТС имеются следующие свойства: марка, модель,
    // максимальная допустимая масса перевозимого груза, кол-во пассажиров, максимальная скорость.
    private String _Mark;
    private String _Model;
    private double _MaxWeight;
    private int _PassengerAmount;
    private double _MaxSpeed;
    //Для введения отличий мотоцикла с автомобилями.
    private int _WheelsAmount;

    protected Vehicle(){}

    protected Vehicle(String _Mark, String _Model,
                      double _MaxWeight, int _PassengerAmount, double _MaxSpeed, int _WheelsAmount)
            throws InvalidParameterException {
        this._Mark = _Mark;
        this._Model = _Model;
        if (_MaxWeight < 0.0)
            throw new InvalidParameterException("MaxWeight cannot be smaller then 0.");
        this._MaxWeight = _MaxWeight;
        if (_PassengerAmount < 0)
            throw new InvalidParameterException("PassengerAmount cannot be smaller then 0.");
        this._PassengerAmount = _PassengerAmount;
        if (_MaxSpeed < 0.0)
            throw new InvalidParameterException("MaxSpeed cannot be smaller then 0.");
        this._MaxSpeed = _MaxSpeed;
        if (_WheelsAmount < 0)
            throw new InvalidParameterException("WheelsAmount cannot be smaller then 0.");
        this._WheelsAmount = _WheelsAmount;
    }

    public String get_Mark() {
        return _Mark;
    }

    public String get_Model() {
        return _Model;
    }

    public double get_MaxWeight() {
        return _MaxWeight;
    }

    public int get_PassengerAmount() {
        return _PassengerAmount;
    }

    public double get_MaxSpeed() {
        return _MaxSpeed;
    }

    public int get_WheelsAmount() {
        return _WheelsAmount;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "_Mark='" + _Mark + '\'' +
                ", _Model='" + _Model + '\'' +
                ", _MaxWeight=" + this.get_MaxWeight() +
                ", _PassengerAmount=" + _PassengerAmount +
                ", _MaxSpeed=" + _MaxSpeed +
                ", _WheelsAmount=" + _WheelsAmount +
                '}';
    }

    public static VehicleViewModel GetViewModel(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();
        if (className != "Vehicle:")
            throw new InvalidParameterException("Class name not Vehicle");
        try {
            VehicleViewModel res = new VehicleViewModel(
                    scanner.nextLine(),
                    scanner.nextLine(),
                    scanner.nextDouble(),
                    scanner.nextInt(),
                    scanner.nextDouble(),
                    scanner.nextInt()
            );
            return res;
        } catch (Exception ex) {
            throw new InvalidParameterException("Invalid vehicle-serialized string: " + ex.getMessage());
        }
    }

    public String SerializeToString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Vehicle:\n");
        sb.append(_Mark);
        sb.append('\n');
        sb.append(_Model);
        sb.append('\n');
        sb.append(_MaxWeight);
        sb.append('\n');
        sb.append(_PassengerAmount);
        sb.append('\n');
        sb.append(_MaxSpeed);
        sb.append('\n');
        sb.append(_WheelsAmount);

        return sb.toString();
    }

    public abstract void DeserializeFromString(String s);

    public static  <T extends Vehicle> T DeserializeVehicleFromString(String s){
        var scanner = new Scanner(s);
        var className = scanner.nextLine();
        switch (className){
            case "Bike:":
                var bike = new Bike();
                bike.DeserializeFromString(s);
                return (T) bike;
            case "Bus:":
                var bus = new Bus();
                bus.DeserializeFromString(s);
                return (T) bus;
            case "Car:":
                var car = new Car();
                car.DeserializeFromString(s);
                return (T) car;
            case "Trailer:":
                var trailer = new Trailer();
                trailer.DeserializeFromString(s);
                return (T) trailer;
            case "Truck:":
                var truck = new Truck();
                truck.DeserializeFromString(s);
                return (T) truck;
            default:
                return null;
        }
    }

    protected void DeserializeVehicleFieldsFromString(String s) {
        Scanner scanner = new Scanner(s);
        String className = scanner.nextLine();
        if (!className.equals("Vehicle:"))
            throw new InvalidParameterException("Class name not Vehicle");
        try {
            _Mark = scanner.nextLine();
            _Model = scanner.nextLine();
            _MaxWeight = scanner.nextDouble();
            _PassengerAmount = scanner.nextInt();
            _MaxSpeed = scanner.nextDouble();
            _WheelsAmount = scanner.nextInt();
        } catch (Exception ex) {
            throw new InvalidParameterException("Invalid vehicle-serialized string: " + ex.getMessage());
        }
    }
}
