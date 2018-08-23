package org.routefinder.configuration;

/**
 * Enum for all the configuration parameters required for the application.
 *
 * @author Vijay Bharrathi
 */
public enum ConfigurationParamters {
    FILE_PATH("src/org/routefinder/sources/kioskCoordsAfterClustering.csv"),
    KITCHEN_NAME("Kitchen"),
    KITCHEN_ADDRESS("Lake and Racine avenue"),
    KITCHEN_LATTITUDE("41.8851024"),
    KITCHEN_LONGITUDE("-87.6618988");

    private final String value;

    ConfigurationParamters(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}