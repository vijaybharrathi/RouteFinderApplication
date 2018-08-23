package org.routefinder.lib;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Class to find the optimized shortest path for each driver
 *
 * @author Vijay Bharrathi
 */
public class RoutingHelper {
    List<Kiosk> kiosks;
    private Stack<Kiosk> pathStack;
    private ArrayList<Kiosk> route;

    public RoutingHelper(List<Kiosk> kiosks) {
        this.kiosks = kiosks;
        pathStack = new Stack<Kiosk>();
        route = new ArrayList<Kiosk>(kiosks.size());
    }

    /**
     * Finds the shortest route for a driver within the cluster
     *
     * @return {@link List} of {@link Kiosk}s to be travelled by the driver
     */
    public List<Kiosk> findShortestRoute() {
        double[][] distanceMatrix = generateDistanceMatrix();
        Kiosk kitchen = kiosks.get(0);
        kitchen.isVisited = true;
        pathStack.push(kitchen);
        route.add(kitchen);

        Kiosk currentKiosk;
        Kiosk nextKiosk = kiosks.get(0);
        boolean shortestDistance = false;

        while (!pathStack.isEmpty()) {
            currentKiosk = pathStack.peek();
            double minimum = Double.MAX_VALUE;
            for (int i=0; i<kiosks.size(); i++) {
                double distanceToReach = distanceMatrix[kiosks.indexOf(currentKiosk)][i];
                if (distanceToReach<minimum && !kiosks.get(i).isVisited) {
                    if (distanceToReach < minimum) {
                        minimum = distanceToReach;
                        nextKiosk = kiosks.get(i);
                        shortestDistance = true;
                    }
                }
            }
            if (shortestDistance) {
                nextKiosk.isVisited = true;
                pathStack.push(nextKiosk);
                route.add(nextKiosk);
                shortestDistance = false;
                continue;
            }
            pathStack.pop();
        }
        return route;
    }

    /**
     * Calculate Distance Matrix between every kiosk in the cluster
     *
     * @return {@link Double} Matrix denoting distances
     */
    private double[][] generateDistanceMatrix() {
        int noOfKiosksInCluster = kiosks.size();
        double[][] distanceMatrix = new double[noOfKiosksInCluster][noOfKiosksInCluster];
        for (int i = 0; i < noOfKiosksInCluster; i++) {
            for (int j = i + 1; j < noOfKiosksInCluster; j++) {
                distanceMatrix[i][j] = kiosks.get(i).calculateDistance(kiosks.get(j));
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }
        return distanceMatrix;
    }
}
