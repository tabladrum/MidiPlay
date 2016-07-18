package com.topo.miditest;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.util.List;

/**
 * Created by yaf107 on 7/17/16.
 */
public abstract class AbstractPlay {
    protected static void playSequence (int bpm, int resolution, int instrument, List<Note[]> noteList) {
        try {
            MidiSequence seq = new MidiSequence(bpm, resolution);
            seq.addTrack(instrument, noteList);
            MidiPlay.play(seq.getSequence(), bpm);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }


    public abstract void playSequence(MusicFile musicFile);
}
