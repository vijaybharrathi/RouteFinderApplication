package org.routefinder.lib;

/**
 * Model for Kiosk properties.
 *
 * @author Vijay Bharrathi
 */
public class Kiosk {
    String name;
    String address;
    double lattitude;
    double longitude;
    boolean isVisited;

    public Kiosk(String name, String address, double lattitude, double longitude) {
        this.name = name;
        this.address = address;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    /**
     * To get kiosk details
     *
     * @return kiosk details as a {@link String}
     */
    public String getKioskDetails() {
        return "Kiosk Name:" + name + ", Address:" + address + ", Lattitude:" + lattitude + ", Longitude:" + longitude;
    }

    /**
     * Calculates distance between the kiosk instance and the given kiosk
     *
     * @param kiosk
     * @return distance as a {@link Double}
     */
    public double calculateDistance(Kiosk kiosk) {
        double latitudeDistance = lattitude - kiosk.lattitude;
        double longitudeDistance = longitude - kiosk.longitude;
        return Math.sqrt((latitudeDistance*latitudeDistance) + (longitudeDistance*longitudeDistance));
    }
}
