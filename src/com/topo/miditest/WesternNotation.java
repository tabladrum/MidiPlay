package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public enum WesternNotation {
    Do,
    Di,
    Ra,
    Ri,
    Me,
    Mi,
    Fa,
    Fi,
    Se,
    Sol,
    Si,
    Le,
    La,
    Li,
    Te,
    Ti;

    public static WesternNotation fromString(String value) {
        for (WesternNotation note : values()) {
            if (note.toString().equalsIgnoreCase(value)) {
                return note;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Western Note.");
    }

    public static WesternNotation fromOrdinal(int value) {
        for (WesternNotation note : values()) {
            if (note.ordinal() == value) {
                return note;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Western Note.");
    }

}
