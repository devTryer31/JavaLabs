package Services;

import Services.Interfaces.ILogger;
import Services.Interfaces.ISpeedTestLogger;
import models.*;
import models.domain.VehicleItem;

import java.util.*;

public final class CollectionTester {

    public static final Map<String, HashMap<Integer, Long>> AddResults = new TreeMap<>();
    public static final Map<String, HashMap<Integer, Long>> RemoveResults = new TreeMap<>();

    public static void StartTesting(ILogger logger, boolean enableEveryElementLogs) {
        TestCollection(logger, new ArrayList<>(), "ArrayList", enableEveryElementLogs, 5);
        TestCollection(logger, new LinkedList<>(), "LinkedList", enableEveryElementLogs, 5);
    }

    public static void TestCollection(ILogger logger, Collection<VehicleItem> collection, String collectionName, boolean enableEveryElementLogs, int seed) {
        Random rnd = new Random(seed);
        logger.LogInfo("Executing StartTesting method from CollectionTester");
        for (int cnt : new int[]{10, 100, 1000, 10000, 100000}) {
            var db = new InMemoryVehiclesDataService(null, collection);
            var items = GenerateRange(cnt, 5);

            String header = "new " + cnt + " values to " + collectionName;
            logger.LogStartSpeedTest(header, "ms");

            int cntItems = 0;
            String addItemsHeader = "adding";
            logger.LogStartSpeedTest(addItemsHeader, "ns");

            for (var item : items) {
                String headerIdx = "add, idx=" + (++cntItems);
                if (enableEveryElementLogs)
                    logger.LogStartSpeedTest(headerIdx, "ns");

                db.Add(item);
                if (enableEveryElementLogs)
                    logger.LogEndSpeedTest(headerIdx, "ns");
            }
            long totalAddItemsTime = logger.LogEndSpeedTest(addItemsHeader, "ns");

            var res = (totalAddItemsTime / cnt);

            logger.LogInfo("Average element adding time: " + res + " ms");

            var row = AddResults.get(collectionName);
            if (row == null) {
                var tmp = new HashMap<Integer, Long>();
                tmp.put(cnt, res);
                AddResults.put(collectionName, tmp);
            } else
                row.put(cnt, res);

            String removeItemsHeader = "removing";
            int toDelete = (int) (cnt * 0.1);
            LinkedList<Integer> idxsToDelete = new LinkedList<>();
            do {
                int idxToDelete = Math.abs(rnd.nextInt()) % cnt;
                if (!idxsToDelete.contains(idxToDelete))
                    idxsToDelete.add(idxToDelete);
            } while (idxsToDelete.size() != toDelete);


            logger.LogStartSpeedTest(removeItemsHeader, "ns");


            for (int id : idxsToDelete) {

                String headerIdx = "remove, idx=" + (id);
                if (enableEveryElementLogs)
                    logger.LogStartSpeedTest(headerIdx, "ns");

                db.Delete(id);

                if (enableEveryElementLogs)
                    logger.LogEndSpeedTest(headerIdx, "ns");
            }
            long totalRemoveItemsTime = logger.LogEndSpeedTest(removeItemsHeader, "ns");

            res = totalRemoveItemsTime / cnt;

            logger.LogInfo("Average element removing time: " + res + " ns");

            row = RemoveResults.get(collectionName);
            if (row == null) {
                var tmp = new HashMap<Integer, Long>();
                tmp.put(cnt, res);
                RemoveResults.put(collectionName, tmp);
            } else
                row.put(cnt, res);

            logger.LogEndSpeedTest(header, "ms");
        }
        logger.LogInfo("Additional test begin");
        AdditionalArrayListTest(logger);
        logger.LogInfo("Additional test end\n");
    }

    public static void AdditionalArrayListTest(ISpeedTestLogger logger) {
        int oldCapacity = 10;
        String toAddElement = "Some element to add";
        //var items = GenerateRange(oldCapacity * 3 / 2 + 1, 5);
        var oldCollection = new ArrayList<String>(oldCapacity);

        String header1 = "Add one element with capacity=" + oldCapacity;
        logger.LogStartSpeedTest(header1, "ns");
        oldCollection.add(toAddElement);
        logger.LogEndSpeedTest(header1, "ns");


        int newCapacity = oldCapacity * 3 / 2 + 1;
        var newCollection = new ArrayList<String>(newCapacity);
        String header2 = "Add one element with capacity=" + newCapacity;
        logger.LogStartSpeedTest(header2, "ns");
        newCollection.add(toAddElement);
        logger.LogEndSpeedTest(header2, "ns");

    }

    public static Iterable<VehicleItem> GenerateRange(int length, int seed) {
        final String[] vehicle_types = new String[]{
                "Bike",
                "Car",
                "Truck",
                "Bus",
                "Trailer"
        };
        Random rnd = new Random(seed);

        VehicleItem[] result_arr = new VehicleItem[length];

        for (int i = 0; i < length; ++i) {

            String type = vehicle_types[Math.abs(rnd.nextInt()) % vehicle_types.length];

            switch (type) {
                case "Bike" -> {
                    result_arr[i] = new VehicleItem();
                    result_arr[i].setId(i);
                    result_arr[i].setItem(new Bike(
                            "Bike Mark #" + rnd.nextInt(),
                            "Bike Model #" + rnd.nextInt(),
                            rnd.nextDouble(10000.0) + 2000.0,
                            rnd.nextInt(6) + 1,
                            rnd.nextDouble(100.0) + 256.3,
                            rnd.nextInt(3) + 0
                    ));
                }
                case "Car" -> {
                    result_arr[i] = new VehicleItem();
                    result_arr[i].setId(i);
                    result_arr[i].setItem(new Car(
                            "Car Mark #" + rnd.nextInt(),
                            "Car Model #" + rnd.nextInt(),
                            rnd.nextDouble(10000.0) + 2000.0,
                            rnd.nextInt(6) + 1,
                            rnd.nextDouble(100.0) + 256.3,
                            rnd.nextInt(7) + 3
                    ));
                }
                case "Truck" -> {
                    result_arr[i] = new VehicleItem();
                    result_arr[i].setId(i);
                    result_arr[i].setItem(new Truck(
                            "Truck Mark #" + rnd.nextInt(),
                            "Truck Model #" + rnd.nextInt(),
                            rnd.nextDouble(1000000.0) + 5000.0,
                            rnd.nextInt(5) + 1,
                            rnd.nextDouble(100.0) + 356.3,
                            rnd.nextInt(2) + 6
                    ));
                }
                case "Bus" -> {
                    result_arr[i] = new VehicleItem();
                    result_arr[i].setId(i);
                    result_arr[i].setItem(new Bus(
                            "Bus Mark #" + rnd.nextInt(),
                            "Bus Model #" + rnd.nextInt(),
                            rnd.nextDouble(1000000.0) + 5000.0,
                            rnd.nextInt(50) + 1,
                            rnd.nextDouble(100.0) + 356.3,
                            rnd.nextInt(2) + 6
                    ));
                }
                case "Trailer" -> {
                    result_arr[i] = new VehicleItem();
                    result_arr[i].setId(i);
                    result_arr[i].setItem(new Trailer(
                            "Trailer Mark #" + rnd.nextInt(),
                            "Trailer Model #" + rnd.nextInt(),
                            rnd.nextDouble(10000.0) + 3000.0,
                            rnd.nextInt(25) + 0,
                            rnd.nextDouble(100.0) + 266.3,
                            rnd.nextInt(2) + 6
                    ));
                }
            }
        }
        return List.of(result_arr);
    }


}
