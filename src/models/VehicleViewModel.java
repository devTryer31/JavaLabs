package models;

public class VehicleViewModel {

    public VehicleViewModel(String mark, String model, double maxWeight, int passengerAmount, double maxSpeed, int wheelsAmount) {
        Mark = mark;
        Model = model;
        MaxWeight = maxWeight;
        PassengerAmount = passengerAmount;
        MaxSpeed = maxSpeed;
        WheelsAmount = wheelsAmount;
    }

    public String Mark;
    public String Model;
    public double MaxWeight;
    public int PassengerAmount;
    public double MaxSpeed;
    public int WheelsAmount;

    public VehicleViewModel(){}
}
