package com.topo.miditest;


import org.apache.commons.lang.StringUtils;

public class TranslateNotes {
    private String[] notes;
    private int[] codes;
    private Scale scale;


    public TranslateNotes (String[] notes, NotationType type, Scale scale) {
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
            String cleanNote = notes[i].replace("+","").replace("-","");
            if ("O".equalsIgnoreCase(notes[i])) {
                codes[i] = -1;
            } else {
                WesternNotation wn = WesternNotation.fromString(cleanNote);
                codes[i] = NoteToMidiNote.getMidiNote(NoteNotation.fromOrdinal(wn.ordinal()), getOctave(notes[i]), scale);
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
            String cleanNote = notes[i].replace("+","").replace("-","");

            if ("O".equalsIgnoreCase(notes[i])) {
                codes[i] = -1;
            } else {
                IndianNotation in = IndianNotation.fromString(cleanNote);

                codes[i] = NoteToMidiNote.getMidiNote(NoteNotation.fromOrdinal(in.ordinal()), getOctave(notes[i]), scale );
            }
        }
    }


    public int[] getCodes() {
        return codes;
    }
}
