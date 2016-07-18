package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public enum PlayModeType {
    Normal,
    Loop,
    Combination;


    public static PlayModeType fromString(String value) {
        for (PlayModeType playModeType : values()) {
            if (playModeType.toString().equals(value)) {
                return playModeType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Mode.");
    }

    public static PlayModeType fromOrdinal(int value) {
        for (PlayModeType playModeType : values()) {
            if (playModeType.ordinal() == value) {
                return playModeType;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Mode.");
    }
}
