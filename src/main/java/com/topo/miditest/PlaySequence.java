package com.topo.miditest;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by yaf107 on 7/16/16.
 */
public class PlaySequence {
    public static final int BEAT_RESOLUTION = 60;

    public static void main(String[] args) {
        MusicFile f = new MusicFile("/Users/yaf107/Desktop/MeruKhand.txt");
        Note[] notess = f.getNotes();
        TranslateNotes translateNotes = new TranslateNotes(notess, f.getType(), f.getScale());
        Note[] finalNotes = translateNotes.getNotes();
        try {
            MidiSequence seq = new MidiSequence(f.getBpm(), BEAT_RESOLUTION);
            seq.addTrack(f.getInstrument(), finalNotes);
            seq.play();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
