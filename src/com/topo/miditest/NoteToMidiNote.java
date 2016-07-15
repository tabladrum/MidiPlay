package com.topo.miditest;


public class NoteToMidiNote {


    public static int getMidiNote (String note, int octave, Scale scale) {
        NoteNotation noteNotation = NoteNotation.fromString(note);
        return getMidiNote(noteNotation, octave, scale);
    }

    public  static int getMidiNote (NoteNotation noteNotation, int octave, Scale scale) {
        return noteNotation.ordinal() + scale.getNote().ordinal() + 12 * octave;
    }

}
