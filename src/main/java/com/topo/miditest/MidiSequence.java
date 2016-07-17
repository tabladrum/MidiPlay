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


    /*
 * This method parses the specified char[  ] of notes into a Track.
 * The musical notation is the following:
 * A-G:   A named note; Add b for flat and # for sharp.
 * +:     Move up one octave. Persists.
 * -:     Move down one octave.  Persists.
 * /1:    Notes are whole notes.  Persists 'till changed
 * /2:    Half notes
 * /4:    Quarter notes
 * /n:    N can also be 8, 16, 32, 64.
 * s:     Toggle sustain pedal on or off (initially off)
 *
 * >:     Louder.  Persists
 * <:     Softer.  Persists
 * .:     Rest. Length depends on current length setting
 * Space: Play the previous note or notes; notes not separated by spaces
 *        are played at the same time
 */
    public void addTrack(int instrument, Note[] notes) throws InvalidMidiDataException {
        Track track = sequence.createTrack();  // Begin with a new track
        // Set the instrument on channel 0
        ShortMessage sm = new ShortMessage();
        sm.setMessage(ShortMessage.PROGRAM_CHANGE, 0, instrument, 0);
        track.add(new MidiEvent(sm, 0));

//        int n = 0; // current character in notes[  ] array
        int timeInTicks = 60; // time in ticks for the composition

        // These values persist and apply to all notes 'till changed
        int notelength = beatResolution; // default to quarter notes
        int velocity = 64;   // default to middle volume
        int basekey = 60;    // 60 is middle C. Adjusted up and down by octave
        boolean sustain = false;   // is the sustain pedal depressed?
        int numnotes = 0;    // How many notes in current chord?

        for (int n = 0; n < notes.length; n++) {
            int[] keys = notes[n].getMidi();
            if (keys.length == 1) {
                int key = keys[0];
                /**
                 * quater note = 1 beat
                 * 1 beat = 16
                 */
                notelength = beatResolution;

                //sustain
//                ShortMessage m = new ShortMessage();
//                m.setMessage(ShortMessage.CONTROL_CHANGE, 0,
//                        DAMPER_PEDAL, notes[n].isSustain() ? DAMPER_ON : DAMPER_OFF);
//                track.add(new MidiEvent(m, timeInTicks));

                if (key !=-1) {
                    addNote(track, timeInTicks, notelength, key, notes[n].getVelocity());
                }
                timeInTicks += notelength;
            } else {
                if (notes[n].isTogether()) {
                    notelength = beatResolution;
                } else {
                    notelength = beatResolution / keys.length;
                }
                for (int i = 0; i < keys.length; i++) {
//                    //sustain
//                    ShortMessage m = new ShortMessage();
//                    m.setMessage(ShortMessage.CONTROL_CHANGE, 0,
//                            DAMPER_PEDAL, notes[n].isSustain() ? DAMPER_ON : DAMPER_OFF);
//                    track.add(new MidiEvent(m, timeInTicks));
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
            /**
             char c = notes[n++];
             int[] keys = notes[n].getMidi();

             if (c == '+') basekey += 12;        // increase octave

             else if (c == '-') basekey -= 12;   // decrease octave
             else if (c == '>') velocity += 16;  // increase volume;
             else if (c == '<') velocity -= 16;  // decrease volume;
             else if (c == '/') {
             char d = notes[n++];
             if (d == '2') notelength = 32;  // half note
             else if (d == '4') notelength = 16;  // quarter note
             else if (d == '8') notelength = 8;   // eighth note
             else if (d == '3' && notes[n++] == '2') notelength = 2;
             else if (d == '6' && notes[n++] == '4') notelength = 1;
             else if (d == '1') {
             if (n < notes.length && notes[n] == '6')
             notelength = 4;    // 1/16th note
             else notelength = 64;  // whole note
             }
             } else if (c == 's') {
             sustain = !sustain;
             // Change the sustain setting for channel 0
             ShortMessage m = new ShortMessage();
             m.setMessage(ShortMessage.CONTROL_CHANGE, 0,
             DAMPER_PEDAL, sustain ? DAMPER_ON : DAMPER_OFF);
             track.add(new MidiEvent(m, timeInTicks));
             }

             addNote(track, timeInTicks, notelength, key, velocity);
             numnotes++;
             } else if (c == ' ') {
             // Spaces separate groups of notes played at the same time.
             // But we ignore them unless they follow a note or notes.
             if (numnotes > 0) {
             timeInTicks += notelength;
             numnotes = 0;
             }
             } else if (c == '.') {
             // Rests are like spaces in that they force any previous
             // note to be output (since they are never part of chords)
             if (numnotes > 0) {
             timeInTicks += notelength;
             numnotes = 0;
             }
             // Now add additional rest time
             timeInTicks += notelength;
             }
             **/
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
