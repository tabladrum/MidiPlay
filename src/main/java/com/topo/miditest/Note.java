package com.topo.miditest;

/**
 * Created by yaf107 on 7/15/16.
 */
public class Note {
    private boolean together;
    private boolean sustain = false;
    private int velocity = 60;
    private String[] note;
    private int[] midi;

    public Note(String note) {
        this.note = new String[1];
        this.midi = new int[1];
        this.note[0] = note;
        this.together = false;
    }

    public Note(String[] note, boolean together) {
        this.note = note;
        this.midi = new int[note.length];
        this.together = together;
    }

    public String[] getNote() {
        return note;
    }

    public int[] getMidi() {
        return midi;
    }

    public void setMidi(int index, int midi) {
        this.midi[index] = midi;
    }

    public boolean isTogether() {
        return together;
    }

    public void setTogether(boolean together) {
        this.together = together;
    }

    public boolean isSustain() {
        return sustain;
    }

    public void setSustain(boolean sustain) {
        this.sustain = sustain;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
