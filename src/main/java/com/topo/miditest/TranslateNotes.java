package com.topo.miditest;


import org.apache.commons.lang.StringUtils;

public class TranslateNotes {
    private Note[] notes;
    private int[] codes;
    private Scale scale;


    public TranslateNotes (Note[] notes, NotationType type, Scale scale) {
        this.notes = notes;
        this.scale = scale;
        translate(type);
    }

    private void translate(NotationType type) {
        switch (type) {
            case Indian:
                translateIndian();
                break;
            case Western:
                translateWestern();
                break;
            default:
                break;
        }
    }

    private void translateWestern() {
        codes = new int[notes.length];
        for (int i = 0; i < notes.length; i++) {
            String cleanNote = notes[i].getNote().replace("+","").replace("-","");
            if ("O".equalsIgnoreCase(notes[i].getNote())) {
                notes[i].setMidi(-1);
            } else {
                WesternNotation wn = WesternNotation.fromString(cleanNote);
                notes[i].setMidi(NoteToMidiNote.getMidiNote(NoteNotation.fromOrdinal(wn.ordinal()), getOctave(notes[i].getNote()), scale));
            }
        }
    }

    private int getOctave(String note) {
        int pluses = StringUtils.countMatches(note,"+");
        int minuses = StringUtils.countMatches(note, "-");
        return scale.getOctave() + pluses - minuses;
    }

    private void translateIndian() {
        codes = new int[notes.length];
        for (int i = 0; i < notes.length; i++) {
            String cleanNote = notes[i].getNote().replace("+","").replace("-","");

            if ("O".equalsIgnoreCase(notes[i].getNote())) {
                notes[i].setMidi(-1);
            } else {
                IndianNotation in = IndianNotation.fromString(cleanNote);

                notes[i].setMidi(NoteToMidiNote.getMidiNote(NoteNotation.fromOrdinal(in.ordinal()), getOctave(notes[i].getNote()), scale ));
            }
        }
    }


    public int[] getCodes() {
        return codes;
    }

    public Note[] getNotes() {
        return notes;
    }
}
