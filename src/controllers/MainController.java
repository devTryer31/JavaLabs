package controllers;

import Services.CollectionTester;
import Services.InMemoryVehiclesDataService;
import Services.Interfaces.IVehiclesRepository;
import Services.Interfaces.ILogger;
import models.domain.VehicleItem;
import views.GUI.Components.GraphComponent;
import views.GUI.WindowView;
import views.MainView;

import java.awt.*;
import java.util.Properties;

public class MainController {
    private final IVehiclesRepository _dataRepository;
    private final Properties _properties;
    private ILogger _logger;

    public MainController(IVehiclesRepository dataRepository, Properties properties, ILogger logger) {
        _dataRepository = dataRepository;
        _properties = properties;
        _logger = logger;
    }

    public boolean Execute(String role) {
        switch (MainView.GetFunctionId()) {
            case 0:
                MainView.Print(MainView.Lobby);
                if (role.equals("root")) {
                    Boolean isAutoTests = _properties.getProperty("ENABLEAUTOTESTS").equals("true");
                    MainView.Print("Admin display: \n" +
                            "Debug: " + _properties.getProperty("ISDEBUGMODE") + "\n" +
                            "AutoTests: " + isAutoTests + "\n");
                    if (isAutoTests) {
                        MainView.Print("Add results:");
                        MainView.PrintAutoTestTable(CollectionTester.AddResults);
                        MainView.Print("\n\nRemove results:");
                        MainView.PrintAutoTestTable(CollectionTester.RemoveResults);
                        MainView.Print("\n");
                    }
                }
                break;
            case 1:
                var res = _dataRepository.Get(MainView.GetId());
                if (res != null)
                    MainView.Print(res.getItem());
                else
                    MainView.PrintError("Entity not found.");
                break;
            case 2:
                AddOrGetVehicle(false);
                break;
            case 3:
                int cnt = MainView.GetVehicleAmount();
                while (cnt-- != 0)
                    AddOrGetVehicle(false);
                break;
            case 4:
                int id = MainView.GetId();
                if (!_dataRepository.Update(id, AddOrGetVehicle(true)))
                    MainView.PrintError("The modification has not been applied.");
                break;
            case 5:
                if (!_dataRepository.Delete(MainView.GetId()))
                    MainView.PrintError("The deleting has not been applied.");
                break;
            case 6:
                for (VehicleItem item : _dataRepository.GetAll()) {
                    MainView.Print("id=" + item.getId() + " Entity= " + item.getItem());
                }
                break;
            case 7:
                ((InMemoryVehiclesDataService) _dataRepository).DumpToFile(MainView.GetFilePath(), MainView.DisplayYesOrNo("Tern on append mode?"));
                break;
            case 8:
                ((InMemoryVehiclesDataService) _dataRepository).LoadFromFile(MainView.GetFilePath(), MainView.DisplayYesOrNo("Tern on append mode?"));
                break;
            case 9:
                var params = new GraphComponent.GraphComponentParams();
                params.graphColor = Color.CYAN;
                params.xAxisName = "N";
                params.yAxisName = "ns";
                for (var addResKey : CollectionTester.AddResults.keySet()) {
                    params.title = addResKey;
                    new WindowView("Add result", CollectionTester.AddResults.get(addResKey), params).ShowWindowView();
                }
                break;
            case 10:
                return false;
            default:
                MainView.Print("Invalid function number.");
        }
        return true;
    }

    public VehicleItem AddOrGetVehicle(boolean isGetVehicleMode) {
        var type = MainView.GetVehicleType();
        var vehItem = new VehicleItem();

        switch (type) {
            case "Bike":
                vehItem.setItem(MainView.GetBike());
                break;
            case "Car":
                vehItem.setItem(MainView.GetCar());
                break;
            case "Truck":
                vehItem.setItem(MainView.GetTruck());
                break;
            case "Bus":
                vehItem.setItem(MainView.GetBus());
                break;
            case "Trailer":
                vehItem.setItem(MainView.GetTrailer());
                break;

        }
        if (isGetVehicleMode)
            return vehItem;

        _dataRepository.Add(vehItem);
        return null;
    }

}
