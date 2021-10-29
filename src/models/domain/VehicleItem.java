package models.domain;

import models.Vehicle;

public final class VehicleItem {
    private int _id;
    private Vehicle _item;

    public int getId() {
        return _id;
    }

    public Vehicle getItem() {
        return _item;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setItem(Vehicle source) {
        _item = source;
    }
}
