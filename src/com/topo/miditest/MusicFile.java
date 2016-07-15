package com.topo.miditest;


import javax.naming.directory.InvalidAttributesException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

public class MusicFile {

    private int instrument = 0;
    private Scale scale = new Scale(NoteNotation.C, 5);
    private String timing = "4/4";
    private int bpm = 60;
    private String[] notes;
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
        input = input.replace("\n","");
        StringTokenizer st = new StringTokenizer(input, LINE_DELIM);
        while (st.hasMoreElements()) {
            String s = st.nextElement().toString();
            String[] parts = s.split(VARIABLE_DELIM);
            switch (parts[0].toUpperCase()) {
                case INSTRUMENT:
                    instrument = Integer.parseInt(parts[1]);

                case BPM:
                    bpm = Integer.parseInt(parts[1]);
                    break;

                case SCALE:
                    char[] chars = parts[1].toCharArray();
                    scale = new Scale(NoteNotation.fromString(String.valueOf(chars[0])), Integer.parseInt(String.valueOf(chars[1])));
                    break;

                case TIMING:
                    timing = parts[1];
                    break;

                case TYPE:
                    type = NotationType.fromString(parts[1]);
                    break;

                case NOTES:
                    String noteString = parts[1];
                    notes = noteString.split(" ");
                    break;

                default:
                    throw new InvalidAttributesException("Invalid input file. Must contain Instrument and Notes");
            }

        }
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

    public String[] getNotes() {
        return notes;
    }

    public int getBpm() {
        return bpm;
    }

    public NotationType getType() {
        return type;
    }
}
