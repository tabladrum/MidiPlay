package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public enum NoteNotation {
    C,
    CS,
    D,
    DS,
    E,
    F,
    FS,
    G,
    GS,
    A,
    AS,
    B;


    public static NoteNotation fromString(String value) {
        value = value.replace("#", "S");
        for (NoteNotation note : values()) {
            if (note.toString().equalsIgnoreCase(value)) {
                return note;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Note.");
    }


    public static NoteNotation fromOrdinal(int value) {
        for (NoteNotation note : values()) {
            if (note.ordinal() == value) {
                return note;
            }
        }
        throw new IllegalArgumentException(value + " is not a valid Indian Note.");
    }

}
