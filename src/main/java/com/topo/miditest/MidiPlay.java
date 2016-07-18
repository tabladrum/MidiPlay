package com.topo.miditest;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

import static com.topo.miditest.MidiSequence.END_OF_TRACK;

/**
 * Created by yaf107 on 7/18/16.
 */
public class MidiPlay {


    public static void  play(Sequence sequence, int tempo) throws MidiUnavailableException, InvalidMidiDataException {
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
