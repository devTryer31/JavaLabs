package Services;

import Services.Interfaces.ILogger;
import Services.Interfaces.IVehiclesRepository;
import models.Vehicle;
import models.domain.*;
import views.MainView;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public final class InMemoryVehiclesDataService implements IVehiclesRepository {

    private int _lastId = 0;

    private Collection<VehicleItem> _items;
    private ILogger _logger;

    public InMemoryVehiclesDataService(ILogger logger, Collection<VehicleItem> collection) {
        _items = collection;
        _logger = logger;
    }

    public InMemoryVehiclesDataService() {
    }

    @Override
    public Iterable<VehicleItem> GetAll() {
        return _items;
    }

    @Override
    public void Add(VehicleItem source) {
        if(_logger != null)
            _logger.LogStartSpeedTest("Adding item", "ns");
        source.setId(_lastId++);
        _items.add(source);
        if(_logger != null)
            _logger.LogEndSpeedTest("Adding item", "ns");
    }

    @Override
    public boolean Update(int id, VehicleItem source) {
        var founded_item = Get(id);

        if (founded_item == null)
            return false;

        if (_logger != null)
            _logger.LogInfo("Swapping on id= " + id + " between\n"
                    + founded_item.getItem() +
                    "\nand\n" +
                    source.getItem() + '\n');

        founded_item.setItem(source.getItem());


        return true;
    }

    @Override
    public VehicleItem Get(int id) {
        VehicleItem founded_item = null;
        for (var item : _items)
            if (item.getId() == id) {
                founded_item = item;
                break;
            }
        return founded_item;
    }

    @Override
    public boolean Delete(int id) {
        if (_logger != null)
            _logger.LogInfo("Deleting entity: id=" + id + " item=" + Get(id).getItem());
        return _items.removeIf(item -> item.getId() == id);
    }

    public void DumpToFile(String filePath, boolean isAppend) {
        if (_logger != null)
            _logger.LogInfo("Attempt dump to " + filePath + " append mode=" + isAppend);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, isAppend);
            for (VehicleItem item : _items) {
                fileWriter.append(item.getItem().SerializeToString() + "\n|\n");
            }
            if (_logger != null)
                _logger.LogInfo("Dumped " + _items.size() + " entities successfully");
            fileWriter.close();
        } catch (Exception e) {
            if (_logger != null)
                _logger.LogError("Dump denied. msg=" + e.getMessage());
            MainView.PrintError(e.getMessage());
            return;
        }
    }

    public void LoadFromFile(String filePath, boolean isAppend) {
        if (_logger != null)
            _logger.LogInfo("Attempt load from " + filePath + " append mode=" + isAppend);
        if (!isAppend) {
            if (_logger != null)
                _logger.LogInfo("Clearing local data...");
            _items.clear();
            _lastId = 0;
        }
        try {
            Scanner scanner = new Scanner(new File(filePath));
            var sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                var s = scanner.nextLine();
                if (s.equals("|")) {
                    var item = new VehicleItem();
                    item.setItem(Vehicle.DeserializeVehicleFromString(sb.toString()));
                    Add(item);
                    sb = new StringBuilder();
                    continue;
                }
                sb.append(s + "\n");
            }
            if (_logger != null)
                _logger.LogInfo("Loaded " + _items.size() + " entities successfully");
        } catch (Exception e) {
            if (_logger != null)
                _logger.LogError("Load denied. msg=" + e.getMessage());
            MainView.PrintError(e.getMessage());
            return;
        }
    }
}

