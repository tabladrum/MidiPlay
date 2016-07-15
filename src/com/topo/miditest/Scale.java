package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public class Scale {
    private NoteNotation note;
    private int octave;

    public Scale (NoteNotation note, int octave) {
        this.note = note;
        this.octave = octave;
    }

    public NoteNotation getNote() {
        return note;
    }

    public int getOctave() {
        return octave;
    }
}
