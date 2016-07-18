package com.topo.miditest;

/**
 * Created by yaf107 on 7/17/16.
 */
public class PlayMode {
    private PlayModeType type;
    private String[] data;

    public PlayMode (PlayModeType type, String[] data) {
        this.type = type;
        this.data = data;
    }

    public PlayModeType getType() {
        return type;
    }

    public String[] getData() {
        return data;
    }
}
