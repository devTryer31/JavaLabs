package Services.Interfaces;

import models.domain.VehicleItem;

public interface IVehiclesRepository{

    Iterable<VehicleItem> GetAll();

    VehicleItem Get(int id);

    void Add(VehicleItem source);

    boolean Update(int id, VehicleItem source);

    boolean Delete(int id);
}
