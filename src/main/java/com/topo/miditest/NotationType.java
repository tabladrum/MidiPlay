package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public enum NotationType {
    Notes,
    Indian,
    Western;

    public static NotationType fromString(String value) {
        for (NotationType notationType : values()) {
            if (notationType.toString().equalsIgnoreCase(value)) {
                return notationType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Notation Type. Valid values are 'Indian' and 'Western'.");
    }
}
