package com.topo.miditest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by yaf107 on 7/16/16.
 */
public class PlayNormal extends AbstractPlay {

    @Override
    public void playSequence(MusicFile musicFile) {
        Note[] rawNotes = musicFile.getNotes();
        TranslateNotes translateNotes = new TranslateNotes(rawNotes, musicFile.getType(), musicFile.getScale());
        Note[] notes = translateNotes.getNotes();

        List<Note[]> noteList = new LinkedList<>();
        noteList.add(notes);
        playSequence(musicFile.getBpm(), musicFile.getResolution(), musicFile.getInstrument(), noteList);
    }
}
