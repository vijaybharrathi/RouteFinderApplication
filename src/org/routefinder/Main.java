package org.routefinder;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.routefinder.configuration.ConfigurationParamters;
import org.routefinder.lib.Kiosk;
import org.routefinder.lib.RoutingHelper;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main class which loads the configuration params, implements clustering and shortest path finder for the drivers
 *
 * @author Vijay Bharrathi
 */
public class Main {
    public static void main(String[] args) throws Exception {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(ConfigurationParamters.FILE_PATH.getValue()))
                .withSkipLines(1).build();
        Kiosk kitchen = new Kiosk(ConfigurationParamters.KITCHEN_NAME.getValue(),
                ConfigurationParamters.KITCHEN_ADDRESS.getValue(),
                Double.parseDouble(ConfigurationParamters.KITCHEN_LATTITUDE.getValue()),
                Double.parseDouble(ConfigurationParamters.KITCHEN_LONGITUDE.getValue()));
        String[] nextRecord;
        Map<Integer, List<Kiosk>> kiosksByClusterId = new HashMap<Integer, List<Kiosk>>();
        while ((nextRecord = csvReader.readNext()) != null) {
            int clusterId = Integer.parseInt(nextRecord[5]);
            Kiosk currentKiosk = new Kiosk(nextRecord[1], nextRecord[2],
                    Double.parseDouble(nextRecord[3]), Double.parseDouble(nextRecord[4]));
            if (kiosksByClusterId.containsKey(clusterId)) {
                kiosksByClusterId.get(clusterId).add(currentKiosk);
            } else {
                List<Kiosk> newKioskList = new ArrayList<Kiosk>();
                newKioskList.add(kitchen);
                newKioskList.add(currentKiosk);
                kiosksByClusterId.put(clusterId, newKioskList);
            }
        }
        findShortestPath(kiosksByClusterId, kitchen);
    }

    public static void findShortestPath(Map<Integer, List<Kiosk>> kiosksByClusterId, Kiosk kitchen) {
        int driverNumber = 1;
        for (Map.Entry<Integer, List<Kiosk>> kiosks : kiosksByClusterId.entrySet()) {
            double distance = 0.0;
            Kiosk previousKiosk = kitchen;
            System.out.println("Route Travelled by Driver " +driverNumber);
            System.out.println();
            RoutingHelper routingHelperInstance = new RoutingHelper(kiosks.getValue());
            List<Kiosk> routeCalculated = routingHelperInstance.findShortestRoute();
            for (Kiosk kiosk : routeCalculated) {
                distance+=kiosk.calculateDistance(previousKiosk);
                System.out.println(kiosk.getKioskDetails());
                previousKiosk = kiosk;
            }
            distance+=previousKiosk.calculateDistance(kitchen);
            System.out.println();
            System.out.println("Distance travelled by driver " +driverNumber+ " is " +distance);
            System.out.println("--------------------------------------------------------------------------");
            driverNumber++;
        }
    }
}

