package com.topo.miditest;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

public class PlayNote {

    public void playNote (int note, int bpm, MidiChannel[] channels) {

        if (note >= 0) {
            channels[0].noteOn(note, 100);
        }
        float sleep = (float) ((60.0 / (float )bpm) * 1000.0);
        long sleepTime = Integer.toUnsignedLong(Math.round(sleep));
        try {
            Thread.sleep(sleepTime); // wait time in milliseconds to control duration
        } catch (InterruptedException e) {
        }
        channels[0].noteOff(note);//turn of the note
    }


    public void playNotes (int[] notes, MidiChannel[] channels) {
        for (int i = 0; i < notes.length -1 ; i++) {
            channels[i].noteOn(notes[i], 100);
        }
        try {
            Thread.sleep(1000); // wait time in milliseconds to control duration
        } catch (InterruptedException e) {
        }
        for (int i = 0; i < notes.length -1 ; i++){
            channels[i].noteOff(notes[i]);
        }
    }


    public static void main(String[] args) {
        MusicFile f = new MusicFile("/Users/yaf107/Desktop/music.txt");
        Note[] notess = f.getNotes();
        TranslateNotes translateNotes = new TranslateNotes(notess, f.getType(), f.getScale());
        Note[] finalNotes = translateNotes.getNotes();
        try {
        /* Create a new Sythesizer and open it. Most of
         * the methods you will want to use to expand on this
         * example can be found in the Java documentation here:
         * https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
         */
            Synthesizer midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            //get and load default instrument and channel lists
            Instrument[] instr = midiSynth.getDefaultSoundbank().getInstruments();
            MidiChannel[] mChannels = midiSynth.getChannels();
            Soundbank bank = midiSynth.getDefaultSoundbank();
            midiSynth.loadAllInstruments(midiSynth.getDefaultSoundbank());
            midiSynth.remapInstrument(instr[0], instr[f.getInstrument()]);
            PlayNote pn = new PlayNote();
            pn.playNote(-1, f.getBpm(), mChannels);
            for (Note n : finalNotes) {
                System.out.println(n);
                pn.playNote(n.getMidi(), n.getDuration(), mChannels);
            }
//            int[] notes = {60, 63, 67};
//
//            pn.playNotes(notes, mChannels);
//
//            int[] notes1 = {62, 65, 69};
//            pn.playNotes(notes1, mChannels);
//
//            pn.playNote(60, mChannels);
//            pn.playNote(67, mChannels);
//            pn.playNote(64, mChannels);
//            pn.playNote(64, mChannels);
//            pn.playNote(62, mChannels);
//            pn.playNote(65, mChannels);
//            pn.playNote(64, mChannels);
//            pn.playNote(67, mChannels);
//            pn.playNote(65, mChannels);
//            pn.playNote(69, mChannels);
//
//            pn.playNote(67, mChannels);
//            pn.playNote(71, mChannels);
//            pn.playNote(69, mChannels);
//            pn.playNote(70, mChannels);
//            pn.playNote(65, mChannels);
//            pn.playNote(63, mChannels);
//            pn.playNote(62, mChannels);
//            pn.playNote(60, mChannels);








        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}
