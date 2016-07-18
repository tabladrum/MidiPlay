package com.topo.miditest;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by yaf107 on 7/16/16.
 */
public class PlayCombination extends AbstractPlay {

    @Override
    public void playSequence(MusicFile musicFile) {
        String[] rawNotes = musicFile.getRawNotes();
        int numNotes = rawNotes.length;
        if (musicFile.getMode().getData() != null) {
            try {
                numNotes = Integer.parseInt(musicFile.getMode().getData()[0]);
            } catch (NumberFormatException ne) {
            }
        }
        Set<String[]> combinations = NoteCombination.permute(rawNotes);
        List<Note[]> noteList = new LinkedList<>();
        for (String[] sArray : combinations) {
            Note[] notes = musicFile.getNotesFromStringArray(sArray);
            TranslateNotes translateNotes = new TranslateNotes(notes, musicFile.getType(), musicFile.getScale());
            Note[] translated = translateNotes.getNotes();
            for (Note n: notes) {
                for (String s : n.getNote())
                System.out.print(s + " ");
            }
            System.out.println();
            noteList.add(translated);
        }
        playSequence(musicFile.getBpm(), musicFile.getResolution(), musicFile.getInstrument(), noteList);
    }
}
