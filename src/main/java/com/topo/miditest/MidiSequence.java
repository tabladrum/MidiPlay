package com.topo.miditest;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/**
 * Created by yaf107 on 7/16/16.
 */
public class MidiSequence {
    public static final int DAMPER_PEDAL = 64;
    public static final int DAMPER_ON = 127;
    public static final int DAMPER_OFF = 0;
    public static final int END_OF_TRACK = 47;
    private int beatResolution;

    private Sequence sequence;
    private int tempo = 60;


    public MidiSequence(int bpm) throws InvalidMidiDataException {
        this.tempo = bpm;
        this.beatResolution = 60;
        sequence = new Sequence(Sequence.PPQ, beatResolution);
    }

    public MidiSequence(int bpm, int ticks) throws InvalidMidiDataException {
        this.tempo = bpm;
        this.beatResolution = ticks;
        sequence = new Sequence(Sequence.PPQ, beatResolution);
    }



    public void addTrack(int instrument, Note[] notes) throws InvalidMidiDataException {
        Track track = sequence.createTrack();  // Begin with a new track
        // Set the instrument on channel 0
        ShortMessage sm = new ShortMessage();
        sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
        track.add(new MidiEvent(sm, 0));

        int timeInTicks = 60; // time in ticks for the composition
        int notelength; // default to quarter notes

        boolean firstNote = true;
        for (int n = 0; n < notes.length; n++) {
            int[] keys = notes[n].getMidi();
            if (keys.length == 1) {
                int key = keys[0];
                notelength = beatResolution;
                //sustain
                ShortMessage m = new ShortMessage();
                m.setMessage(ShortMessage.CONTROL_CHANGE, 0,
                        DAMPER_PEDAL, notes[n].isSustain() ? DAMPER_ON : DAMPER_OFF);
                track.add(new MidiEvent(m, timeInTicks));

                if (key !=-1) {
                    addNote(track, timeInTicks, notelength, key, notes[n].getVelocity());
//                    addNoteSmooth(track, timeInTicks, notelength, key, notes[n].getVelocity(), firstNote ? 1 : 2);
                    firstNote = !firstNote;
                }
                timeInTicks += notelength;
            } else {
                if (notes[n].isTogether()) {
                    notelength = beatResolution;
                } else {
                    notelength = beatResolution / keys.length;
                }
                for (int i = 0; i < keys.length; i++) {
                    if (keys[i] !=-1) {
                        addNote(track, timeInTicks, notelength, keys[i], notes[n].getVelocity());
                    }
                    if (!notes[n].isTogether()) {
                        timeInTicks += notelength;
                    } else {
                        if (i == keys.length - 1) {
                            timeInTicks += notelength;
                        }
                    }
                }

            }
        }
    }

    // A convenience method to add a note to the track on channel 0
    public static void addNote(Track track, int startTick,
                               int tickLength, int key, int velocity)
            throws InvalidMidiDataException {
        ShortMessage on = new ShortMessage();
        on.setMessage(ShortMessage.NOTE_ON, 0, key, velocity);
        ShortMessage off = new ShortMessage();
        off.setMessage(ShortMessage.NOTE_OFF, 0, key, velocity);
        track.add(new MidiEvent(on, startTick));
        track.add(new MidiEvent(off, startTick + tickLength));
    }

    public static void addNoteSmooth (Track track, int startTick,
                               int tickLength, int key, int velocity, int noteNumber)
            throws InvalidMidiDataException {

        if (noteNumber == 2) {
            ShortMessage on = new ShortMessage();
            on.setMessage(ShortMessage.NOTE_ON, 0, key, velocity);
            track.add(new MidiEvent(on, startTick));
        }

        if (noteNumber == 1) {
            ShortMessage off = new ShortMessage();
            off.setMessage(ShortMessage.NOTE_OFF, 0, key, velocity);
            track.add(new MidiEvent(off, startTick + tickLength));
        }
    }

    public void play() throws MidiUnavailableException, InvalidMidiDataException {
        Sequencer sequencer = MidiSystem.getSequencer();
        sequencer.open();
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        sequencer.getTransmitter().setReceiver(synthesizer.getReceiver());

        // Specify the sequence to play, and the tempo to play it at
        sequencer.setTempoInBPM((float)tempo);
        sequencer.setSequence(sequence);


        // Let us know when it is done playing
        sequencer.addMetaEventListener(new MetaEventListener() {
            public void meta(MetaMessage m) {
                // A message of this type is automatically sent
                // when we reach the end of the track
                if (m.getType() == END_OF_TRACK) System.exit(0);
            }
        });
        // And start playing now.
        sequencer.start();
    }
}
