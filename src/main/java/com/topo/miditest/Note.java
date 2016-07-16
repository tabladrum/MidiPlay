package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public class Note {
    private String note;
    private int duration;
    private int midi;

    public Note(String note, int duration) {
        this.note = note;
        this.duration = duration;
    }

    public String getNote() {
        return note;
    }

    public int getDuration() {
        return duration;
    }

    public int getMidi() {
        return midi;
    }

    public void setMidi(int midi) {
        this.midi = midi;
    }
}
