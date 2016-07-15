package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public enum IndianNotation {
    S,
    r,
    R,
    g,
    G,
    m,
    M,
    P,
    d,
    D,
    n,
    N;


    public static IndianNotation fromString(String value) {
        for (IndianNotation note : values()) {
            if (note.toString().equals(value)) {
                return note;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Indian Note.");
    }

    public static IndianNotation fromOrdinal(int value) {
        for (IndianNotation note : values()) {
            if (note.ordinal() == value) {
                return note;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Indian Note.");
    }
}
