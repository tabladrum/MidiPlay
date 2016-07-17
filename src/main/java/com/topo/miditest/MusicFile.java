package com.topo.miditest;


import org.apache.commons.lang.StringUtils;

import javax.naming.directory.InvalidAttributesException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class MusicFile {

    private int instrument = 0;
    private Scale scale = new Scale(NoteNotation.C, 5);
    private String timing = "4/4";
    private int bpm = 60;
    private Note[] notes;
    private String file;
    private NotationType type;

    private static final String LINE_DELIM = ";";
    private static final String VARIABLE_DELIM = "=";

    private static final String INSTRUMENT = "INSTRUMENT";
    private static final String SCALE = "SCALE";
    private static final String TIMING = "TIMING";
    private static final String NOTES = "NOTES";
    private static final String BPM = "BPM";
    private static final String TYPE = "TYPE";

    private static final String CHORD_BEGIN = "[";
    private static final String CHORD_END = "]";
    private static final String COMPOUND_BEGIN = "(";
    private static final String COMPOUND_END = ")";

    public MusicFile(String file) {
        this.file = file;
        init();
    }

    private void init() {
        try {
            String input = new String(Files.readAllBytes(Paths.get(file)));
            parseInput(input);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidAttributesException e) {
            e.printStackTrace();
        }
    }

    private void parseInput(String input) throws InvalidAttributesException {
        input = input.replace("\n", "");
        StringTokenizer st = new StringTokenizer(input, LINE_DELIM);
        while (st.hasMoreElements()) {
            String s = st.nextElement().toString();
            String[] parts = s.split(VARIABLE_DELIM);
            switch (parts[0].toUpperCase()) {
                case INSTRUMENT:
                    instrument = Integer.parseInt(parts[1].trim());

                case BPM:
                    bpm = Integer.parseInt(parts[1].trim());
                    break;

                case SCALE:
                    char[] chars = parts[1].trim().toCharArray();
                    scale = new Scale(NoteNotation.fromString(String.valueOf(chars[0])), Integer.parseInt(String.valueOf(chars[1])));
                    break;

                case TIMING:
                    timing = parts[1].trim();
                    break;

                case TYPE:
                    type = NotationType.fromString(parts[1].trim());
                    break;

                case NOTES:
                    String noteString = parts[1].trim();
                    notes = getNotesFromString(noteString);
                    break;

                default:
                    throw new InvalidAttributesException("Invalid input file. Must contain Instrument and Notes");
            }

        }
    }

    private Note[] getNotesFromString(String noteString) {
        String[] notes = StringUtils.split(noteString, " ");
        List<Note> noteList = new LinkedList<>();
        for (int i = 0; i < notes.length; i++) {
            String cleanNote = StringUtils.remove(notes[i], '-');
            cleanNote = StringUtils.remove(cleanNote, '+');
            if (cleanNote.length() < 2) {
                Note n = new Note(notes[i]); // midi note length 64 = full note.
                noteList.add(n);
            } else if (notes[i].startsWith(COMPOUND_BEGIN) || notes[i].startsWith(CHORD_BEGIN)) {
                String tempNotes = StringUtils.removeEnd(notes[i], CHORD_END);
                tempNotes = StringUtils.removeStart(tempNotes, CHORD_BEGIN);
                tempNotes = StringUtils.removeEnd(tempNotes, COMPOUND_END);
                tempNotes = StringUtils.removeStart(tempNotes, COMPOUND_BEGIN);
                String[] singles = tempNotes.split(",");
                Note n = new Note(singles, notes[i].startsWith(CHORD_BEGIN));
                noteList.add(n);
            }
        }
        Note[] returnArray = new Note[noteList.size()];
        int i = 0;
        for (Note n : noteList) {
            returnArray[i] = n;
            i = i + 1;
        }
        return returnArray;
    }

    private String[] breakCompoundNotes(String notes) {
        notes = StringUtils.removeStart(notes, "(");
        notes = StringUtils.removeEnd(notes, ")");
        return notes.split(",");
    }

    public int getInstrument() {
        return instrument;
    }

    public Scale getScale() {
        return scale;
    }

    public String getTiming() {
        return timing;
    }

    public Note[] getNotes() {
        return notes;
    }

    public int getBpm() {
        return bpm;
    }

    public NotationType getType() {
        return type;
    }
}
